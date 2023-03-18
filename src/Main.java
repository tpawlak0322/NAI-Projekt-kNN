import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final int k = 4;
    public static void main(String[] args) {

        ArrayList<MLVector> objects_data_train = new ArrayList<>();
        objects_data_train = parseCsvToArray("/Users/tomaszpawlak/Documents/wdbc.data",1,",");
        ArrayList<MLVector> objects_data_test = new ArrayList<>();
        objects_data_test = parseCsvToArray("/Users/tomaszpawlak/Documents/wdbctest.data",1,",");

        AlgorithmKNN algorithmKNN = new AlgorithmKNN(objects_data_train);
//        for(MLVector vec : objects_data_test) {
//            System.out.println("For vec: " + vec + " and K = " + k);
//            String[] result = algorithmKNN.returnSmallestDistance(k, vec);
//            Arrays.stream(result).forEach(System.out::println);
//        }
        System.out.println("Podaj atrybuty");
        String input = new Scanner(System.in).nextLine();
        String[] splittedInput = input.split(",");
        MLVector inputMLVector = parseStringToMLVector(splittedInput);
        System.out.println("Provided vector: " + inputMLVector);
        String[] result = algorithmKNN.returnSmallestDistance(k, inputMLVector);
        Arrays.stream(result).forEach(System.out::println);
    }
    public static  MLVector parseStringToMLVector(String[] text,int DecisionParamaterIndex){
        double[] attributes = new double[text.length - 1]; // -1 ponieważ wczytuje atrybut decyzyjny
        int realIndex = 0;
        for (int i = 0; i < text.length; i++) {
            if (i == DecisionParamaterIndex) continue;
            attributes[realIndex] = Double.parseDouble(text[i]);
            realIndex++;
        }
        return new MLVector(text[DecisionParamaterIndex],attributes);
    }
    public static  MLVector parseStringToMLVector(String[] text){
        double[] attributes = new double[text.length];
        for (int i = 0; i < text.length; i++) {
            attributes[i] = Double.parseDouble(text[i]);
        }
        return new MLVector("Test",attributes);
    }
    public static ArrayList<MLVector> parseCsvToArray(String PathToFile, int DecisionParamaterIndex,String splitRegex) {
        ArrayList<MLVector> mlVectors = new ArrayList<>();
        try {
            for (String s : Files.readAllLines(Paths.get(PathToFile))) {
                String[] splitted = s.split(splitRegex);
                mlVectors.add(parseStringToMLVector(splitted,DecisionParamaterIndex));

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mlVectors;
    }
}