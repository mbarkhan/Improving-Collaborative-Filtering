
package Proposed_Method;

import java.util.HashMap;
import java.util.Map;

import net.librec.common.AbstractContext;
import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.similarity.RecommenderSimilarity;


public class RecommenderContext extends AbstractContext {

    protected DataModel dataModel;

    protected RecommenderSimilarity similarity;

    protected Map<String, RecommenderSimilarity> similarities;

    public RecommenderContext(Configuration conf) {
        this.conf = conf;
    }

    public RecommenderContext(Configuration conf, DataModel dataModel) {
        this.conf = conf;
        this.dataModel = dataModel;
    }

    public RecommenderContext(Configuration conf, DataModel dataModel, RecommenderSimilarity similarity) {
        this.conf = conf;
        this.dataModel = dataModel;
        this.similarity = similarity;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public RecommenderSimilarity getSimilarity() {
        return similarity;
    }

    public void setSimilarity(RecommenderSimilarity similarity) {
        this.similarity = similarity;
    }

    /**
     * @return the similarities
     */
    public Map<String, RecommenderSimilarity> getSimilarities() {
        return similarities;
    }

    /**
     * @param similarityKey
     *            the similarities to add
     * @param similarity
     *            the similarities to add
     */
    public void addSimilarities(String similarityKey, RecommenderSimilarity similarity) {
        if(this.similarities == null){
            this.similarities = new HashMap<String, RecommenderSimilarity>();
        }
        this.similarities.put(similarityKey, similarity);
    }
}
