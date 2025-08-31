import java.util.*;

public class DataSet {
    List<Neuron> neurons;
    FileReader reader;

    public DataSet(String trainingDirectoryName) {
        reader = new FileReader(trainingDirectoryName);
        neurons = new LinkedList<>();

        for (String language : reader.languages) {
            neurons.add(new Neuron(language));
        }
    }

    public List<Double> train(double alpha, int epoch) {
        List<List<String>> languageText = reader.getText();
        List<Double> result = new LinkedList<>();

        int totalTextCount = 0;

        for (List<String> langTextList : languageText) {
            totalTextCount += langTextList.size();
        }

        if (!languageText.isEmpty()) {
            for (int e = 0; e < epoch; e++) {
                int iterationCorrectAnswers = 0;

                for (int i = 0; i < languageText.stream().max(Comparator.comparingInt(List::size)).get().size(); i++) {

                    for (int i2 = 0; i2 < languageText.size(); i2++) {

                        if (languageText.get(i2).size() > i) {
                            String text = languageText.get(i2).get(i);
                            int[] out = classifyText(text);

                            for (int p = 0; p < neurons.size(); p++) {
                                neurons.get(p).train(p == i2 ? 1 : 0, out[p], alpha,
                                    Vector.textToVector(text, "unknown"));
                            }
                            if (out[i2] == 1) {
                                iterationCorrectAnswers++;
                            }
                        }
                    }
                }

                result.add((double) (iterationCorrectAnswers) / (double) (totalTextCount));
            }
        } else {
            System.out.println("training data is empty");
        }

        return result;
    }

    public String classify(String text) {
        int[] out = classifyText(text);

        for (int i = 0; i < out.length; i++) {
            if (out[i] == 1) {
                return reader.languages.get(i);
            }
        }

        return "Error occurred identifying language";
    }

    public int[] classifyText(String text) {
        Vector input = Vector.textToVector(text, "unknown");
        double[] output = new double[neurons.size()];
        int[] result = new int[output.length];

        int maxId = 0;
        double maxValue = 0;

        for (int i = 1; i < neurons.size(); i++) {
            double value = neurons.get(i).classify(input);

            if (neurons.get(i).classify(input) > maxValue) {
                maxId = i;
                maxValue = value;
            }
        }

        result[maxId] = 1;

        return result;
    }
}
