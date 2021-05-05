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
		if(args.length > 0) {
			sendCode("143 567", "Mathieu", args[0]);
		}else {
			System.out.println("Usage: ./gradlew testEmail adresseDestinataire");
		}
	}

	public static void sendCode(String loginCode, String userName, String toEmail) {
		final String fromEmail = "aiguilleur.ca@gmail.com";
		//final String fromEmail = "mailagent@aiguilleur.ca";

		final String username = "aiguilleur.ca@gmail.com";
		//final String username = "mailagent@aiguilleur.ca";

		final String password = "Momo!1234";
		//final String password = "qwe123";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); //TLS
		
		/*
		prop.put("mail.smtp.host", "mail.aiguilleur.ca");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.auth", "true");
		*/
		
		Session session = Session.getInstance(prop,
		        new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(username, password);
		            }
		        });

		try {

		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(fromEmail));
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
