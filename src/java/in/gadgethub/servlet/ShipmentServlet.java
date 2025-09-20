/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.OrderDao;
import in.gadgethub.dao.impl.OrderDaoImpl;
import in.gadgethub.dao.impl.TransationDaoImpl;
import in.gadgethub.dao.impl.UserDaoImpl;
import in.gadgethub.pojo.OrderPojo;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class ShipmentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
       String userName=(String)session.getAttribute("userName");
       String password=(String)session.getAttribute("password");
       String userType=(String)session.getAttribute("userType");
         if( userType==null ||password==null|| userName==null ){
            response.sendRedirect("login.jsp?message=Access Denied !please login as admin");
        }else if(!userType.equalsIgnoreCase("admin")){
           response.sendRedirect("login.jsp?message=Session expired !please login again"); 
        }
         
         String orderid=request.getParameter("orderid");
         String amount=request.getParameter("amount");
         String userid=request.getParameter("userid");
         String prodid=request.getParameter("prodid");
         OrderDao orderDao=new OrderDaoImpl();
         orderDao.shipNow(orderid, prodid);
         TransationDaoImpl t= new  TransationDaoImpl();
         UserDaoImpl user=new UserDaoImpl();
         List<OrderPojo>orders=orderDao.getAllOrders();
          Map<String,String> user_Id=new HashMap<>();
          Map<String,String> user_address=new HashMap<>();
          for(OrderPojo o:orders){
              String uid=t.getUserId(o.getOrderId());
              user_Id.put(o.getOrderId(),uid) ;
              user_address.put(uid,user.getUserAddress(uid));
          }
          
          RequestDispatcher rd=request.getRequestDispatcher("shippedItems.jsp");
          request.setAttribute("orders",orders);
          request.setAttribute("userId", user_Id);
          request.setAttribute("userAddress",user_address);
          rd.forward(request,response);

        }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
