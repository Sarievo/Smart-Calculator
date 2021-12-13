package calculator;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner s = new Scanner(System.in);
    static List<String> commands = List.of();

    public static void main(String[] args) {
        String[] params = input();
        
        while (!"/exit".equals(params[0])) {
            try {
                if (params.length == 1) {
                    System.out.println(Integer.parseInt(params[0]));
                } else if (params.length == 2) {
                    int n1 = Integer.parseInt(params[0]);
                    int n2 = Integer.parseInt(params[1]);
                    System.out.println(n1 + n2);
                }
            } catch (Exception ignored) {}
            params = input();
        }
        
        System.out.println("Bye!");
    }

    static String[] input() {
        return s.nextLine().split("\\s+");
    }
}
