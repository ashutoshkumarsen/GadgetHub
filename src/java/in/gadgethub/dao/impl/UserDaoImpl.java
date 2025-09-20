/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.UserPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;

/**
 *
 * @author DELL
 */
public class UserDaoImpl implements UserDao {

    

     
    public boolean isRegisterd(String emailId){
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        boolean flag=false;
        try{
            ps=conn.prepareStatement("select 1 from users where useremail=?");
            ps.setString(1,emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                flag=true;
                
            }
        }catch(SQLException ex){
            System.out.println("Error in isRegisterd "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
        return flag;
    }

    @Override
    public String registerUser(UserPojo user) {
        String status="Registeration failed";
        boolean isUserRegisterd=isRegisterd(user.getUseremail());
           if(isUserRegisterd){
               status= "User Is Already Registerd";
               return status;
           }else{
               Connection conn=DBUtil.provideConnection();
               PreparedStatement ps=null;
               try{
                   ps=conn.prepareStatement("insert into users values(?,?,?,?,?,?)");
                   ps.setString(1,user.getUseremail());
                   ps.setString(2,user.getUsername());
                   ps.setString(3, user.getMobile());
                   ps.setString(4, user.getAddress());
                   ps.setInt(5, user.getPincode());
                   ps.setString(6, user.getPassword());
                   int count=ps.executeUpdate();
                   if(count==1){
                       status="regestration successful now you can login ";
                       MailMessage.registrationSuccess(user.getUseremail(), user.getUsername());
                       System.out.println("mail sent successfully .......");
                   }
               }catch(SQLException | MessagingException ex){
            System.out.println("Error in isRegisterd "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
             return status;
           }
    }
    public String isValidCredentials(String emailId,String password){
       PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        String status="Login Denied. Invalid Username or Password";
        try{
               ps=conn.prepareStatement("Select * from users where useremail=? and password=?");
               ps.setString(1,emailId);
               ps.setString(2,password);
               rs=ps.executeQuery();
               if(rs.next()){
                   status="Login Succesful";      
                   }
               }
        catch(SQLException ex){
            status="Error:"+ex.getMessage();
            System.out.println("Error in isValidCredentials:"+ex);
            ex.printStackTrace();
        }       
       DBUtil.closeResultSet(rs);
       DBUtil.closeStatemant(ps);
       return status;
    }
    public UserPojo getUserDetails(String emailId){
        UserPojo user=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
        ps=conn.prepareStatement("select * from users where useremail=?");
            ps.setString(1,emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                user=new UserPojo();
                user.setUseremail(rs.getString("useremail"));
                user.setUsername(rs.getString("username"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                user.setPincode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
            }
    }catch(SQLException ex){
            
            System.out.println("Error in get user details"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
        return user;
    }
    public String getUserAddress(String emailId){
        String userAdd=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            ps=conn.prepareStatement("select address from users where useremail=?");
            ps.setString(1,emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                userAdd=rs.getString(1);
            }
        }catch(SQLException ex){
            
            System.out.println("Error in getting address"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
        return userAdd;
    }
    public String getUserFirstName(String emailId){
        String fname=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
        ps=conn.prepareStatement("select username from users where useremail=?");
            ps.setString(1,emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                String fullName=rs.getString(1);
                fname=fullName.split(" ")[0];
            }
    }catch(SQLException ex){
            
            System.out.println("Error in gettig user first name"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
        return fname;
}
}