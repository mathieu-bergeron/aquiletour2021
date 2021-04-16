package ca.aquiletour.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.ConfigService;

public class AquiletourConfig extends ConfigService {

	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	public static AquiletourConfig loadFromJson(Path configFilepath) {
		T.call(AquiletourConfig.class);
		
		FileReader reader = null;

		try {

			reader = new FileReader(configFilepath.toFile());
			return gson.fromJson(reader, AquiletourConfig.class);

		} catch (FileNotFoundException e) {
			
			System.err.println("Cannot find config file: " + configFilepath.toAbsolutePath());
			System.exit(0);
		}
		
		return null;
	}
	
	private List<String> adminIds = new ArrayList<>();
	

	public List<String> getAdminIds() {
		return adminIds;
	}

	public void setAdminIds(List<String> adminIds) {
		this.adminIds = adminIds;
	}
}
