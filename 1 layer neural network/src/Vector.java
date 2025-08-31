import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Vector {
    double[] parameters;
    String value;

    public Vector(String value, double... parameters) {
        this.parameters = parameters;
        this.value = value;
    }

    public Vector(double... parameters) {
        this.parameters = parameters;
        this.value = "";
    }

    public Vector(int paramNum) {
        this.parameters = new double[paramNum];
        Arrays.fill(parameters, 0.00001);
        this.value = "";
    }

    public double dotProduct(Vector vector) {
        if (vector.parameters.length == parameters.length) {
            double result = 0;

            for (int i = 0; i < parameters.length; i++) {
                result += vector.parameters[i] * parameters[i];
            }

            return result;
        } else {
            return 0.0;
        }
    }

    public Vector add(Vector vector) {
        if (vector.parameters.length == parameters.length) {
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] += vector.parameters[i];
            }
        }
        return this;
    }

    public Vector multiply(double number) {
        double[] newParameters = new double[this.parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            newParameters[i] = parameters[i] * number;
        }

        return new Vector(newParameters);
    }

    public double distance(Vector vector) {
        if (vector.parameters.length != parameters.length) {
            return -1;
        }

        double result = 0;

        for (int i = 0; i < parameters.length; i++) {
            result += Math.pow(parameters[i] - vector.parameters[i], 2);
        }

        return Math.sqrt(result);
    }

    public double length() {
        double result = 0;

        for (double parameter : parameters) {
            result += Math.pow(parameter, 2);
        }

        return Math.sqrt(result);
    }

    public Vector normalize() {
        double length = length();

        return new Vector(value, Arrays.stream(parameters).map(operand -> operand / length == 0 ? 1 : length).toArray());
    }

    public static Vector stringToVector(String str, String separator, boolean isValueGiven) {
        String[] values = str.split(separator);
        double[] parameters;

        if (isValueGiven) {
            parameters = Arrays.stream(values)
                .limit(values.length - 1)
                .mapToDouble(Double::valueOf).toArray();

            return new Vector(values[values.length - 1], parameters);
        } else {
            parameters = Arrays.stream(values)
                .mapToDouble(Double::valueOf).toArray();
            return new Vector(parameters);
        }
    }

    public static Vector textToVector(String text, String language) {
        Map<Integer, Integer> chars = new HashMap<>();
        int enLanChaNum = 26;
        int charNum = 0;

        for (int i = 0; i < text.length(); i++) {
            if ((64 < text.charAt(i) && text.charAt(i) < 91) || (96 < text.charAt(i) && text.charAt(i) < 123)) {
                Integer charId = text.charAt(i) < 91 ? text.charAt(i) + ('a' - 'A') : text.charAt(i);
                charId -= 'a';
                chars.put(charId, chars.getOrDefault(charId, -1) + 1);

                charNum++;
            }
        }

        double[] params = new double[enLanChaNum];

        for (int i = 0; i < enLanChaNum; i++) {
            params[i] = (double) (chars.getOrDefault(i, 0)) / charNum;
        }

        return new Vector(language, params);
    }

    @Override
    public String toString() {
        String result = "";

        for (double num : parameters) {
            result += num + " ";
        }

        result += value;

        return result;
    }
}
