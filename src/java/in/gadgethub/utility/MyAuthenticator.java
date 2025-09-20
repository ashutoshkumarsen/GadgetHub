/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.utility;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author DELL
 */
public class MyAuthenticator extends Authenticator{
    private String username, password;

    public MyAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        PasswordAuthentication pwdAuth = new PasswordAuthentication(this.username, this.password);
        return pwdAuth;
    }
}
