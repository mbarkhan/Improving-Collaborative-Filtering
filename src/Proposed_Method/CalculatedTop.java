/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proposed_Method;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


class CalculatedTop {

    void Action(ArrayList<String> PreferenceList, ArrayList<String> subtoken) throws FileNotFoundException, IOException {

        GetRelation Relat = new GetRelation();

        double[] S = Relat.GetS(PreferenceList);

        double[] Si = Relat.getSi(subtoken);

        double Sim_Items[][] = Relat.getSimilarity();

        GetUserItems obj = new GetUserItems();

        obj.GetRU(Si, S, Sim_Items);

    }

}
