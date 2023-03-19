import java.util.*;

public class AlgorithmKNN {
    private ArrayList<MLVector> objects_data;
    public AlgorithmKNN( ArrayList<MLVector> objects_data) {
        this.objects_data = objects_data;
    }

    public String[] returnSmallestDistance(int k, MLVector sourceVector){
        if(k > objects_data.size()) {
            System.out.println("Za duże K zmniejszam do rozmiaru przypadków. K = " + objects_data.size());
            k = objects_data.size();
        }
        MLVector[] vectors = objects_data.stream().sorted((x, y)-> Double.compare(checkDistance(x.getData(),sourceVector.getData()), checkDistance(y.getData(),sourceVector.getData()))).toArray(MLVector[]::new);
        String[] toReturn = new String[k];

//        System.out.println("Posortowane");
//        Arrays.stream(vectors).forEach(System.out::println);

        for(int i = 0; i < k;i++)
        toReturn[i] = vectors[i].Name;


    return toReturn;
    }

    private double checkDistance(double[] Values, double[] sourceValues){
        double Pow2Sum = 0;
        for(int i = 0; i < Values.length; i++){
            Pow2Sum += Math.pow(Values[i]-sourceValues[i], 2);
        }
//        System.out.println("Values " + Math.sqrt(Pow2Sum));
        return Math.sqrt(Pow2Sum);
    }
}


