package Proposed_Method;

import java.io.IOException;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.math.algorithm.Randoms;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;

public class Experiment_1 {

    public static void main(String[] args) throws LibrecException, IOException {

        // recommender configuration
        Configuration conf = new Configuration();

        conf.set("dfs.data.dir", "./data");

        conf.set("data.input.path", "MSD/User_cluster_level/User_cluster_level_1.txt");//sparsity for  0.2----0.4

        conf.set("rec.iterator.maximum", "100");
        conf.set("rec.learnrate.decay", "1.0");
        conf.set("rec.learnrate.bolddriver", "false");
        conf.set("rec.recommender.earlystop", "false");
        conf.set("rec.recommender.verbose", "true");
        conf.set("rec.recommender.isranking", "true");
        conf.set("rec.eval.enable", "true");
        conf.set("rec.recommender.ranking.topn", "15");

//********************************************************************************
        Randoms.seed(1);
        // build data model
        TextDataModel dataModel = new TextDataModel(conf);
        dataModel.buildDataModel();

        // build recommender context
        RecommenderContext context = new RecommenderContext(conf, dataModel);

        System.out.println("*************************************************");
        Recommender recommender = new Recommendation();
        recommender.setContext(context);

        // run recommender algorithm
        recommender.recommend(context);

        // evaluate the recommended result
        RecommenderEvaluator evaluator1 = new PrecisionEvaluators();

        RecommenderEvaluator evaluator3 = new RecallEvaluators();

        evaluator1.setTopN(15);//pre
        evaluator3.setTopN(10);//rec

        System.out.println("Precision :" + recommender.evaluate(evaluator1));
        System.out.println("Recall :" + recommender.evaluate(evaluator3));
        double F_measure = (2 * recommender.evaluate(evaluator1) * recommender.evaluate(evaluator3)) / (recommender.evaluate(evaluator1) + recommender.evaluate(evaluator3));
        System.out.println("F_measure : " + F_measure);

    }
}
