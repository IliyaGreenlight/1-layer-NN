import java.util.List;
import java.util.Scanner;

public class Main {
    static DataSet data;
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        //Scanner reads dir and epochs
        String  dir = "train";
        double alpha = 0.1;
        int epoch = 10;

        data = new DataSet(dir);
        List<Double> resultList = data.train(alpha, epoch);

        for (int i = 0; i < resultList.size(); i++) {
            System.out.println((i+1) + ": " + resultList.get(i));
        }

        choice : while(true) {
            System.out.println("1. Test\n2. EXIT");
            int switchvar = scan.nextInt();
            switch (switchvar) {
                case 1:
                    System.out.println("Enter text to analyze: ");
                    String text;
                    while ((text = scan.nextLine()).isEmpty()) { }
                    System.out.println(data.classify(text));
                    break;
                case 2:
                    break choice;
                default:
                    break;
            }
        }
        scan.close();
    }
}