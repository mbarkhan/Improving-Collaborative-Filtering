package Proposed_Method;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Read_SimilarityItems {


    public  double[][] getsimilarity() throws IOException {

        String filename = "data\\MSD\\Similarity_Items\\Items_Items_similarity.txt"; //Here you must write the path to the file f.exp "//folder//file.txt"
        int row = 20277;//number similarity
        int col = 3;

        FileReader readConnectionToFile = new FileReader(filename);
        BufferedReader reads = new BufferedReader(readConnectionToFile);
        Scanner scan = new Scanner(reads);

        double[][] temperatures = new double[row][col];
        int counter = 0;

        while (scan.hasNext() && counter < 5) {
            for (int i = 0; i < row; i++) {
                counter = counter + 1;
                for (int m = 0; m < col; m++) {
                    temperatures[i][m] = scan.nextInt();
                }
            }
        }

        scan.close();
        reads.close();

        return temperatures;
    }
}
