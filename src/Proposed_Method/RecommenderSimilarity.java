
package Proposed_Method;

import net.librec.data.DataModel;
import net.librec.math.structure.SymmMatrix;


public interface RecommenderSimilarity {

    /**
     * build and compute similarity matrix by dataModel
     * 
     * @param dataModel  data model
     */
    public void buildSimilarityMatrix(DataModel dataModel);

    /**
     * get similarity matrix as a SymmMatrix
     * 
     * @return Similarity Matrix
     */
    public SymmMatrix getSimilarityMatrix();
}
