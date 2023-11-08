
package Proposed_Method;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


class GetRelation {

    double[] GetS(ArrayList<String> PreferenceList) throws FileNotFoundException {

        Read_ClusterItems clusteritems = new Read_ClusterItems();

        double[] S = clusteritems.getmusic(PreferenceList);
        return S;
    }

    double[] getSi(ArrayList<String> subtoken) throws FileNotFoundException {

        Read_ClusterItems getitems = new Read_ClusterItems();

        double[] Si = getitems.getmusic(subtoken);

        return Si;

    }

    double[][] getSimilarity() throws IOException {

        Read_SimilarityItems similar = new Read_SimilarityItems();

        double Sim_Items[][] = similar.getsimilarity();

        return Sim_Items;
    }

}
