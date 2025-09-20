/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.TransationDao;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class TransationDaoImpl implements TransationDao{

    @Override
    public String getUserId(String TransId) {
         String userId="";
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            ps=conn.prepareStatement("Select useremail from transactions where transid=?");
            ps.setString(1,TransId);
            rs=ps.executeQuery();
            if(rs.next()){
                userId=rs.getString(1);
            }
    }catch(SQLException ex){
            
            System.out.println("Error in getUserId method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatemant(ps);
        return userId;
    
    }
}
