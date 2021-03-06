package com.ssb.mobileshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ssb.mobileshop.dao.phoneDao;
import com.ssb.mobileshop.model.Phone;
import com.ssb.mobileshop.util.JdbcConnection;

public class PhoneDaoImpl implements phoneDao {
    private static PhoneDaoImpl phoneDaoImpl;

    // create singleton for PhoneDaoImpl
    public static PhoneDaoImpl getInstance() {
        if (phoneDaoImpl == null) {
            phoneDaoImpl = new PhoneDaoImpl();
        }
        return phoneDaoImpl;
    }

    // list Phone
    @Override
    public List<Phone> phone() throws Exception {
        List<Phone> phone = new ArrayList<Phone>();
        String sql = "select * from phone";
        Connection con = JdbcConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Phone phoneDetails = new Phone();
            phoneDetails.setId(rs.getInt(1));
            phoneDetails.setBrandName(rs.getString(2));
            phoneDetails.setModelName(rs.getString(3));
            phoneDetails.setPrice(rs.getFloat(4));
            phoneDetails.setStock(rs.getInt(7));
            phoneDetails.setRam(rs.getInt(5));
            phoneDetails.setRom(rs.getInt(6));
            phone.add(phoneDetails);
        }
        return phone;
    }

    // add phone to database
    @Override
    public int add(Phone phone) throws Exception {
        String sql = "insert into phone (brand_name,model_name,price,stock,ram_size,rom_size) values (?,?,?,?,?,?)";
        Connection con = JdbcConnection.getConnection();
        int insertStatus = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, phone.getBrandName());
            preparedStatement.setString(2, phone.getModelName());
            preparedStatement.setFloat(3, phone.getPrice());
            preparedStatement.setInt(4, phone.getStock());
            preparedStatement.setInt(5, phone.getRam());
            preparedStatement.setInt(6, phone.getRom());
            insertStatus = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            con.close();
        }
        return insertStatus;
    }

    // Remove phone from database
    @Override
    public int delete(int phone_id) throws Exception {
        String sql = "delete from phone where id=" + phone_id + "";
        Connection con = JdbcConnection.getConnection();
        int status = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            status = ps.executeUpdate();
            return status;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        } finally {
            con.close();
        }

    }

    // Getting Phone details By Id:
    @Override
    public Phone getDetails(int phone_id) throws Exception {
        String sql = "select * from phone where id=" + phone_id + "";
        Connection con = JdbcConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Phone phone = new Phone();
            while (rs.next()) {
                phone.setBrandName(rs.getString(2));
                phone.setModelName(rs.getString(3));
                phone.setPrice(rs.getFloat(4));
                phone.setStock(rs.getInt(7));
                phone.setRam(rs.getInt(5));
                phone.setRom(rs.getInt(6));
                phone.setId(rs.getInt(1));
            }
            return phone;
        } catch (Exception e) {
            return null;
        } finally {
            con.close();
        }

    }

    // Update Price Details of phone
    @Override
    public int editPrice(float price, int id) throws SQLException {
        String sql = "update phone set price=" + price + " where id=" + id + "";
        Connection con = JdbcConnection.getConnection();
        int status = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            status = ps.executeUpdate();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            con.close();
        }
    }

    // upfate Stock Details of Phone
    @Override
    public int editByStock(int stock, int id) throws Exception {
        String sql = "update phone set stock=" + stock + " where id=" + id + "";
        Connection con = JdbcConnection.getConnection();
        int status = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            status = ps.executeUpdate();
            return status;
        } catch (Exception e) {
            return 0;
        } finally {
            con.close();
        }
    }

    //Filtering Phone only by Brand
    @Override
    public Set<Phone> brandName() throws Exception {
        Set<Phone> brand_name = new HashSet<Phone>();
        String sql = "select brand_name from phone";
        Connection con = JdbcConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Phone brand = new Phone();
                brand.setBrandName(rs.getString(1));
                brand_name.add(brand);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Set<Phone> brandname = new HashSet<Phone>(brand_name);
        return brandname;
    }

    //Getting Phone Details by Brand Name
    @Override
    public List<Phone> getByBrand(String brand) throws Exception {
        List<Phone> brandDteails = new ArrayList<Phone>();
        String sql = "select * from phone where brand_name like '%"+brand+"%'";
        Connection con = JdbcConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                Phone phoneDetails = new Phone();
                phoneDetails.setBrandName(rs.getString(2));
                phoneDetails.setModelName(rs.getString(3));
                phoneDetails.setPrice(rs.getFloat(4));
                phoneDetails.setRam(rs.getInt(5));
                phoneDetails.setRom(rs.getInt(6));
                brandDteails.add(phoneDetails);
            }
            return brandDteails;

        } catch (Exception e) {

        }
        return null;
    }

    //Geting price details and stock by Model Name
    @Override
    public Map<String,Integer> getPrice(String modelname) throws Exception {
        String sql = "select price,stock from phone where model_name='" + modelname + "'";
        Connection con = JdbcConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        Map<String,Integer> buyPhone=new HashMap<String,Integer>();
        String price=null;
        int stock=0;
        try {
            while(rs.next()){
                price=Float.toString(rs.getFloat("price"));
                stock=rs.getInt("stock");
                buyPhone.put(price, stock);
            }

        } catch (Exception e) {
            return null;
        }finally{
            con.close();
        }
        return buyPhone;
    }

    //Update stock after Buying a phone
    @Override
    public void updateStock(int stock,String modelname)throws Exception {
       String sql="update phone set stock="+stock+" where model_name='"+modelname+"'";
       Connection con=JdbcConnection.getConnection();
       PreparedStatement ps=con.prepareStatement(sql);
       try{
           ps.executeUpdate();
       }catch(Exception e){
           System.out.println(e);
       }finally{
           con.close();
       }
    }
    //Get phone Details by RAM
    @Override
    public List<Phone> getByRam(int ram)throws Exception{
        String sql="select * from phone where ram_size="+ram+"";
        Connection con=JdbcConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(sql);
        List<Phone> phone=new ArrayList<Phone>();
        try{
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Phone phoneDetails = new Phone();
                phoneDetails.setBrandName(rs.getString(2));
                phoneDetails.setModelName(rs.getString(3));
                phoneDetails.setPrice(rs.getFloat(4));
                phoneDetails.setRam(rs.getInt(5));
                phoneDetails.setRom(rs.getInt(6));
                phone.add(phoneDetails);
            }

        }catch (Exception e){
            return null;
        }finally{
            con.close();
        }
        return phone;
    }

    @Override
    public List<Phone> getByPrice(float price) throws Exception {
        String sql="select * from phone where price <"+price+"";
        Connection con=JdbcConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(sql);
        List<Phone> byPrice=new ArrayList<Phone>();
        try {
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Phone phone=new Phone();
                phone.setId(rs.getInt(1));
                phone.setBrandName(rs.getString(2));
                phone.setModelName(rs.getString(3));
                phone.setPrice(rs.getFloat(4));
                phone.setRam(rs.getInt(5));
                phone.setRom(rs.getInt(6));
                byPrice.add(phone);
            }
            return byPrice;
        } catch (Exception e) {
            return null;
        }
    }
    //Get details of phone by both Ram and Brand Name 
    @Override
    public List<Phone> getByRamAndBrand(String brand,int ram) throws Exception {
        List<Phone> brandDteails = new ArrayList<Phone>();
        String sql = "select * from phone where brand_name like '%"+brand+"%' And ram_size="+ram+"";
        Connection con = JdbcConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                Phone phoneDetails = new Phone();
                phoneDetails.setBrandName(rs.getString(2));
                phoneDetails.setModelName(rs.getString(3));
                phoneDetails.setPrice(rs.getFloat(4));
                phoneDetails.setRam(rs.getInt(5));
                phoneDetails.setRom(rs.getInt(6));
                brandDteails.add(phoneDetails);
            }
            return brandDteails;

        } catch (Exception e) {

        }
        return null;
    }
}