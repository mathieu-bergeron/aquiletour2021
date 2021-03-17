package ca.aquiletour.server.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// from: https://www.tutorialspoint.com/java/java_sending_email.htm
// from : https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
public class TestEmail {

	   public static void main(String [] args) {    
		   
	        final String username = "aiguilleur.ca@gmail.com";
	        final String password = "Momo!1234";

	        Properties prop = new Properties();
	        prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("aiguilleur.ca@gmail.com"));
	            message.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse("eborgner@gmail.com, mathieu.bergeron@cmontmorency.qc.ca")
	            );
	            message.setSubject("Nouveau code: 143 645");
	            message.setText("Bonjour,"
	                    + "\n\nVoici votre nouveau code de connexion"
	                    + "\n\n143 645"
	                    + "\n\nMerci et bonne journée!\nL'équipe Aiguilleur.ca");

	            Transport.send(message);

	            System.out.println("Done");

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
}
