package com.ssb.mobileshop.service;

import com.ssb.mobileshop.model.User;

public interface UserService {
    public User RegisterValidation (User user) throws Exception;
    int add(User user)throws Exception;
    public User loginValidation(String mobile,String password)throws Exception;
}
