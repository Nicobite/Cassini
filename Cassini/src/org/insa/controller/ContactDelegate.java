/*
* Copyright 2014 Abel Juste Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.insa.controller;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.insa.controller.thread.ContactThread;

/**
 *
 * @author Thomas Thiebaud
 */
public class ContactDelegate {
    
    private MainController mainController = null;
    
    /**
     * Constructor
     * @param mainController Reference to main controller
     */
    public ContactDelegate(MainController mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Send a message (email)
     * @param recipient Remote user
     * @param subject Subject of the mail
     * @param message Content of the mail
     * @throws MessagingException Raised if a problem occured during mail transfer
     */
    public void sendEmail(String recipient, String subject, String message) throws MessagingException{
        final String username = "cassini.insa@gmail.com";
        final String password = "okijuhygtfrd";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        
        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress("cassini.insa@gmail.com"));
        mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipient));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);
        
        ContactThread contactThread = new ContactThread(mimeMessage);
        contactThread.start();
    }
}
