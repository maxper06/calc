

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        //boolean exit = false;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("Введите строку для расчета: ");
            String str = input.nextLine();
            System.out.println(calc(str));
        }
    }

    public static String calc(String input) throws IOException {
        String result = "";
        input = input.replaceAll("\\s", "");
        if (input.matches("(.*)exit(.*)")) {
            System.exit(1);
        }

        boolean accord = input.matches("[IVX0123456789+\\-*/]+");
        if (!accord) {
            throw new IOException();
        }

        char[] simbols = new char[4];
        simbols[0] = '+';
        simbols[1] = '-';
        simbols[2] = '*';
        simbols[3] = '/';

        for (char s : simbols){
            if (countSimbol(input, s) > 1){
                //System.out.println("Введено больше одного знака " + s);
                //System.exit(1);
                throw new IOException();
            }
        }

        String ch = "/|\\*|\\+|-";
        Matcher matcher = Pattern.compile(ch).matcher(input);
        while (matcher.find()) {
            if (matcher.start() != 1) {
                if (matcher.start() != 2) {
                    if (matcher.start() != 3) {
                        if (matcher.start() != 4) {
                            //System.out.println("Строка не соответствует формату по знаку");
                            //System.exit(1);
                            throw new IOException();
                        }
                    }
                }
            }
        }

        boolean[] operation = new boolean[4];
        if (input.contains("+")) {
            operation[0] = true;
        }
        if (input.contains("-")) {
            operation[1] = true;
        }
        if (input.contains("*")) {
            operation[2] = true;
        }
        if (input.contains("/")) {
            operation[3] = true;
        }
        int i = 0;
        for (boolean op : operation) {
            if (op) {
                i = i + 1;
            }
        }
        if (i > 1) {
            //System.out.println("Строка имеет более одного оператора");
            //System.exit(1);
            throw new IOException();
        }
        if (i == 0) {
            //System.out.println("Строка не имеет операторов");
            //System.exit(1);
            throw new IOException();
        }

        String[] numbers = input.split(("/|\\*|\\+|-"));
        String number1 = numbers[0];
        String number2 = numbers[1];
        boolean rimNum1 = false;
        boolean rimNum2 = false;
        boolean arNum1 = false;
        boolean arNum2 = false;

        if (equality(number1, number2)){
            throw new IOException();
        }

        if (number1.matches("\\d+")) {
            arNum1 = true;
        }
        if (number2.matches("\\d+")) {
            arNum2 = true;
        }
        if (arNum1 == true && arNum2 == true) {
            int oper = 0;
            for (boolean op : operation) {
                if (op) {
                    break;
                }
                oper++;
            }
            if ((Integer.parseInt(number1) <= 10) && (Integer.parseInt(number2) <= 10)) {
                int resultAr = res(Integer.parseInt(number1), Integer.parseInt(number2), oper);
                result = String.valueOf(resultAr);
            } else {
                throw new IOException();
            }
        }
        //

        if (number1.matches("[IVX]+")) {
            rimNum1 = true;
        }
        if (number2.matches("[IVX]+")) {
            rimNum2 = true;
        }
        if (rimNum1 == true && rimNum2 == true) {
            try {
                NumToAr numbers1 = NumToAr.valueOf(number1);
                if (numbers1 != null) {
                    number1 = numbers1.getTranslation();
                }

                NumToAr numbers2 = NumToAr.valueOf(number2);
                if (numbers2 != null) {
                    number2 = numbers2.getTranslation();
                }


                int oper = 0;
                for (boolean op : operation) {
                    if (op) {
                        break;
                    }
                    oper++;
                }

                int resultRim = res(Integer.parseInt(number1), Integer.parseInt(number2), oper);
                result = numToRim(resultRim);
                //if (result == "") {
                //    System.out.println("Не верный результат");
                //    System.exit(1);
                //}
            } catch (IllegalArgumentException e) {
                //System.out.println("Введено значение больше 10");
                //System.exit(1);
                throw new IllegalArgumentException();
            }
        }

        return result;
    }

    public static boolean equality(String number1, String number2){
        boolean eq = false;
        boolean numIsAr = false;
        boolean numIsAr2 = false;
        if (number1.matches("\\d+")) {
            numIsAr = true;
        }
        if (number2.matches("\\d+")) {
            numIsAr2 = true;
        }
        if (numIsAr != numIsAr2){
            eq = true;
        }
        return eq;
    }




    public static int res(int num, int num2, int operation) {
        int num3 = 0;
        switch (operation) {
            case (0):
                num3 = num + num2;
                break;
            case (1):
                num3 = num - num2;
                break;
            case (2):
                num3 = num * num2;
                break;
            case (3):
                try {
                    num3 = num / num2;
                } catch (ArithmeticException e) {
                    throw new ArithmeticException();
                }
                break;
        }
        return num3;
    }

    private static long countSimbol(String str, char ch) {
        return str.chars().filter(c -> c == ch).count();
    }

    private static String numToRim(int num) {
        String[] roman = {"O", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
        };
        try {
            final String s = roman[num];
            return s;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new ArrayIndexOutOfBoundsException();
            //String s = "";
            //return s;
        }
    }
}

