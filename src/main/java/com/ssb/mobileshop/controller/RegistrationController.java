package com.ssb.mobileshop.controller;

import java.util.Scanner;

import com.ssb.mobileshop.MainFunction;
import com.ssb.mobileshop.model.User;
import com.ssb.mobileshop.service.impl.UserServiceImpl;

public class RegistrationController {
    Scanner sc = new Scanner(System.in);
    private static RegistrationController registrationController;
        //creating singleton instance for RegistrationController
    public static RegistrationController getInstance() {
        if (registrationController == null) {
            registrationController = new RegistrationController();
        }
        return registrationController;
    }

    //Getting user Details
    public User registerDetails() throws Exception {
        User user = new User();
        user.setRoleId(2);
        System.out.print("Enter User Name: ");
        user.setName(sc.nextLine());
        System.out.print("Enter Mobile Number: ");
        user.setMobileNumber(sc.nextLine());
        System.out.print("Enter Password: ");
        user.setPassword(sc.nextLine());
        System.out.print("Confirm Password: ");
        user.setConfirmPassword(sc.nextLine());
        return user;
    }
    //Method to Save User into Database
    public void add() {
        try {
            //calling registerDetails() method with user reference
              User user = registerDetails();
            
            //calling ReisterValidation from service 
            User addUser= UserServiceImpl.getInstance().RegisterValidation(user);
            
            if(addUser!=null){
               
                //Adding user details to database
                int adduser = UserServiceImpl.getInstance().add(user);
            
                if (adduser != 0) {
                    System.out.println("User Register Successfully");
                    MainFunction.initialProcess();
                } else {
                    System.out.println("Failed to create user");
                    System.out.println();
                    add();
                }               
            }else{
                System.out.println("Failed to create user");
                System.out.println();
                add();
            }
        } 
        catch (Exception e) {                
                e.printStackTrace();
            }
        }

}
