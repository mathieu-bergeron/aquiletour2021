package ca.aquiletour.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.ntro.core.Constants;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.services.ConfigService;

public class AquiletourConfig extends ConfigService {

	private static final Gson gson = new GsonBuilder().serializeNulls().create();
	
	public static AquiletourConfig loadFromJson(Path configFilepath) {
		T.call(AquiletourConfig.class);
		
		AquiletourConfig config = null;
		FileReader reader = null;

		try {

			reader = new FileReader(configFilepath.toFile());
			config = gson.fromJson(reader, AquiletourConfig.class);

		} catch (FileNotFoundException e) {
			
			System.err.println("\n\n[ERROR] Cannot find config file: " + configFilepath.toAbsolutePath() + "\n\n");
			config = new AquiletourConfig();
		}
		
		return config;
	}
	
	private List<String> adminRegistrationIds = new ArrayList<>();

	private String passwordSalt = SecureRandomString.generate(Constants.RANDOM_STRING_DEFAULT_LENGTH);

	private String smtpHost = "";
	private String smtpPort = "";
	private boolean smtpTls = false;
	private String smtpUser = "";
	private String smtpFrom = "";
	private String smtpPassword = "";

	

	public List<String> getAdminRegistrationIds() {
		return adminRegistrationIds;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public boolean getSmtpTls() {
		return smtpTls;
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public String getSmtpFrom() {
		return smtpFrom;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}
}
