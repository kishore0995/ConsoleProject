package com.ssb.mobileshop.controller;

import java.util.Scanner;

import com.ssb.mobileshop.MainFunction;
import com.ssb.mobileshop.view.ConsoleView;

public class AdminController {
    // static instance for AdminController
    private static AdminController loginController;

    public static AdminController getInstance() {
        if (loginController == null) {
            loginController = new AdminController();
        }
        return loginController;
    }

    Scanner scanner = new Scanner(System.in);

    // Option View for Admin
    public void option() {
        ConsoleView.phoneOption();
        System.out.println("Choose one option");
        String opt = scanner.nextLine();
        try {
            int option = Integer.parseInt(opt);
            switch (option) {
                case 1:
                    System.out.println("");
                    System.out.println("Add Phone");
                    PhoneController.getInstance().save();
                    break;
                case 2:
                    System.out.println("");
                    System.out.println("Edit Phone");
                    PhoneController.getInstance().edit();
                    break;
                case 3:
                    System.out.println("");
                    System.out.println("View Phone");
                    PhoneController.getInstance().viewMobiles();
                    break;
                case 4:
                    System.out.println("");
                    System.out.println("Delete Phone");
                    PhoneController.getInstance().remove();
                    break;
                case 5:
                    UserController.getInstance().option();
                    break;
                case 6:
                    System.out.println("Logout");
                    MainFunction.initialProcess();
                    break;
                default:
                    System.out.println("Enter Valid Option");
                    option();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Choose Valid Option");
            option();
        }
    }
}
