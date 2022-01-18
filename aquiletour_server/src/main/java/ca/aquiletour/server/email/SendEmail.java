package ca.aquiletour.server.email;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ca.aquiletour.server.AquiletourConfig;
import ca.ntro.core.system.log.Log;
import ca.ntro.services.ConfigService;

// from: https://www.tutorialspoint.com/java/java_sending_email.htm
// from : https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
public class SendEmail {

	public static void main(String[] args) {
		if(args.length > 0) {
			System.out.println("Sending email to " + args[0]);

			String userHome = System.getProperty("user.home");
			Path configFilepath = Paths.get(userHome, ".aiguilleur", "config.json");
			AquiletourConfig config = AquiletourConfig.loadFromJson(configFilepath);

			sendCode(config, "143 567", "Mathieu", args[0]);
		}else {
			System.out.println("Usage: ./gradlew testEmail -Precipient=adresseDestinataire");
		}
	}
	

	public static void sendCode(ConfigService configService, String loginCode, String userName, String toEmail) {
		if(!configService.isProd()) {
			System.err.println("[WARNING] emails are disabled");
			return;
		}

		AquiletourConfig config = (AquiletourConfig) configService;
		if(config.getSmtpHost() == null || config.getSmtpHost().isEmpty()) {
			System.err.println("[WARNING] emails not configured in config.json");
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

		    System.out.println("[INFO] Email sent to " + toEmail);

		} catch (MessagingException e) {
		    e.printStackTrace();
		}
	}
}
