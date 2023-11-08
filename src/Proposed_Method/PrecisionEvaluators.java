
package Proposed_Method;

import net.librec.eval.AbstractRecommenderEvaluator;
import net.librec.math.structure.SparseMatrix;
import net.librec.recommender.item.ItemEntry;
import net.librec.recommender.item.RecommendedList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PrecisionEvaluators extends AbstractRecommenderEvaluator {

   
    public double evaluate(SparseMatrix testMatrix, RecommendedList recommendedList) {
        double auc = 0.0d;

        int numUsers = testMatrix.numRows();
        int nonZeroNumUsers = 0;
        int[] numDroppedItemsArray = getConf().getInts("rec.eval.auc.dropped.num");

        for (int userIdx = 0; userIdx < numUsers; userIdx++) {
            Set<Integer> testSetByUser = testMatrix.getColumnsSet(userIdx);
            if (testSetByUser.size() > 0) {
                nonZeroNumUsers++;
                List<ItemEntry<Integer, Double>> recommendListByUser = recommendedList.getItemIdxListByUserIdx(userIdx);
                int numDroppedItems = numDroppedItemsArray[userIdx] - recommendListByUser.size();
                Set<Integer> recommendSetByUser = new HashSet<>();
                int topK = this.topN <= recommendListByUser.size() ? this.topN : recommendListByUser.size();
                for (int indexOfItem = 0; indexOfItem < topK; ++indexOfItem) {
                    recommendSetByUser.add(recommendListByUser.get(indexOfItem).getKey());
                }

                int numRelevantItems = 0, numMissItems = 0;
                for (Integer testItemIdx : testSetByUser) {
                    if (recommendSetByUser.contains(testItemIdx)) {
                        numRelevantItems++;
                    } else {
                        numMissItems++;
                    }
                }

                int numEvaluatingItems = recommendSetByUser.size() + numDroppedItems;
                int numEvaluatingPairs = (numEvaluatingItems - numRelevantItems) * numRelevantItems;

                if (numEvaluatingPairs < 0) {
                    throw new IndexOutOfBoundsException("numEvaluatingPairs cannot be less than 0.");
                }

                if (numEvaluatingPairs == 0) {
                    auc += 0.5;
                    continue;
                }

                int numCorrectPairs = 0;
                int hits = 0;
                for (Integer itemIdx : recommendSetByUser) {
                    if (!testSetByUser.contains(itemIdx)) {
                        numCorrectPairs += hits;
                    } else {
                        hits ++;
                    }
                }

                numCorrectPairs += hits * (numDroppedItems - numMissItems);

                auc += (numCorrectPairs + 0.0) / numEvaluatingPairs;
            }
        }

        return nonZeroNumUsers > 0 ? auc / nonZeroNumUsers : 0.0d;
    }
}
