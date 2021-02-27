package com.ssb.mobileshop;

import java.sql.SQLException;
import java.util.Scanner;

import com.ssb.mobileshop.controller.RegistrationController;
import com.ssb.mobileshop.controller.UserController;

public class MainFunction {
        static Scanner sc = new Scanner(System.in);

        //intitial procees view in Console
        public static void initialProcess() {
                System.out.println("Enter any one 1.Login \t 2.Register User \t3.Exit");

                try {
                        String option = sc.nextLine();
                        int a = Integer.parseInt(option);
                        switch (a) {
                                case 1:
                                        System.out.println("Enter Details to login \n ");
                                        UserController.getInstance().operation();
                                        break;
                                case 2:
                                        System.out.println("Registration Form");
                                        RegistrationController.getInstance().add();
                                        break;
                                case 3:
                                        System.out.println("Thank You");
                                        System.exit(0);
                                        break;
                                default:
                                        System.out.println("Enter Valid option");
                                        MainFunction.initialProcess();
                                        break;
                        }
                } catch (Exception e) {
                        System.out.println("Enter Valid option");
                        MainFunction.initialProcess();
                }
        }

        //main Method
        public static void main(String[] args) throws SQLException {
                MainFunction.initialProcess();
        }
}
