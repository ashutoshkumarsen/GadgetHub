/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.utility;

import javax.mail.MessagingException;

/**
 *
 * @author DELL
 */
public class MailMessage {
    public static void registrationSuccess(String recipientMailId, String name) throws MessagingException {
        String subject = "Registration Successfull";
        String htmlTextMessage = "" + "<html>" + "<body>"
                + "<h2 style='color:green;'>Welcome to " + AppInfo.appName + "</h2>" + "" + "Hi " + name + ","
                + "<br><br>Thanks for singing up with " + AppInfo.appName + ".<br>"
                + "We are glad that you choose us. We invite you to check out our latest collection of new electonics appliances."
                + "<br>We are providing upto 60% OFF on most of the electronic gadgets. So please visit our site and explore the collections."
                + "<br><br>Our Online electronics is growing in a larger amount these days and we are in high demand so we thanks all of you for "
                + "making us up to that level. We Deliver Product to your house with no extra delivery charges and we also have collection of most of the"
                + "branded items.<br><br>As a Welcome gift for our New Customers we are providing additional 10% OFF Upto 500 Rs for the first product purchase. "
                + "<br>To avail this offer you only have "
                + "to enter the promo code given below.<br><br><br> PROMO CODE: " + "" + AppInfo.appName.toUpperCase() + "500<br><br><br>"
                + "Have a good day!<br>" + "" + "</body>" + "</html>";
        JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);
    }
     public static void orderPlaced(String username,double paidAmount) throws MessagingException{
         String status="Order placed Successfully";
          String htmlTextMessage = "" + "<html>" + "<body>"
                  +"<h2 style='color:green;'>Your order of Rs"+paidAmount+" has been successfully placed</h2><br>"
                  +"Ensure all details are accurate to avoid delivery delays.<br>"
                  +"Orders placed on weekends or public holidays will be processed the next business day.<br>"
                  +"Review your order summary, including shipping charges and delivery time.<br>"
                  +"You will receive the next update when the item in your order is packed/shipped by the seller"
                  +"<p>Your rewards with this order<br>"
                  +"<b>Coupon Code:JAIN500</b>"+"<br>"
                  +"which will be applied on your next order<br></p>"
                    + "Thank you for shopping with GadgetHub<br>" + "" + "</body>" + "</html>";
          
          JavaMailUtil.sendMail(username, status, htmlTextMessage);
    }
    public static void orderShipped(String username,String prodId)throws MessagingException{
     String status="Order has been shipped successfully";
     String htmlTextMessage = "" + "<html>" + "<body>"
             +"Your product with product Id <b>"+prodId+"</b><br>"
             +"has been shipped succesfully.<br>Stay tuned on Gadegthub for latest and trendy products"
             +"Inspect the package for any visible damage." +
"If damaged, do not accept the delivery and immediately contact our support team."
             +"<br><b>Need Help</b>" +
"<br>For any issues or assistance with your order, contact our support team via live chat, email at support@gadgethub.com, or call us at 1800-567-123<br>" +
"Happy Shopping at GadgetHub!"
             +"<img src='images/phone2'/>"
             + "</body>" + "</html>";
     JavaMailUtil.sendMail(username, status, htmlTextMessage);
    }
    public static void updateProduct(){
        
    }
}
