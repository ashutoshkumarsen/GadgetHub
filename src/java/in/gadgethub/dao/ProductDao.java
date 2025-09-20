/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.ProductPojo;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface ProductDao {
    String addproduct(ProductPojo product);
    String updateProduct(ProductPojo prevProduct,ProductPojo updatedProduct);
    String updateProductPrice(String prodId,double updatePrice);
    List<ProductPojo>getAllProduct();
    List<ProductPojo>getAllProductByType(String type);
    List<ProductPojo>searchAllProduct(String search);
    ProductPojo getProductDetails(String prodId);
    int getProductQuantity(String prodId);
    String updateProductWithoutImage(String prevProductId,ProductPojo updatedProduct);
    double getProductPrice(String prodId);
    boolean sellNProduct(String prodId,int n);
    List<String>getAllProductType();
    byte[] getImage(String prodId);
    String removeProduct(String prodId);
    
}
