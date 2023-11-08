
package Proposed_Method;

import net.librec.recommender.RecommenderContext;
import net.librec.recommender.item.RecommendedList;


public interface RecommenderEvaluator {

    /**
     * Evaluate on the recommender context with the recommended list.
     *
     * @param context          RecommenderContext object
     * @param recommendedList  RecommendedList object
     * @return  evaluate result
     */
    public double evaluate(RecommenderContext context, RecommendedList recommendedList);

    /**
     * Set the number of recommended items.
     *
     * @param topN the number of recommended items
     */
    public void setTopN(int topN);
}
