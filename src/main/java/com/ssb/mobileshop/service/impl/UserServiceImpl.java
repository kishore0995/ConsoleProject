package com.ssb.mobileshop.service.impl;


import com.ssb.mobileshop.dao.impl.UserDaoImpl;
import com.ssb.mobileshop.model.User;
import com.ssb.mobileshop.service.UserService;

public class UserServiceImpl implements UserService {
        private static UserServiceImpl userServiceImpl;
        public static UserServiceImpl getInstance(){
            if(userServiceImpl==null){
                userServiceImpl=new UserServiceImpl();
            }
            return userServiceImpl;
        }
    // User Registration Validation    
    @Override
    public User RegisterValidation(User user) throws Exception {
     try{  if(user.getName().equals("")){
           System.out.println("User Should Not be Blank");
       }else if(user.getMobileNumber().equals("")){
           System.out.println("Mobile Number Should not be Blank");
       }else if(user.getPassword().equals("")){
           System.out.println("Password Should Not be bLank");
       }else if(user.getConfirmPassword().equals("")){
           System.out.println("Confirm Password Should not be blank");
       }else if(!user.getPassword().equals(user.getConfirmPassword())){
           System.out.println("Password and confirm password should be match");
       }
       else if(user.getMobileNumber().length()!=10){
           System.out.println("Enter Valid Mobile Number");
       }else{
       return user;
    }
}catch(NullPointerException e){
    System.out.println("Fields should not be blank");
}
       return null;
    }
    //Save user to Database
    @Override
    public int add(User user) throws Exception {
        return UserDaoImpl.getInstance().save(user);
    }
    // login validation of User
    @Override
    public User loginValidation(String mobile, String password) throws Exception {
            User login=UserDaoImpl.getInstance().findByMobileNumber(mobile, password);
            if(login.getMobileNumber().equals(mobile)&&login.getPassword().equals(password)){
                    return login;
            }else{
                return null;
            }
        }
    }
        
        
    
    

