
package Proposed_Method;

import net.librec.common.LibrecException;
import net.librec.data.DataModel;
import net.librec.eval.Measure.MeasureValue;
import net.librec.eval.RecommenderEvaluator;
import net.librec.recommender.item.RecommendedItem;

import java.util.List;
import java.util.Map;


public interface Recommender {
    /**
     * recommend
     *
     * @param context  recommender context
     * @throws LibrecException if error occurs during recommending
     */
    void recommend(RecommenderContext context) throws LibrecException;

    /**
     * evaluate
     *
     * @param  evaluator recommender evaluator
     * @return evaluate result
     * @throws LibrecException if error occurs during evaluating
     */
    double evaluate(RecommenderEvaluator evaluator) throws LibrecException;

    /**
     * evaluate Map
     *
     * @return evaluate map
     * @throws LibrecException if error occurs during constructing evaluate map
     */
    Map<MeasureValue, Double> evaluateMap() throws LibrecException;

    /**
     * get DataModel
     *
     * @return data model
     */
    DataModel getDataModel();

    /**
     * load Model
     *
     * @param filePath file path
     */
    void loadModel(String filePath);

    /**
     * save Model
     *
     * @param filePath file path
     */
    void saveModel(String filePath);

    /**
     * get Recommended List
     *
     * @return  recommended list
     */
    List<RecommendedItem> getRecommendedList();


    /**
     * set Context
     *
     * @param context recommender context
     */
    void setContext(RecommenderContext context);
}
