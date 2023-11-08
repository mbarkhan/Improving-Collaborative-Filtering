/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proposed_Method;

import net.librec.common.LibrecException;
import net.librec.recommender.SocialRecommender;


public class GetUserItems extends SocialRecommender {

    void GetRU(double[] Si, double[] S, double[][] Sim_Items) {

        int pu = Si.length;//Si.lenght
        int k = S.length;//s.lenght
        double Ru = 0;
        int sim = 0;
        for (int SI = 0; SI < pu; SI++) {

            for (int s = 0; s < k; s++) {
                sim = (int) (sim + Sim_Items[SI][s]);
                Ru = sim / pu;
                if (Ru >= 0.66) {

                    userFactors.add(S[s]);
                }
            }

        }

    }

    @Override
    protected void trainModel() throws LibrecException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
