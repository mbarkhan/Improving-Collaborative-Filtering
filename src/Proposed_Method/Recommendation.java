
package Proposed_Method;

import com.google.common.cache.LoadingCache;
import net.librec.annotation.ModelData;
import net.librec.common.LibrecException;
import net.librec.math.algorithm.Maths;
import net.librec.math.algorithm.Randoms;
import net.librec.math.structure.DenseMatrix;
import net.librec.math.structure.DenseVector;
import net.librec.recommender.MatrixFactorizationRecommender;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;


@ModelData({"isRanking", "gbpr", "userFactors", "itemFactors", "trainMatrix"})
public class Recommendation extends MatrixFactorizationRecommender {
    private float rho;
    private int gLen;

    /**
     * bias regularization
     */
    protected double regBias;

    /**
     * items biases vector
     */
    private DenseVector itemBiases;

    /**
     * user-items cache, item-users cache
     */
    protected LoadingCache<Integer, List<Integer>> userItemsCache, itemUsersCache;

    /**
     * Guava cache configuration
     */
    protected static String cacheSpec;

    @Override
    protected void setup() throws LibrecException {
        super.setup();

        itemBiases = new DenseVector(numItems);
        itemBiases.init();

        rho = conf.getFloat("rec.gpbr.rho",1.5f);
        gLen = conf.getInt("rec.gpbr.gsize",2);

        cacheSpec = conf.get("guava.cache.spec", "maximumSize=200,expireAfterAccess=2m");
        userItemsCache = trainMatrix.rowColumnsCache(cacheSpec);
        itemUsersCache = trainMatrix.columnRowsCache(cacheSpec);
    }

    @Override
    protected void trainModel() throws LibrecException {
        for (int iter = 1; iter <= numIterations; iter++) {

            loss = 0.0d;

            DenseMatrix tempUserFactors = new DenseMatrix(numUsers, numFactors);
            DenseMatrix tempItemFactors = new DenseMatrix(numItems, numFactors);

            for (int sample = 0, smax = numUsers * 100; sample < smax; sample++) {
                // uniformly draw (userIdx, posItemIdx, userGroupSet, negItemIdx)
                int userIdx, posItemIdx, negItemIdx;
                // userIdx
                List<Integer> ratedItems = null; // row userIdx itemList
                do {
                    userIdx = Randoms.uniform(trainMatrix.numRows());
                    try {
                        ratedItems = userItemsCache.get(userIdx);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } while (ratedItems.size() == 0);

                // positive item
                posItemIdx = Randoms.random(ratedItems);

                // users group Set
                List<Integer> posRatedUserList = null; // column i
                try {
                    posRatedUserList = itemUsersCache.get(posItemIdx);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Set<Integer> groupSet = new HashSet<>();
                if (posRatedUserList.size() <= gLen) {
                    groupSet.addAll(posRatedUserList);
                } else {
                    groupSet.add(userIdx); // u in G
                    while (groupSet.size() < gLen) {
                        int tempUserIdx = Randoms.random(posRatedUserList);
                        if (!groupSet.contains(tempUserIdx))
                            groupSet.add(tempUserIdx);
                    }
                }

                double posPredictRating = predict(userIdx, posItemIdx, groupSet);

                // negative item index
                do {
                    negItemIdx = Randoms.uniform(numItems);
                } while (ratedItems.contains(negItemIdx));

                double negPredictRating = predict(userIdx, negItemIdx);

                double diffValue = posPredictRating - negPredictRating;

                double lossValue = -Math.log(Maths.logistic(diffValue));
                loss += lossValue;

                double deriValue = Maths.logistic(-diffValue);

                // update bi, bj
                double posBiasValue = itemBiases.get(posItemIdx);
                itemBiases.add(posItemIdx, learnRate * (deriValue - regBias * posBiasValue));

                double negBiasValue = itemBiases.get(negItemIdx);
                itemBiases.add(negItemIdx, learnRate * (-deriValue - regBias * negBiasValue));

                // update Pw
                double averageWeight = 1.0 / groupSet.size();
                double sumGroup[] = new double[numFactors];
                for (int groupUserIdx : groupSet) {
                    double delta = groupUserIdx == userIdx ? 1 : 0;
                    for (int factorIdx = 0; factorIdx < numFactors; factorIdx++) {
                        double groupUserFactorValue = userFactors.get(groupUserIdx, factorIdx);
                        double posItemFactorValue = itemFactors.get(posItemIdx, factorIdx);
                        double negItemFactorValue = itemFactors.get(negItemIdx, factorIdx);

                        double deltaGroup = rho * averageWeight * posItemFactorValue + (1 - rho) * delta * posItemFactorValue - delta * negItemFactorValue;
                        tempUserFactors.add(groupUserIdx, factorIdx, learnRate * (deriValue * deltaGroup - regUser * groupUserFactorValue));

                        sumGroup[factorIdx] += groupUserFactorValue;
                    }
                }

                // update itemFactors
                for (int factorIdx = 0; factorIdx < numFactors; factorIdx++) {
                    double userFactorValue = userFactors.get(userIdx, factorIdx);
                    double posItemFactorValue = itemFactors.get(posItemIdx, factorIdx);
                    double negItemFactorValue = itemFactors.get(negItemIdx, factorIdx);

                    double posDelta = rho * averageWeight * sumGroup[factorIdx] + (1 - rho) * userFactorValue;
                    tempItemFactors.add(posItemIdx, factorIdx, learnRate * (deriValue * posDelta - regItem * posItemFactorValue));

                    double negDelta = -userFactorValue;
                    tempItemFactors.add(negItemIdx, factorIdx, learnRate * (deriValue * negDelta - regItem * negItemFactorValue));
                }
            }

            userFactors.addEqual(tempUserFactors);
            itemFactors.addEqual(tempItemFactors);

            if (isConverged(iter) && earlyStop) {
                break;
            }
            updateLRate(iter);
        }
    }


    protected double predict(int userIdx, int itemIdx, Set<Integer> groupSet) throws LibrecException {
        double predictRating = predict(userIdx, itemIdx);

        double sum = 0;
        for (int groupUserIdx : groupSet)
            sum += DenseMatrix.rowMult(userFactors, groupUserIdx, itemFactors, itemIdx);

        double groupRating = sum / groupSet.size() + itemBiases.get(itemIdx);

        return rho * groupRating + (1 - rho) * predictRating;
    }

    protected double predict(int userIdx, int itemIdx){
        return itemBiases.get(itemIdx) + DenseMatrix.rowMult(userFactors, userIdx, itemFactors, itemIdx);
    }
}
