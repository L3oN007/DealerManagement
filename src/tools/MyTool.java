/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.Scanner;

/**
 *
 * @author minhn
 */
public class MyTool {

    public static Scanner sc = new Scanner(System.in);

    public static int getInt(String mess) {
        while (true) {
            try {
                System.out.print(mess);
                int result = Integer.parseInt(sc.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input a number!!!");
            }
        }
    }

    public static String getString(String mess, String remindMess) {
        String id;
        while (true) {
            System.out.print(mess);
            id = sc.nextLine().trim();
            if (id.isEmpty() || id.length() == 0) {
                System.out.println(remindMess);
            } else {
                return id;
            }
        }
    }

    public static String getStringWithRegex(String mess, String remindMess, String regex) {
        while (true) {
            System.out.print(mess);
            String id = sc.nextLine();
            if (!id.matches(regex)) {
                System.out.println(remindMess);
                continue;
            }
            return id;
        }
    }

    public static boolean confirmYesNo(String mess) {
        while (true) {
            System.out.println(mess);
            String result = sc.nextLine();
            // check user input y/Y or n/N
            if (result.equalsIgnoreCase("Y")) {
                return true;
            } else if (result.equalsIgnoreCase("N")) {
                return false;
            }
            System.err.println("Please input y/Y or n/N.");
            System.out.print("Enter again: ");
        }
    }

    public static String getTrueFalse() {
        while (true) {
            System.out.print("(t/T for continuing and f/F for uncontinuing): ");
            String result = sc.nextLine();
            if (result.equalsIgnoreCase("T")) {
                return "true";
            } else if (result.equalsIgnoreCase("F")) {
                return "false";
            }
        }
    }
}
