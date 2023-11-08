
package Proposed_Method;

import net.librec.conf.Configuration;
import net.librec.math.structure.SparseMatrix;
import net.librec.math.structure.SymmMatrix;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.item.RecommendedList;
import net.librec.similarity.RecommenderSimilarity;

import java.util.Map;


public abstract class AbstractRecommenderEvaluator implements RecommenderEvaluator {

    /**
     * the number of  recommended items
     */
    protected int topN;
    /**
     * configuration of the evaluator
     */
    protected Configuration conf;
    /**
     * default similarityMatrix
     */
    protected SymmMatrix similarityMatrix;
    /**
     * all similarity maps
     */
    protected Map<String, RecommenderSimilarity> similarities;

    /**
     * Evaluate on the recommender context with the recommended list.
     *
     * @param context          the recommender context
     * @param recommendedList  the list of recommended items
     * @return  evaluate result
     */
    public double evaluate(RecommenderContext context, RecommendedList recommendedList) {
        SparseMatrix testMatrix = context.getDataModel().getDataSplitter().getTestData();
        conf = context.getConf();
        String[] similarityKeys = conf.getStrings("rec.recommender.similarities");
        if (similarityKeys != null && similarityKeys.length > 0) {
            similarityMatrix = context.getSimilarity().getSimilarityMatrix();
            similarities = context.getSimilarities();
        }
        return evaluate(testMatrix, recommendedList);
    }

    /**
     * Evaluate on the test set with the the list of recommended items.
     *
     * @param testMatrix
     *            the given test set
     * @param recommendedList
     *            the list of recommended items
     * @return evaluate result
     */
    public abstract double evaluate(SparseMatrix testMatrix, RecommendedList recommendedList);

    /**
     * Set the number of recommended items.
     *
     * @param topN the number of  recommended items
     */
    public void setTopN(int topN) {
        this.topN = topN;
    }

    /**
     * Return the configuration fo the evaluator.
     *
     * @return the configuration fo the evaluator
     */
    public Configuration getConf() {
        return conf;
    }

}
