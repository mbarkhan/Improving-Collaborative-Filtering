package Proposed_Method;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.librec.common.LibrecException;
import net.librec.math.structure.MatrixEntry;
import net.librec.math.structure.SymmMatrix;
import net.librec.math.structure.SparseVector;
import net.librec.recommender.SocialRecommender;

public class Main extends SocialRecommender {

   
    public void setup() throws LibrecException {
        super.setup();

    }

    @Override
    protected void trainModel() throws LibrecException {

        for (int iter = 1; iter <= numIterations; iter++) {

            loss = 0.0d;

            //start part1
            for (MatrixEntry matrixEntry : trainMatrix) {

                for (int userindex = 0; userindex < numUsers; userindex++) {

                    try {

                        AprioriCalculation app = new AprioriCalculation();

                        Vector<String> rules = new Vector<String>();

                        rules = app.aprioriProcess();

                        ArrayList<String> subtoken = new ArrayList<String>();

                        SparseVector UserCluster = trainMatrix.column(userindex);

                        ArrayList<String> PreferenceList = new ArrayList<String>();

                        int count = 0;
                        for (int Bestusercluster : UserCluster.getIndex()) {

//
                            for (int x = 0; x < rules.size(); x++) {

                                StringTokenizer st1 = new StringTokenizer(rules.get(x));

                                for (int y = 0; y < st1.countTokens() - 1; y++) {

                                    subtoken.add(y, st1.nextToken());

                                    if (subtoken.get(y) == Bestusercluster + "") {
                                        PreferenceList.add(count, subtoken.get(subtoken.size()));
                                        count++;
                                    }
                                }
                            }

                        }

                        try {

                            CalculatedTop Object = new CalculatedTop();

                            Object.Action(PreferenceList, subtoken);

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }//end user
                    catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }//end user

            }
            loss *= 0.5d;
            if (isConverged(iter) && earlyStop) {
                break;
            }
            updateLRate(iter);
        }
    }
}
