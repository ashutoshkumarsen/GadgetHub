/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.DemandPojo;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface DemandDao {
    public boolean addProduct(DemandPojo demandPojo);
    public boolean removeProduct(String userId,String ProdId);
    public List<DemandPojo>haveDemanded(String prodId);
    
}
