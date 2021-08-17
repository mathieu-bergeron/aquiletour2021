package ca.aquiletour.server.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ca.aquiletour.core.Constants;
import ca.aquiletour.server.AquiletourConfig;
import ca.ntro.core.system.log.Log;
import ca.ntro.services.Ntro;

// from: https://www.tutorialspoint.com/java/java_sending_email.htm
// from : https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
public class SendEmail {

	public static void main(String[] args) {
		if(args.length > 0) {
			sendCode("143 567", "Mathieu", args[0]);
		}else {
			System.out.println("Usage: ./gradlew testEmail adresseDestinataire");
		}
	}
	

	public static void sendCode(String loginCode, String userName, String toEmail) {
		if(!Ntro.config().isProd()) {
			Log.warning("[WARNING] emails are disabled");
			return;
		}

		AquiletourConfig config = (AquiletourConfig) Ntro.config();
		if(config.getSmtpHost() == null || config.getSmtpHost().isEmpty()) {
			Log.warning("[WARNING] emails not configured in config.json");
		}
		
		final String fromEmail = config.getSmtpFrom();
		final String username = config.getSmtpUser();
		final String password = config.getSmtpPassword();

		Properties prop = new Properties();
		prop.put("mail.smtp.host", config.getSmtpHost());
		prop.put("mail.smtp.port", config.getSmtpPort());
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", String.valueOf(config.getSmtpTls())); 
		
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

		    Log.info("Email sent to " + toEmail);

		} catch (MessagingException e) {
		    e.printStackTrace();
		}
	}
}
