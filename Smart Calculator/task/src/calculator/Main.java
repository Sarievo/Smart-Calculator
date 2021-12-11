package calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Scanner s = new Scanner(System.in);
    static String[] errMsg = {
//            "Error! Such a file doesn't exist!",
//            "Incorrect file",
            "Invalid expression", "Unknown command", "Invalid assignment", "Unknown variable"
    };
    static List<String> commands = List.of(
            "/help"
    );
    static Pattern numberPattern = Pattern.compile("[-+]?\\d+");
    static Pattern operatorPattern = Pattern.compile("=|\\++|-+");
    static Pattern latinPattern = Pattern.compile("[a-zA-Z]+");
    static Pattern variablePattern = Pattern.compile("[-+]?\\d+|[a-zA-Z]+");
    static HashMap<String, Long> variables = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            String rawInput = s.nextLine().toLowerCase().trim();
            try {
                if ("".equals(rawInput.trim())) {
                    continue;
                } else if (rawInput.charAt(0) == '/') {
                    if ("/exit".equals(rawInput)) {
                        break;
                    } else if (commands.contains(rawInput)) {
                        if ("/help".equals(rawInput)) {
                            System.out.println("The program calculates the sum of numbers");
                            System.out.println("Supports binary minus: double-minus means plus");
                        }
                        continue;
                    }
                    err(1);
                    continue;
                } else if (rawInput.matches(".*\\d+[-+].*")) {
                    err(0);
                    continue;
                }

                List<Long> numbers = scanNumbers(rawInput);
                List<Character> operators = scanOperators(rawInput);
                List<String> names = scanLatinNames(rawInput);

                // check validity or assign
                if (!names.isEmpty()) {
                    if (!operators.isEmpty() && operators.get(0) == '=') {
                        if (operators.size() != 1 || names.size() + numbers.size() != 2) {
                            err(2);
                            continue;
                        }
                        if (numbers.size() == 0) {
                            if (!variables.containsKey(names.get(1))) {
                                err(3);
                                continue;
                            }
                            variables.put(names.get(0), variables.get(names.get(1)));
                        } else {
                            variables.put(names.get(0), numbers.get(0));
                        }
                        continue;
                    }

                    boolean isValid = true;
                    for (String name : names) {
                        if (!variables.containsKey(name)) {
                            err(3);
                            isValid = false;
                            break;
                        }
                    }
                    if (!isValid) {
                        continue;
                    }
                }

                List<Long> variables = scanVariables(rawInput);
                long result = variables.remove(0);
                while (!variables.isEmpty()) {
                    char operator = operators.remove(0);
                    if (operator == '+') {
                        result += variables.remove(0);
                    } else {
                        result -= variables.remove(0);
                    }
//                    System.out.print(result + "! ");
                }
//                System.out.println();
                System.out.println(result);

            } catch (Exception e) {
                e.printStackTrace();
                err(0);
            }
        }

        System.out.println("Bye!");
    }

    static List<Long> scanVariables(String s) {
        // scan all variables and map them to values
        List<Long> matchList = new ArrayList<>();
        Matcher regexMatcher = variablePattern.matcher(s);
        while (regexMatcher.find()) {
            String variable = regexMatcher.group();
            if (variables.containsKey(variable)) {
                matchList.add(variables.get(variable));
            } else {
                matchList.add(Long.parseLong(variable));
            }
        }
        return matchList;
    }

    static List<Long> scanNumbers(String s) {
        // only scan numbers
        List<Long> matchList = new ArrayList<>();
        Matcher regexMatcher = numberPattern.matcher(s);
        while (regexMatcher.find()) {
            matchList.add(Long.parseLong(regexMatcher.group()));
        }
        return matchList;
    }

    static List<Character> scanOperators(String s) {
        // only scan operator symbols
        List<Character> matchList = new ArrayList<>();
        Matcher regexMatcher = operatorPattern.matcher(s);
        while (regexMatcher.find()) {
            String fetched = regexMatcher.group();
            if (fetched.charAt(0) == '-' && fetched.length() % 2 == 0) {
                matchList.add('+');
            } else {
                matchList.add(regexMatcher.group().charAt(0));
            }
//            if (regexMatcher.group(1) != null) {
//            } else {
//                matchList.add(regexMatcher.group(0).charAt(0));
//            }
        }
//        System.out.println(matchList);
        return matchList;
    }

    static List<String> scanLatinNames(String s) {
        List<String> matchList = new ArrayList<>();
        Matcher regexMatcher = latinPattern.matcher(s);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }
        return matchList;
    }

    static void err(int i) {
        System.out.println(errMsg[i]);
    }
}
