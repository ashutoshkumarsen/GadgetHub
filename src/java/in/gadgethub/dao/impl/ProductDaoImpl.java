/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ProductDaoImpl implements ProductDao{

    @Override
    public String addproduct(ProductPojo product) {
        String status="Product Regestration Failed";
        if(product.getProdId()==null){
            product.setProdId(IDUtil.generateProdId());
    } 
        Connection conn=DBUtil.provideConnection();
               PreparedStatement ps=null;
               try{
                   ps=conn.prepareStatement("insert into products values(?,?,?,?,?,?,?,?)");
                   ps.setString(1, product.getProdId());
                   ps.setString(2, product.getProdName());
                   ps.setString(3, product.getProdType());
                   ps.setString(4, product.getProdInfo());
                   ps.setDouble(5, product.getProdPrice());
                   ps.setInt(6, product.getProdQuantity());
                   ps.setBlob(7, product.getProdImage());
                   ps.setString(8, "Y");
                   int count=ps.executeUpdate();
                   if(count==1){
                       status="product added successfullu with Id= "+product.getProdId();
                       
                   }
               }catch(SQLException ex){
            System.out.println("Error in AddProduct "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
//             DBUtil.closeConnection();
             return status;
}
    
    @Override
    public String updateProduct(ProductPojo prevProduct, ProductPojo updatedProduct) {
       String status="updation Failed";
       if(!prevProduct.getProdId().equals(updatedProduct.getProdId())){
           status="Product ID's Do not Matchh. Updation Failed";
           return status;
       }Connection conn=DBUtil.provideConnection();
               PreparedStatement ps=null;
               try{
                   ps=conn.prepareStatement("update ptoducts set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,image=? where pid=?");
                   ps.setString(1, updatedProduct.getProdName());
                   ps.setString(2, updatedProduct.getProdType());
                   ps.setString(3, updatedProduct.getProdInfo());
                   ps.setDouble(4, updatedProduct.getProdPrice());
                   ps.setInt(5, updatedProduct.getProdQuantity());
                   ps.setBlob(6, updatedProduct.getProdImage());
                   ps.setString(7, updatedProduct.getProdId());
                   int count=ps.executeUpdate();
                   if(count==1){
                       status="product updated Successfully";
                       
                   }
               }catch(SQLException ex){
            System.out.println("Error updating product "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
//             DBUtil.closeConnection();
             return status;
    
    }

    @Override
    public String updateProductPrice(String prodId, double updatePrice) {
        String status=" price updation Failed";
        Connection conn=DBUtil.provideConnection();
               PreparedStatement ps=null;
               try{
                   ps=conn.prepareStatement("update ptoducts set pprice=? where pid=?");
                   ps.setDouble(1, updatePrice);
                   ps.setString(2, prodId);
                   int count=ps.executeUpdate();
                   if(count==1){
                       status="product price updated Successfully";
                       
                   }
               }catch(SQLException ex){
                   status= "error:"+ex.getMessage();
            System.out.println("Error updating Product product "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
//             DBUtil.closeConnection();
             return status;
    
    }

    @Override
    public List<ProductPojo> getAllProduct() {
        List<ProductPojo> productList=new ArrayList<>();
        Statement st=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            st=conn.createStatement();
            rs=st.executeQuery("Select * from products where AVAILABLE  ='Y'");
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
    }catch(SQLException ex){
            
            System.out.println("Error in fatching product"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(st);
//        DBUtil.closeConnection();
        return productList;
        
    }

    @Override
    public List<ProductPojo> getAllProductByType(String type) {
        List<ProductPojo> productList=new ArrayList<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            ps=conn.prepareStatement("select * from products where ptype=?");
            ps.setString(1,type);
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
    }catch(SQLException ex){
            
            System.out.println("Error in getAllProduct by type "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
//        DBUtil.closeConnection();
        return productList;
    }

    @Override
    public List<ProductPojo> searchAllProduct(String search) {
        List<ProductPojo> productList=new ArrayList<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            ps=conn.prepareStatement("select * from products where lower(ptype) like ? or lower(pname) like ? or lower(pinfo) like ?");
            ps.setString(1,"%"+search+"%");
            ps.setString(2,"%"+search+"%");
            ps.setString(3,"%"+search+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
    }catch(SQLException ex){
            
            System.out.println("Error in getAllProduct by type"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
//        DBUtil.closeConnection();
        return productList;
    }

    @Override
    public ProductPojo getProductDetails(String prodId) {
        ProductPojo product=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
        ps=conn.prepareStatement("select * from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                
            }
    }catch(SQLException ex){
            
            System.out.println("Error in get user details"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
//        DBUtil.closeConnection();
        return product;

    }

    @Override
    public int getProductQuantity(String prodId) {
        int quantity=0;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
        ps=conn.prepareStatement("select pquantity from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
             quantity=rs.getInt(1);
                
            }
    }catch(SQLException ex){
            
            System.out.println("Error in getting quantity"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
//        DBUtil.closeConnection();
        return quantity;
    }

    @Override
    public String updateProductWithoutImage(String prevProductId, ProductPojo updatedProduct) {
        String status="updation Failed";
        int prevQuantity=0;
       if(!prevProductId.equals(updatedProduct.getProdId())){
           status="Product ID's Do not Matchh. Updation Failed";
           return status;
       }Connection conn=DBUtil.provideConnection();
               PreparedStatement ps=null;
               ResultSet rs=null;
               try{
                   prevQuantity=getProductQuantity(prevProductId);
                   ps=conn.prepareStatement("update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=? where pid=?");
                   ps.setString(1, updatedProduct.getProdName());
                   ps.setString(2, updatedProduct.getProdType());
                   ps.setString(3, updatedProduct.getProdInfo());
                   ps.setDouble(4, updatedProduct.getProdPrice());
                   ps.setInt(5, updatedProduct.getProdQuantity());
                   ps.setString(6, updatedProduct.getProdId());
                   int count=ps.executeUpdate();
                   if(count==1 && prevQuantity<updatedProduct.getProdQuantity()){
                       status="product updated Successfully and Mail send";
                       //code to send mail
                   }else if(count==1){
                       status="product updated Successfully";
                   }
               }catch(SQLException ex){
            System.out.println("Error updating product "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
             DBUtil.closeResultSet(rs);
//             DBUtil.closeConnection();
             return status;
    }

    @Override
    public double getProductPrice(String prodId) {
        double price=0.0;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
        ps=conn.prepareStatement("select pprice from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
             price=rs.getDouble(1);
                
            }
    }catch(SQLException ex){
            
            System.out.println("Error in getting product price"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
//        DBUtil.closeConnection();
        return price;
    }

    @Override
    public boolean sellNProduct(String prodId, int n) {
        boolean result=false;
        Connection conn=DBUtil.provideConnection();
               PreparedStatement ps=null;
               try{
                   ps=conn.prepareStatement("update products set pquantity=(pquantity-?) where pid=? and available='Y' ");
                   ps.setInt(1, n);
                   ps.setString(2, prodId);
                   int count=ps.executeUpdate();
                   if(count==1){
                       result=true;
                   }
               }catch(SQLException ex){
            System.out.println("Error in sellNProducrs "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
//             DBUtil.closeConnection();
             return result;
    
    }

    @Override
    public List<String> getAllProductType() {   
        List<String> productTypeList=new ArrayList<>();
        Statement st=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            st=conn.createStatement();
            rs=st.executeQuery("select DISTINCT ptype from products");
            while(rs.next()){
                productTypeList.add(rs.getString(1));
            }
    }catch(SQLException ex){
            
            System.out.println("Error in getAllProduct by type"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(st);
//        DBUtil.closeConnection();
        return productTypeList;
    }

    @Override
    public byte[] getImage(String prodId) {
           byte[]arr=null;
       Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       ResultSet rs=null;
        try{
            ps=conn.prepareStatement("Select image from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
               arr=rs.getBytes(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in getImage:"+ex);
            ex.printStackTrace();
        } 
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
        return arr;
    }

    @Override
    public String removeProduct(String prodId) {
        String status="Product not found";
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        Connection conn=DBUtil.provideConnection();
        try{
        ps1=conn.prepareStatement("update products set available='N' where pid=?and available='Y'");
            ps1.setString(1,prodId);
            int k=ps1.executeUpdate();
            if(k>0){
               ps2=conn.prepareStatement("delete from usercart where prodid=?");
               ps2.setString(1,prodId);
               ps2.executeUpdate();
               status="Product Removed Success";
            }
    }catch(SQLException ex){
            status="Error: "+ex.getMessage();
            System.out.println("Error in deleating the product"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatemant(ps1);
        DBUtil.closeStatemant(ps2);
//        DBUtil.closeConnection();
        return status;
    }
    
}
