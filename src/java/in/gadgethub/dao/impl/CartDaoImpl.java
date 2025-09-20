/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class CartDaoImpl implements CartDao{
    
    @Override
    public String addProductToCart(CartPojo cart) {
        PreparedStatement ps1=null;
        ResultSet rs=null;
        String status="Failed to add to cart";
        Connection conn=DBUtil.provideConnection();
        try{
                ps1=conn.prepareStatement("select * from usercart where useremail=? and prodid=?");
                ps1.setString(1,cart.getUseremail());
                ps1.setString(2,cart.getProdId());
                rs=ps1.executeQuery();
                if(rs.next()){
                    ProductDaoImpl prod=new ProductDaoImpl();
                    int stockQty=prod.getProductQuantity(cart.getProdId());
                    int newQty= cart.getQuantity()+rs.getInt("quentity");
                    if(stockQty<newQty){
                         cart.setQuantity(newQty);
                        updateProductInCart(cart);
                        status="Only "+stockQty+" no of items available in our Stock so we are adding "+stockQty+" in your cart0";
                        DemandPojo demandpojo=new DemandPojo();
                        demandpojo.setProdId(cart.getProdId());
                        demandpojo.setUseremail(cart.getUseremail());
                        demandpojo.setDemandQuantity(newQty-stockQty);
                        DemandDaoImpl demandDao=new DemandDaoImpl();
                        boolean result=demandDao.addProduct(demandpojo);
                        if(result==true){
                            status="we Will mail you when"+(newQty-stockQty)+"o of items will be available";
                        }
                    }else{
                        cart.setQuantity(newQty);
                        status=updateProductInCart(cart);
                    }
                }
            
            }catch(SQLException ex){
            status="Product Addition failed due to Exception";
            System.out.println("Error in updateProdctInCart "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps1);
        return status;
    }


    @Override
    public String updateProductInCart(CartPojo cart) {
        String status ="Failed to add into cart";
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
        
        Connection conn=DBUtil.provideConnection();
        try{
            ps1=conn.prepareStatement("select * from usercart where prodid=? and useremail=?");
            ps1.setString(1,cart.getProdId());
            ps1.setString(2,cart.getUseremail());
            rs=ps1.executeQuery();
            if(rs.next()){
                int qty=cart.getQuantity();
                if(qty>0){
                     ps2=conn.prepareStatement("update usercart set quentity=? where prodid=?and useremail=?");  
                     ps2.setInt(1,cart.getQuantity());
                     ps2.setString(2,cart.getProdId());
                     ps2.setString(3,cart.getUseremail());
                     int ans=ps2.executeUpdate();
                      if(ans>0){
                         status="Product Successfully updated to cart";
                      }
                }else if(qty==0){
                     ps2=conn.prepareStatement("delete from usercart where prodid=?and useremail=? ");  
        
                     ps2.setString(1,cart.getProdId());
                     ps2.setString(2,cart.getUseremail());
                     int ans=ps2.executeUpdate();
                      if(ans>0){
                         status="Product Successfully updated to cart";
                      }else{
                          status="could not update the product";
                      }
                }
            }else{
              ps2=conn.prepareStatement("insert into usercart values(?,?,?)");  
              ps2.setString(1,cart.getUseremail());
              ps2.setString(2,cart.getProdId());
              ps2.setInt(3,cart.getQuantity());
              int ans=ps2.executeUpdate();
              if(ans>0){
                  status="Product Successfully added to cart";
              }
            }
        }catch(SQLException ex){
            status="Product updation failed due to Exception";
            System.out.println("Error in updateProdctInCart "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps1);
        DBUtil.closeStatemant(ps2);
        return status;
    }

    @Override
    public List<CartPojo> getAllCartItems(String userId) {
        List<CartPojo> itemList =new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
         PreparedStatement ps=null;
         ResultSet rs=null;
         try{
             ps=conn.prepareStatement("select * from usercart where useremail=?");
             ps.setString(1, userId);
             rs=ps.executeQuery();
             while(rs.next()){
                 CartPojo cart=new CartPojo();
                 cart.setUseremail(rs.getString("useremail"));
                 cart.setProdId(rs.getString("prodid"));
                 cart.setQuantity(rs.getInt("quantity"));
                 itemList.add(cart);
                 
             }
         }catch(SQLException ex){
            System.out.println("Error in  getAllCartItems"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
        return itemList;
        
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        if(userId==null||itemId==null){
            return 0;
        }
        int count=0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select quentity from usercart where useremail=? and prodid=?");
             ps.setString(1, userId);
             ps.setString(2, itemId);
             rs=ps.executeQuery();
             if(rs.next()){
                 count=rs.getInt("quentity");
             }
        }catch(SQLException ex){
            System.out.println("Error in getCartItemCount"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
        return count;
    }

    @Override
    public String removeProductFromCart(String userId, String prodId) {
         Connection conn=DBUtil.provideConnection();
         PreparedStatement ps1=null;
         PreparedStatement ps2=null;
         ResultSet rs=null;
         String status="Product Removal Failed";
         try{
             ps1=conn.prepareStatement("select * from usercart where useremail=?,prodid=?");
             ps1.setString(1, userId);
             ps1.setString(2, prodId);
             ps1.executeQuery();
             if(rs.next()){
                 int prodQuantity=rs.getInt("quantity");
                 prodQuantity-=1;
                 if(prodQuantity>0){
                     ps2=conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                     ps2.setInt(1, prodQuantity);
                     ps2.setString(2, userId);
                     ps2.setString(3, prodId);
                     int k=ps2.executeUpdate();
                     if(k>0){
                         status="product Successfully removed from the cart";
                     }
                 }else{
                     ps2=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
                     ps2.setString(1, userId);
                     ps2.setString(2, prodId);
                     int k=ps2.executeUpdate();
                     if(k>0){
                         status="product Successfully removed from the cart";
                     }
                 }
             }
         }catch(SQLException ex){
            status="Removal failed due to Exception";
            System.out.println("Error in removeProductFromCart "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps1);
        DBUtil.closeStatemant(ps2);
        return status;
    }

    @Override
    public Boolean removeAProduct(String userId, String ProdID) {
        boolean flag=false;
         Connection conn=DBUtil.provideConnection();
         PreparedStatement ps=null;
         try{
             ps=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
             ps.setString(1, userId);
             ps.setString(2,ProdID);
             int k=ps.executeUpdate();
             if(k>0){
                 flag=true;
             }
             
         }catch(SQLException ex){
            System.out.println("Error in removeAProduct "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatemant(ps);
        return flag;

    }   
}
