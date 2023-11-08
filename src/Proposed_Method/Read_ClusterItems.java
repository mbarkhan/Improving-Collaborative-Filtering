package Proposed_Method;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Read_ClusterItems {

    public static int row = 515082;
    public static int col = 3;

    double[] getmusic(ArrayList<String> PreferenceList) throws FileNotFoundException {
        String filename = "data\\MSD\\Cluster_Items\\Cluster_Items.txt"; //Here you must write the path to the file f.exp "//folder//file.txt"

        FileReader readConnectionToFile = new FileReader(filename);
        BufferedReader reads = new BufferedReader(readConnectionToFile);
        Scanner scan = new Scanner(reads);

        int[][] temperatures = new int[row][col];
        int counter = 0;

        while (scan.hasNext() && counter < 5) {
            for (int i = 0; i < row; i++) {
                counter = counter + 1;
                for (int m = 0; m < col; m++) {
                    temperatures[i][m] = scan.nextInt();
                }
            }
        }

        int NumCluster = 1508;
        int NumMusic = 20277;
        double rating[] = new double[NumMusic];
        

        for (int a = 0; a < row; a++) {
            int ClusterID = Integer.parseInt(PreferenceList.get(a));
            int ItemId = Integer.parseInt(PreferenceList.get(a+1));
            
            rating[ClusterID] =ItemId;
        }

      

        return rating;
    }

  


}
