/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.OrderDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDUtil;
import in.gadgethub.utility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

/**
 *
 * @author DELL
 */
public class OrderDaoImpl implements OrderDao{

    @Override
    public boolean addOrder(OrderPojo order) {
        PreparedStatement ps=null;
       
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        try{
                   ps=conn.prepareStatement("insert into orders values(?,?,?,?,?)");
                   ps.setString(1,order.getOrderId());
                   ps.setString(2, order.getProdId());
                   ps.setInt(3, order.getQuantity());
                   ps.setDouble(4,order.getAmount());
                   ps.setInt(5, 0);
                   int count=ps.executeUpdate();
                   status=count>0;
               }catch(SQLException ex){
            System.out.println("Error in addOrder "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
             return status;
    }

    @Override
    public boolean addTransaction(TransactionPojo transaction) {
        PreparedStatement ps=null;
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        try{
                   ps=conn.prepareStatement("insert into transactions values(?,?,?,?)");
                   ps.setString(1,transaction.getTransactionId());
                   ps.setString(2, transaction.getUseremail());
                   java.util.Date d1=transaction.getTransTime();
                   java.sql.Date d2=new java.sql.Date(d1.getTime());
                   ps.setDate(3, d2);
                   ps.setDouble(4,transaction.getAmount());
                   int count=ps.executeUpdate();
                   status=count>0;
               }catch(SQLException ex){
            System.out.println("Error in addTransaction "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
             return status;
    }

    @Override
    public List<OrderPojo> getAllOrders() {
        Statement st=null;
        ResultSet rs=null;
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        List<OrderPojo> myList=new ArrayList<>();
        try{
            st=conn.createStatement();
            rs=st.executeQuery("select * from orders");
            while(rs.next()){
                OrderPojo orders=new OrderPojo();
                orders.setOrderId(rs.getString("orderid"));
                orders.setProdId(rs.getString("prodid"));
                orders.setQuantity(rs.getInt("quantity"));
                orders.setAmount(rs.getDouble("amount"));
                orders.setShipped(rs.getInt("shipped"));
                myList.add(orders);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in getAllOrders "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(st);
             return myList;
    }

    @Override
    public List<OrderDetailsPojo> getAllOrderDetails(String userEmailId) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        List<OrderDetailsPojo> orderList=new ArrayList<>();
        try{
            ps=conn.prepareStatement("Select p.pid as prodid,o.orderid as orderid,o.shipped as shipped,p.image as image,p.pname as pname,o.quantity as qty,o.amount as amount,t.transtime as time FROM orders o,products p,transactions t where o.orderid=t.transid and o.prodid=p.pid and t.useremail=?");
            ps.setString(1, userEmailId);
            rs=ps.executeQuery();
            while(rs.next()){
                OrderDetailsPojo orderDetails=new OrderDetailsPojo();
                orderDetails.setOrderId(rs.getString("orderid"));
                orderDetails.setProdId(rs.getString("prodid"));
                orderDetails.setProdName(rs.getString("pname"));
                orderDetails.setQty(rs.getInt("qty"));
                orderDetails.setAmount(rs.getDouble("amount"));
                orderDetails.setShipped(rs.getInt("shipped"));
                orderDetails.setTime(rs.getTimestamp("time"));
                orderDetails.setProdImage(rs.getAsciiStream("image"));
                orderList.add(orderDetails);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in getAllOrderDetails "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
             DBUtil.closeResultSet(rs);
             return orderList;
    }

    @Override
    public String shipNow(String orderId, String prodId) {
        PreparedStatement ps=null;
        
        String status="Failure!";
        Connection conn=DBUtil.provideConnection();
         try{
            ps=conn.prepareStatement("update orders set shipped = 1 where orderid=? and prodid=?");
            ps.setString(1, orderId);
            ps.setString(2, prodId);
            int count=ps.executeUpdate();
            if(count>0){
                status="Odeder has been shippped successfully";
            TransationDaoImpl t=new TransationDaoImpl();
            String name=t.getUserId(orderId);
       
              MailMessage.orderShipped(name, prodId); 
            }
         }catch(SQLException ex){
            System.out.println("Error in shipNow "+ex);
            ex.printStackTrace();
           }catch(MessagingException e) {
                System.out.println("error in sending orderplaced Email");
            }
             DBUtil.closeStatemant(ps);
             return status;
    }

    @Override
    public String PaymentSuccess(String username, double paidAmount) {  
        String status="Order Placement Failed!";

        ProductDaoImpl productDao=new ProductDaoImpl();
        CartDaoImpl cartDao= new CartDaoImpl();
        List <CartPojo> cartList=cartDao.getAllCartItems(username);
        if(cartList.isEmpty()){
            return status;
        }
        String transactionId=IDUtil.generateTransId();
        TransactionPojo trPojo=new TransactionPojo();
        trPojo.setTransactionId(transactionId);
        trPojo.setUseremail(username);
        trPojo.setAmount(paidAmount);
        trPojo.setTransTime(new java.util.Date());
        boolean result=addTransaction(trPojo);
        if(result==false){
            return status;
        }
        boolean orderd=true;
        for(int i=0;i<cartList.size();i++){
            CartPojo cartPojo= cartList.get(i);
            int qty=cartPojo.getQuantity();
            String prodId=cartPojo.getProdId();
//            ProductDaoImpl productDao=new ProductDaoImpl(); 
            double prodPrice=productDao.getProductPrice(prodId);
            double finalPrice=prodPrice*qty;
            OrderPojo orderPojo=new OrderPojo();
            orderPojo.setOrderId(transactionId);
            orderPojo.setProdId(prodId);
            orderPojo.setAmount(finalPrice);
            orderPojo.setQuantity(qty);
            orderPojo.setShipped(0);
//            OrderDaoImpl orderDao=new OrderDaoImpl();
            orderd=addOrder(orderPojo);
            if(orderd==false){
                break;
            }
            cartDao.removeAProduct(username, prodId);
            orderd= productDao.sellNProduct(prodId, qty);
            if(orderd==false){
                break;
            }    
        }
        
        if(orderd==true){
            status="Order Placed Successfully! ";
            try{
              MailMessage.orderPlaced(username, paidAmount); 
           }
            catch(MessagingException e) {
                System.out.println("error in sending orderplaced Email");
            }
            System.out.println("Order Placed Successfully with transactionId="+transactionId);
        }else{
            System.out.println("order Placement or Transaction Failture"+transactionId);
        }
        return status;
    }

    @Override
    public int getSoldQuantity(String prodId) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        int quantity=0;
        Connection conn=DBUtil.provideConnection();
        try{
                   ps=conn.prepareStatement("select sum(quantity) as quant from orders where prodid=?");
                   ps.setString(1,prodId);
                   rs=ps.executeQuery();
                   if(rs.next()){
                       quantity=rs.getInt(1);
                   }
               }catch(SQLException ex){
            System.out.println("Error in getSoldQuantity "+ex);
            ex.printStackTrace();
           }
             DBUtil.closeStatemant(ps);
             DBUtil.closeResultSet(rs);
             return quantity;
    }
    
}
