

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.CartDaoImpl;
import in.gadgethub.dao.impl.ProductDaoImpl;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LandingServlet extends HttpServlet {

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
           HttpSession session = request.getSession();
           String username=(String)session.getAttribute("username");
           String usertype=(String)session.getAttribute("usertype");
           String search=request.getParameter("search");
           String type=request.getParameter("type");
           ProductDaoImpl productDao=new ProductDaoImpl();
           CartDaoImpl cartDao=new CartDaoImpl();
           String message="All Products";
           List<ProductPojo> products=new ArrayList<>();
           if(search!=null){
               products=productDao.searchAllProduct(search);
               message="showing Results for '"+search+"'";
        }else if(type!=null){
            products=productDao.getAllProductByType(type);
            message="showing result for '"+type+"'";
        }else{
            products=productDao.getAllProduct();
        }
        if(products.isEmpty()){
             products=productDao.getAllProduct();
             message="No item found for '"+(search!=null?search:type)+"'"; 
        }
        Map<String,Integer>map=new HashMap<>();
        for(ProductPojo product:products){
            int qty=cartDao.getCartItemCount(username, product.getProdId());
            map.put(product.getProdId(), qty);
        }
        RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
        request.setAttribute("userName", username);
        request.setAttribute("message",message);
        request.setAttribute("products", products);
        request.setAttribute("userType", usertype);
        request.setAttribute("map", map);
        rd.forward(request, response);
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
