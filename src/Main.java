import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static int k = 4;
    private static final String pathToTrainData = "C:\\Users\\tpawl\\Documents\\wdbc.data";
    private static final int DecisionParamaterIndex = 30; //4 - iris, 30 - wdbc
    private static final String pathToTestData = "C:\\Users\\tpawl\\Documents\\wdbc.test.data";
    public static void main(String[] args) {

        ArrayList<MLVector> objects_data_train = new ArrayList<>();
        objects_data_train = parseCsvToArray(pathToTrainData,DecisionParamaterIndex,",");


        AlgorithmKNN algorithmKNN = new AlgorithmKNN(objects_data_train);
        while(true) {
            System.out.println("1. Uruchom algorytm na danych testowych");
            System.out.println("2. Uruchom algorytm na podanym przez siebie wektorze");
            System.out.println("3. Wprowadz nowe K");
            System.out.println("4. Exit");
            String input = new Scanner(System.in).nextLine();
            switch (input) {
                case "1":
                    checkAccuracyForTrainData(algorithmKNN);
                    break;
                case "2":
                    checkVectorManully(algorithmKNN);
                    break;
                case "3":
                    System.out.println("Wprowadz nowe K");
                    k = Integer.parseInt(new Scanner(System.in).nextLine());
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Uknown operation");
            }
        }
    }
    public static String findMostFrequent(String[] array) {
        HashMap<String, Integer> map = new HashMap<>();
        int maxCount = 0;
        String mostFrequentString = "";

        for (String str : array) {
            if (map.containsKey(str)) {
                int count = map.get(str) + 1;
                map.put(str, count);
                if (count > maxCount) {
                    maxCount = count;
                    mostFrequentString = str;
                }
            } else {
                map.put(str, 1);
            }
        }

        return mostFrequentString;
    }
    private static void checkAccuracyForTrainData(AlgorithmKNN algorithmKNN){
        ArrayList<MLVector> objects_data_test = new ArrayList<>();
        objects_data_test = parseCsvToArray(pathToTestData,DecisionParamaterIndex,",");
        int numberOfIterations = objects_data_test.size();
        int correct = 0;
        for(MLVector vec : objects_data_test) {
            System.out.println("For vec: " + vec + " and K = " + k);
            String wantedResult = vec.Name;
            String[] result = algorithmKNN.returnSmallestDistance(k, vec);
            String wynik = findMostFrequent(result);
            if(wynik.equals(wantedResult))
                correct++;
            System.out.println("Wynik " + wynik);
        }
        double accuracy = (double) (correct) / (numberOfIterations);
       accuracy *= 10000;
       accuracy =Math.round(accuracy) / 100;

        System.out.println("ACCURACY: " + accuracy + "%");
    }
    private static void checkVectorManully(AlgorithmKNN algorithmKNN){

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