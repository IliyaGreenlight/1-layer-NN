public class Neuron {
    Vector weight;
    String value;

    public Neuron(String value) {
        this.value = value;
        weight = new Vector(26);
    }

    public double classify(Vector vec) {
        return weight.dotProduct(vec);
    }

    public void train(int desiredOutput, int accualOutput, double learningRate, Vector inputVector) {
        weight = weight.add(inputVector.multiply((desiredOutput - accualOutput) * learningRate));
    }
}
