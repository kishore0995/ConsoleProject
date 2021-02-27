package com.ssb.mobileshop.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ssb.mobileshop.model.Phone;

public interface PhoneService {
    int save(Phone phone)throws Exception;
    Phone phoneValidation(Phone phone)throws Exception;
    List<Phone> phone()throws Exception;
    int delete(int id)throws Exception;
    Phone edit(int id)throws Exception;
    public int editByStock(int stock,int id)throws Exception;
    public int editByPrice(float price,int id)throws Exception;
    Set<Phone> brandName()throws Exception;
    List<Phone> getByBrand(String brand)throws Exception;
    Map<String, Integer> getPrice(String modelname)throws Exception;
    List<Phone> getByRam(int ram)throws Exception;
    public void updateStock(int stock,String modelname)throws Exception;
    List<Phone> getByPrice(float price)throws Exception;
    Phone getDetailsByID(int id)throws Exception;
}
