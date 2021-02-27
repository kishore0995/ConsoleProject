package com.ssb.mobileshop.controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import com.ssb.mobileshop.dao.impl.PhoneDaoImpl;
import com.ssb.mobileshop.model.Phone;
import com.ssb.mobileshop.service.impl.PhoneServiceImpl;

public class PhoneController {

    private static PhoneController phoneController;

    // creating singleton for PhoneController
    public static PhoneController getInstance() {
        if (phoneController == null) {
            phoneController = new PhoneController();
        }
        return phoneController;
    }

    Scanner scanner = new Scanner(System.in);

    // Getting Details of Phone to Add
    public Phone getDetails() {
        try {
            Phone phone = new Phone();
            System.out.print("Enter Phone Brand Name: ");
            phone.setBrandName(scanner.nextLine());
            System.out.print("Enter Model Name: ");
            phone.setModelName(scanner.nextLine());
            System.out.print("Enter price: ");
            phone.setPrice(scanner.nextFloat());
            System.out.print("Enter Stock: ");
            phone.setStock(scanner.nextInt());
            System.out.print("Enter Ram Size: ");
            phone.setRam(scanner.nextInt());
            System.err.print("Enter Rom Size: ");
            phone.setRom(scanner.nextInt());
            scanner.nextLine();
            return phone;
        } catch (InputMismatchException e) {
            System.out.println("Enter valid details: ");
            scanner.nextLine();
            System.out.println();
            getDetails();
            return null;
        }
    }

    // Add phone to Database
    public void save() {
        Phone phone = getDetails();
        if (phone != null) {
            try {
                Phone addMobile = PhoneServiceImpl.getInstance().phoneValidation(phone);
                if (addMobile != null) {
                    int save = PhoneDaoImpl.getInstance().add(addMobile);
                    if (save != 0) {
                        System.out.println("Phone Added Successfully");
                        System.out.println();
                        System.out.println("Do you Want to Add More press y to continue or press anything to go back");
                        String option = scanner.nextLine();
                        if (option.equals("y") || option.equals("Y")) {
                            save();
                        } else {
                            AdminController.getInstance().option();
                        }
                    } else {
                        System.out.println("Failed to Add Phone to Database");
                        save();
                    }
                } else {
                    System.out.println("Failed to Add phone to Database");
                    save();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Enter Valid Mobile details");
        }
    }

    // View Phones From Database for Admin
    public void viewPhone() {
        try {
            List<Phone> phone = PhoneServiceImpl.getInstance().phone();
            System.out.println("--------------------------Phones--------------------------");
            System.out.println("Id \tBrand_Name \tModel_Name \tRam \tRom \tPrice \t\tStock");
            if (phone.isEmpty()) {
                System.out.println("No phones");
            } else {
                for (Phone iteratePhone : phone) {
                    System.out.printf("%-7d %-15s %-10s %8d %8d    %.2f %10d \n", iteratePhone.getId(),
                            iteratePhone.getBrandName(), iteratePhone.getModelName(), iteratePhone.getRam(),
                            iteratePhone.getRom(), iteratePhone.getPrice(), iteratePhone.getStock());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // view Mobiles by Admin
    public void viewMobiles() {
        try {
            List<Phone> phone = PhoneServiceImpl.getInstance().phone();
            System.out.println("--------------------------Phones--------------------------");
            System.out.println("Id \tBrand_Name \tModel_Name \tRam \tRom \tPrice \t\tStock");
            if (phone.isEmpty()) {
                System.out.println("No phones");
            } else {
                for (Phone iteratePhone : phone) {
                    System.out.printf("%-7d %-15s %-10s %8d %8d    %.2f %10d \n", iteratePhone.getId(),
                            iteratePhone.getBrandName(), iteratePhone.getModelName(), iteratePhone.getRam(),
                            iteratePhone.getRom(), iteratePhone.getPrice(), iteratePhone.getStock());
                }
                AdminController.getInstance().option();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Remove Phone from Database
    public void remove() {
        viewPhone();
        try {
            List<Phone> phone = PhoneServiceImpl.getInstance().phone();
            if (phone.isEmpty()) {
                System.out.println("No phones");
            } else {
                System.out.println("Enter phone_Id");
                int phoneId = scanner.nextInt();
                int staus = PhoneServiceImpl.getInstance().delete(phoneId);
                if (staus != 0) {
                    System.out.println("Phone Removed Successfully");
                } else {
                    System.out.println("Failed to Remove Phone");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Edit price and Stock of Phone
    public void edit() {
        viewPhone();
        try {
            // Get list of phone in Database
            List<Phone> phone = PhoneServiceImpl.getInstance().phone();
            if (phone.isEmpty()) {
                System.out.println("No phones");
            } else {
                System.out.println("Enter phone_Id");
                int phoneId = scanner.nextInt();
                Phone editPhone = new Phone();
                editPhone = PhoneServiceImpl.getInstance().edit(phoneId);
                scanner.nextLine();
                if (editPhone.getId() != 0) {
                    System.out.println("Id \tBrand_Name \tModel_Name \tRam \tRom \tPrice \t\tStock");
                    System.out.printf("%-7d %-15s %-10s %8d %8d    %.2f %10d \n", editPhone.getId(),
                            editPhone.getBrandName(), editPhone.getModelName(), editPhone.getRam(), editPhone.getRom(),
                            editPhone.getPrice(), editPhone.getStock());
                    // Get option to change Stock or Price
                    System.out.println("Enter option 1.Change Price \t2.Change Stock");
                    String option = scanner.nextLine();
                    if (option.equals("1") || option.equals("2")) {
                        int choose = Integer.parseInt(option);
                        switch (choose) {

                            // case 1 is to change Price
                            case 1:
                                System.out.println("Enter Price:");
                                float price = scanner.nextFloat();
                                int status = PhoneServiceImpl.getInstance().editByPrice(price, phoneId);
                                if (status != 0) {
                                    System.out.println("Updated Successfully");
                                    System.out.println();
                                    System.out.println("Do you Want to update More choose y/n");
                                    char option1 = scanner.next().charAt(0);
                                    if (option1 == 'y' || option1 == 'Y') {
                                        scanner.nextLine();
                                        edit();
                                    } else {
                                        scanner.nextLine();
                                        AdminController.getInstance().option();
                                    }
                                } else {
                                    scanner.nextLine();
                                    System.out.println("Failed to update");
                                    AdminController.getInstance().option();
                                }
                                break;
                            // case 2 is for Changing Stock
                            case 2:
                                System.out.println("Enter Stock to Update:");
                                int stock = scanner.nextInt();
                                int returnstatus = PhoneServiceImpl.getInstance().editByStock(stock, phoneId);
                                if (returnstatus != 0) {
                                    System.out.println("Updated Successfully");
                                    System.out.println();
                                    System.out.println("Do you Want to update More choose y/n");
                                    char option1 = scanner.next().charAt(0);
                                    if (option1 == 'y' || option1 == 'Y') {
                                        scanner.nextLine();
                                        edit();
                                    } else {
                                        AdminController.getInstance().option();
                                    }
                                }
                        }
                    }
                } else {
                    System.out.println("Enter Id Which is shown on Display");
                    edit();
                }
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("-------------------------");
            System.out.println("Enter only Numbers");
            edit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Get Phone details by Brands
    public void getBrandName() {
        try {
            Set<Phone> brandName = PhoneServiceImpl.getInstance().brandName();
            if (brandName.isEmpty()) {
                System.out.println("No Phones Available");
            } else {
                System.out.println("----------------------------------");
                System.out.println("Available Brands");
                System.out.println("----------------------------------");
                for (Phone brand : brandName) {
                    System.out.printf("%s\n", brand.getBrandName());
                }
                chooseByBrand();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buy Mobile with Brand Name and Model Name
    public void chooseByBrand() throws Exception {
        System.out.println("Enter Available Brand Name");
        String brand = scanner.nextLine();
        List<Phone> byBrand = PhoneServiceImpl.getInstance().getByBrand(brand);
        if (byBrand.isEmpty()) {
            System.out.println("Selected Brand Not Available");
            chooseByBrand();
        } else {
            System.out.println("-------------------------------------------");
            System.out.println("Brand_Name \tModel_Name \tRam \tRom \tPrice");
            for (Phone phone : byBrand) {
                System.out.printf("%-15s %-10s %7d %7d     %.2f\n", phone.getBrandName(), phone.getModelName(),
                        phone.getRam(), phone.getRom(), phone.getPrice());
            }
            System.out.println("press y to change brand or b to go back else press any key to buy Mobile");
            String option = scanner.nextLine();
            if (option.equals("y") || option.equals("Y")) {
                chooseByBrand();
            } else if (option.equals("b") || option.equals("B")) {
                UserController.getInstance().option();
            } else {
                System.out.println("Choose model_Name to Buy");
                String modelName = scanner.nextLine();
                int stock = 0;
                float price = 0.0f;
                Map<String, Integer> priceDetails = PhoneServiceImpl.getInstance().getPrice(modelName);
                for (Entry<String, Integer> entry : priceDetails.entrySet()) {
                    price = Float.parseFloat(entry.getKey());
                    stock = entry.getValue();
                }
                if (price != 0.0 && stock != 0) {
                    System.out.println("Do You Wish to Buy Y/N");
                    String opt = scanner.nextLine();
                    if (opt.equals("y") || opt.equals("Y") || opt.equals("Yes") || opt.equals("yes")) {
                        System.out.println("Total price: " + price);
                        System.out.println("Thank You For Purchasing In Mobile Shop \n \n");
                        stock = stock - 1;
                        PhoneServiceImpl.getInstance().updateStock(stock, modelName);
                        UserController.getInstance().option();
                    } else {
                        UserController.getInstance().option();
                    }
                } else if (price != 0.0 && stock == 0) {
                    System.out.println("Soryy!!!! Stock is Not Available");
                    UserController.getInstance().option();
                } else {
                    System.out.println("choose valid model name");
                    UserController.getInstance().option();
                }
            }
        }
    }

    // View Available Phones in Shop
    public void viewPhones() {
        try {
            List<Phone> phone = PhoneServiceImpl.getInstance().phone();
            System.out.println("-------------Available Phones in our Shop----------------");
            System.out.println("Id \tBrand_Name \tModel_Name \tRam \tRom \tPrice");
            if (phone.isEmpty()) {
                System.out.println("No phones");
            } else {

                for (Phone iteratePhone : phone) {
                    System.out.printf("%-7d %-15s %-10s %8d %8d    %.2f  \n", iteratePhone.getId(),
                            iteratePhone.getBrandName(), iteratePhone.getModelName(), iteratePhone.getRam(),
                            iteratePhone.getRom(), iteratePhone.getPrice());
                }
                chooseByBrand();
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            UserController.getInstance().option();
        }
    }

    // Filter Phone by RAM size
    public void viewByRam() {
        try {
            System.out.println("Enter Ram Details You are Looking for:");
            String RAM = scanner.nextLine();
            int ram = Integer.parseInt(RAM);
            List<Phone> phone = PhoneServiceImpl.getInstance().getByRam(ram);
            if (phone.isEmpty()) {
                System.out.println("No phones Available for the Specific Ram :" + ram + "");
                viewByRam();
            } else {
                System.out.println("-------------Available Phones in our Shop----------------");
                System.out.println("Brand_Name \tModel_Name \tRam \tRom \tPrice");
                for (Phone iteratePhone : phone) {
                    System.out.printf("%-15s %-10s %8d %8d    %.2f  \n", iteratePhone.getBrandName(),
                            iteratePhone.getModelName(), iteratePhone.getRam(), iteratePhone.getRom(),
                            iteratePhone.getPrice());
                }
                System.out.println("Enter Available Brand Name");
                String brand = scanner.nextLine();
                List<Phone> byBrand = PhoneServiceImpl.getInstance().getByRamList(brand, ram);
                if (byBrand.isEmpty()) {
                    System.out.println("Selected Brand Not Available");
                    viewByRam();
                } else {
                    System.out.println("-------------------------------------------");
                    System.out.println("Brand_Name \tModel_Name \tRam \tRom \tPrice");
                    for (Phone phones : byBrand) {
                        System.out.printf("%-15s %-10s %7d %7d     %.2f\n", phones.getBrandName(),
                                phones.getModelName(), phones.getRam(), phones.getRom(), phones.getPrice());
                    }
                    System.out.println(
                            "press 1 to change brand or 2 to go back to Option page else press any key to buy Mobile");
                    String option = scanner.nextLine();
                    if (option.equals("1") || option.equals("y")) {
                        chooseByBrand();
                    } else if (option.equals("2") || option.equals("b")) {
                        UserController.getInstance().option();
                    } else {
                        System.out.println("Choose model_Name to Buy");
                        String modelName = scanner.nextLine();
                        int stock = 0;
                        float price = 0.0f;
                        Map<String, Integer> priceDetails = PhoneServiceImpl.getInstance().getPrice(modelName);
                        for (Entry<String, Integer> entry : priceDetails.entrySet()) {
                            price = Float.parseFloat(entry.getKey());
                            stock = entry.getValue();
                        }
                        if (price != 0.0 && stock != 0) {
                            System.out.println("Do You Wish to Buy Y/N");
                            String opt = scanner.nextLine();
                            if (opt.equals("y") || opt.equals("Y") || opt.equals("Yes") || opt.equals("yes")) {
                                System.out.println("Total price: " + price);
                                System.out.println("Thank You For Purchasing In Mobile Shop \n");
                                stock = stock - 1;
                                PhoneServiceImpl.getInstance().updateStock(stock, modelName);
                                UserController.getInstance().option();
                            } else {
                                UserController.getInstance().option();
                            }
                        } else if (price != 0.0 && stock == 0) {
                            System.out.println("Soryy!!!! Stock is Not Available");
                            UserController.getInstance().option();
                        } else {
                            System.out.println("choose valid model name");
                            UserController.getInstance().option();
                        }
                    }
                }

            }

        } catch (NumberFormatException e) {
            System.out.println("Enter only Numbers");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            UserController.getInstance().option();
        }
    }

    // Filter Phone by Orice
    public void viewByPrice() {
        try {
            System.out.println("Enter the price range you Want:");
            String priceRange = scanner.nextLine();
            float pricerange = Float.parseFloat(priceRange);
            // Get phone Details by Price
            List<Phone> phone = PhoneServiceImpl.getInstance().getByPrice(pricerange);
            if (phone.isEmpty()) {
                System.out.println("Sorry Selected Price Range is Not Available");
                viewByPrice();
            } else {
                System.out.println("-------------Available Phones in our Shop----------------");
                System.out.println("Id \tBrand_Name \tModel_Name \tRam \tRom \tPrice");
                // Add all phone id into Array List to compare
                List<Integer> ID = new ArrayList<Integer>();
                for (Phone iteratePhone : phone) {
                    System.out.printf("%-7d %-15s %-10s %8d %8d    %.2f  \n", iteratePhone.getId(),
                            iteratePhone.getBrandName(), iteratePhone.getModelName(), iteratePhone.getRam(),
                            iteratePhone.getRom(), iteratePhone.getPrice());
                    ID.add(iteratePhone.getId());
                }
                System.out.println("Enter ID to Buy Mobile: ");
                String getId = scanner.nextLine();
                int id = Integer.parseInt(getId);

                // This Condition checks whether given id is present or not
                if (ID.contains(id)) {
                    Phone phoneDetailsByID = PhoneServiceImpl.getInstance().getDetailsByID(id);
                    if (phoneDetailsByID != null) {
                        System.out.println("Id \tBrand_Name \tModel_Name \tRam \tRom \tPrice");
                        System.out.printf("%-7d %-15s %-10s %8d %8d    %.2f  \n", phoneDetailsByID.getId(),
                                phoneDetailsByID.getBrandName(), phoneDetailsByID.getModelName(),
                                phoneDetailsByID.getRam(), phoneDetailsByID.getRom(), phoneDetailsByID.getPrice());
                        System.out.println("Do You Wish to Buy Y/N");
                        String opt = scanner.nextLine();
                        int stock = phoneDetailsByID.getStock();
                        if (opt.equals("y") || opt.equals("Y") || opt.equals("Yes") || opt.equals("yes")) {
                            System.out.println("Total price: " + phoneDetailsByID.getPrice());
                            System.out.println("Thank You For Purchasing In Mobile Shop \n \n");
                            stock = stock - 1;
                            PhoneServiceImpl.getInstance().updateStock(stock, phoneDetailsByID.getModelName());
                            UserController.getInstance().option();
                        } else {
                            UserController.getInstance().option();
                        }
                    } else {
                        System.out.println("Enter Valid ID");
                        viewByPrice();
                    }
                } else {
                    System.out.println("Enter Valid Id");
                    viewByPrice();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter only Numbers");
            viewByPrice();
        } catch (NumberFormatException e) {
            System.out.println("Enter only Numbers");
            viewByPrice();
        } catch (Exception e) {
            System.out.println("No Phones Available");
            viewByPrice();
        }
    }
}
