package com.ssb.mobileshop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ssb.mobileshop.dao.impl.PhoneDaoImpl;
import com.ssb.mobileshop.model.Phone;
import com.ssb.mobileshop.service.PhoneService;

public class PhoneServiceImpl implements PhoneService {
    private static PhoneServiceImpl phoneServiceImpl;

    public static PhoneServiceImpl getInstance() {
        if (phoneServiceImpl == null) {
            phoneServiceImpl = new PhoneServiceImpl();
        }
        return phoneServiceImpl;
    }
    //Service for Saveing phone to Databse
    @Override
    public int save(Phone phone) throws Exception {
        return PhoneDaoImpl.getInstance().add(phone);
    }
    //Phone input Validation
    @Override
    public Phone phoneValidation(Phone phone) throws Exception {
        try {
            if (phone.getBrandName().equals("")) {
                System.out.println("Phone Brand Name Should Not be Blank");
                return null;
            }
            if (phone.getModelName().equals("")) {
                System.out.println("Model Name should not be blank");
                return null;
            } else {
                return phone;
            }

        } catch (Exception e) {
            System.out.println("Fields should not be blank");
            return null;
        }
    }
    //method to Show list of Phones
    @Override
    public List<Phone> phone()throws Exception{
            return PhoneDaoImpl.getInstance().phone();
    }

    //Method to Remove phone from database using id
    @Override
    public int delete(int id) throws Exception {
            return PhoneDaoImpl.getInstance().delete(id);
    }
    //Method to get Details of Phone using id
    @Override
    public Phone edit(int id)throws Exception{
        return PhoneDaoImpl.getInstance().getDetails(id);
    }
    // Method to Edit price Details
    @Override
    public int editByPrice(float price,int id)throws Exception{
        return PhoneDaoImpl.getInstance().editPrice(price, id);
    }
    //Method to Edit Available Stock
    @Override
    public int editByStock(int stock,int id)throws Exception{
        return PhoneDaoImpl.getInstance().editByStock(stock, id);
    }
    //Method to return List of Available Brands
    @Override
    public Set<Phone> brandName() throws Exception {
        return PhoneDaoImpl.getInstance().brandName();
    }
    //Get Details of Phone using Brand Name
    @Override
    public List<Phone> getByBrand(String brand) throws Exception {
      return PhoneDaoImpl.getInstance().getByBrand(brand);
    }
    //Method to Return Price and Stock using Model Name
    @Override
    public Map<String, Integer> getPrice(String modelname) throws Exception {
        return PhoneDaoImpl.getInstance().getPrice(modelname);
    }
    //Update Stock after User Buy a Phone
    @Override
    public void updateStock(int stock,String modelname)throws Exception{
        PhoneDaoImpl.getInstance().updateStock(stock, modelname);
    }
    //Filter Phone By Ram
    @Override
    public List<Phone> getByRam(int ram)throws Exception{
        return PhoneDaoImpl.getInstance().getByRam(ram);
    }

    @Override
    public List<Phone> getByPrice(float price) throws Exception {
        return PhoneDaoImpl.getInstance().getByPrice(price);
    }

    @Override
    public Phone getDetailsByID(int id) throws Exception {
       return PhoneDaoImpl.getInstance().getDetails(id);
    }
    public List<Phone> getByRamList(String brand,int Ram) throws Exception {
        return PhoneDaoImpl.getInstance().getByRamAndBrand(brand, Ram);
      }
}
