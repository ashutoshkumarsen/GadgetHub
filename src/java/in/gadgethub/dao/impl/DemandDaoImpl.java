/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.DemandDao;
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
public class DemandDaoImpl implements DemandDao{

    @Override
    public boolean addProduct(DemandPojo demandPojo) {
        String updateSQL="Update userdemand set quantity=quantity+? where useremail=? and prodid=?";
        String insertSQL="Insert into userdemand values(?,?,?)";
        Connection conn=DBUtil.provideConnection();
        boolean status=false;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        try{
            ps1=conn.prepareStatement(updateSQL);
            ps1.setInt(1,demandPojo.getDemandQuantity());
            ps1.setString(2,demandPojo.getProdId());
            ps1.setString(3, demandPojo.getUseremail());
            int k=ps1.executeUpdate();
            if(k==0){
            ps2=conn.prepareStatement(insertSQL);
            ps2.setInt(1,demandPojo.getDemandQuantity());
            ps2.setString(2,demandPojo.getProdId());
            
            ps2.executeUpdate();
            }
            status=true;
        }catch(SQLException ex){
            
        }
        return status;
    }

    @Override
    public boolean removeProduct(String userId, String ProdId) {
        boolean result=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("delete from userdemand where useremail=? and prodid=?");
            ps.setString(1,userId);
            ps.setString(2, ProdId);
            result = ps.executeUpdate()>0;
        }catch(SQLException ex){
            System.out.println("Exception Occured in DemandDAO "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeConnection();
        DBUtil.closeStatemant(ps);
        return result;
    }

    @Override
    public List<DemandPojo> haveDemanded(String prodId) {
        List <DemandPojo>demandList=new ArrayList();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
             ps=conn.prepareStatement("select * from userdemand where prodid=?");
            ps.setString(1, prodId);
            rs=ps.executeQuery();
            while(rs.next()){
                DemandPojo demandPojo=new DemandPojo();
                demandPojo.setUseremail(rs.getString("useremail"));
                demandPojo.setProdId(rs.getString("prodid"));
                demandPojo.setDemandQuantity(rs.getInt("quantity"));
                demandList.add(demandPojo);
            }
        }catch(SQLException ex){
            System.out.println("Exception Occured in DemandDAO "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeConnection();
        DBUtil.closeStatemant(ps);
        return demandList;
    }
    
}
