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

	public static void main(String[] args) {
		sendCode("143 567", "Mathieu", "mathieu.bergeron@cmontmorency.qc.ca");
	}

	public static void sendCode(String loginCode, String userName, String toEmail) {
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
		            InternetAddress.parse(toEmail));
		    message.setSubject("Nouveau code: " + loginCode);
		    message.setText("Bonjour " + userName + ","
		            + "\n\nVoici votre nouveau code de connexion"
		            + "\n\n" + loginCode
		            + "\n\nMerci et bonne journée!\nL'équipe Aiguilleur.ca\n\n");

		    Transport.send(message);

		    System.out.println("Done");

		} catch (MessagingException e) {
		    e.printStackTrace();
		}
	}
}
