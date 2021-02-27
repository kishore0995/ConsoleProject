package com.ssb.mobileshop.controller;

import java.util.Scanner;

import com.ssb.mobileshop.MainFunction;
import com.ssb.mobileshop.model.User;
import com.ssb.mobileshop.service.impl.UserServiceImpl;
import com.ssb.mobileshop.view.ConsoleView;

public class UserController {
    Scanner sc = new Scanner(System.in);
    private static UserController userController;

    // singleton instance for UserController
    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    // Geting user login Details
    public User login(){
        User user = new User();
        System.out.print("Enter Mobile Number: ");
        user.setMobileNumber(sc.nextLine());
        System.out.print("Enter Password: ");
        user.setPassword(sc.nextLine());
        return user;
    }

    // User Login Validation
    public void operation() {
        User user = login();
        try {
            User userlogin = UserServiceImpl.getInstance().loginValidation(user.getMobileNumber(), user.getPassword());
            if (userlogin != null) {
                System.out.println(userlogin.getName());
                System.out.println(userlogin.getRoleId());
                switch (userlogin.getRoleId()) {
                    case 1:
                        System.out.println();
                        System.out.println("Welcome " + userlogin.getName());
                        AdminController.getInstance().option();
                        break;
                    case 2:
                        System.out.println();
                        System.out.println("Hi " + userlogin.getName() + " Welcome to Mobileshop");
                        UserController.getInstance().option();
                        break;
                    default:
                        System.out.println("Invalid Mobile Number or Password!!");
                        System.out.println();
                        operation();
                        break;
                }
            }
        }

        catch (Exception e) {
            System.out.println(e);
            System.out.println();
            System.out.println("Invalid Mobile Number or Password!!");
            System.out.println();
            operation();
        }

    }

    public void option() {
        System.out.println("Choose one Option");
        ConsoleView.viewPhone();
        String opt = sc.nextLine();
        try {
            int option = Integer.parseInt(opt);
            switch (option) {
                case 1:
                    System.out.println("");
                    System.out.println("view Brands");
                    PhoneController.getInstance().getBrandName();
                    break;
                case 2:
                    System.out.println("");
                    System.out.println("View Phones");
                    PhoneController.getInstance().viewPhones();
                    break;
                case 3:
                    System.out.println("");
                    System.out.println("View By Phones by Ram");
                    PhoneController.getInstance().viewByRam();
                    break;
                case 4:
                    System.out.println();
                    System.out.println("View By Price");
                    PhoneController.getInstance().viewByPrice();
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Are you Sure to Logout?");
                    System.out.println("Press y for logout else press any key to Continue");
                    String choose = sc.nextLine();
                    if (choose.equals("y") || choose.equals("Y")) {
                        System.out.println("Signing Out!!");
                        MainFunction.initialProcess();
                    } else {
                        option();
                    }
                    break;
                default:
                    System.out.println("Enter Valid Option");
                    option();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Enter Valid Option");
            option();
        }
    }
}
