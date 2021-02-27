package com.ssb.mobileshop.view;

import java.util.Scanner;

public class ConsoleView {
    static Scanner scanner = new Scanner(System.in);

    public static void phoneOption() {
        System.out.println("--------------------------------------------------------");
        System.out.println("choose 1.Add phone \t2.Edit phone \t3.View phone \t4.Delete Phone \t 5.User \t6.Logout");
        System.out.println();
    }

    public static void viewPhone() {
        System.out.println("--------------------------------------------------------");
        System.out.println("choose 1.View Brands \t2.View Phones \t3.View by RAM  \t4.Filter By Price \t5. Logout ");
        System.out.println();
    }

}
