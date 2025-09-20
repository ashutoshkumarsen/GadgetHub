/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.UserDao;
import in.gadgethub.dao.impl.UserDaoImpl;
import in.gadgethub.pojo.UserPojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class RegisterServlet extends HttpServlet {

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
               String status="registration failed";
               String password=request.getParameter("password");
               String conPassword=request.getParameter("confirmPassword");
               if(!password.equals(conPassword)){
                status="both password does not match";
                RequestDispatcher rd=request.getRequestDispatcher("register.jsp");
                request.setAttribute("message", status);
                rd.forward(request, response);
               } else{
                    UserPojo user=new UserPojo();
               user.setUsername(request.getParameter("userName"));
               user.setUseremail(request.getParameter("userEmail"));
               user.setAddress(request.getParameter("address"));
               user.setMobile(request.getParameter("mobile"));
               String pinc=request.getParameter("pincode");
               int pinCode=Integer.parseInt(pinc);
               user.setPincode(pinCode);
               user.setPassword(request.getParameter("password"));
               UserDao userDao=new UserDaoImpl();
               status=userDao.registerUser(user);
               RequestDispatcher rd=request.getRequestDispatcher("register.jsp");
               request.setAttribute("message", status);
               rd.forward(request, response);
               }
              
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
