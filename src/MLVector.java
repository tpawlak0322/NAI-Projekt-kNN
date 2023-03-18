public class MLVector {
    String Name;

    private int dimension;

    private double[] data;


    public MLVector(String Name, int dimensions) {

        data = new double[dimensions];
        this.dimension = dimensions;
        this.Name = Name;
    }
    public MLVector(String Name, double[] data) {

        this.data = data;
        this.dimension = data.length;
        this.Name = Name;
    }
    public MLVector add(double... Value){
    for(int i = 0; i < dimension; i++)
        data[i] = Value[i];
    return this;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < data.length;i++)
            sb.append((i+1) + ". " + data[i] + ", ");
        return Name +" attributes: " + sb;
    }
    public double[] getData() {
        return data;
    }
    public int getDimension() {
        return dimension;
    }
}
