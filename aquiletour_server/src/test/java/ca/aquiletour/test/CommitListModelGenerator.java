package ca.aquiletour.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ca.aquiletour.core.pages.git.commit_list.models.CommitListModel;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.server.MessageServiceWebserver;
import ca.aquiletour.server.backend.AquiletourBackendService;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.jdk.services.LocalStoreFiles;
import ca.ntro.jdk.web.NtroWebServer;
import ca.ntro.services.Ntro;

@RunWith(JUnit4.class)
public class CommitListModelGenerator {
	
	private static Random random;
	
	@BeforeClass
	public static void initializeNtro(){
		NtroWebServer.defaultInitializationTask(null, AquiletourBackendService.class, LocalStoreFiles.class, MessageServiceWebserver.class, null)
		             .execute();
		
		random = new Random(Ntro.calendar().now().getEpochSeconds());
	}

	@Test
	public void test01() {
		
		String dateFormat = "dd/MM/yyyy HH:mm";
		
		CommitListModel test01 = new CommitListModel();
		
		test01.setCourseId("StruDon");
		test01.setSemesterId("H2021");
		test01.setExercisePath("/ateliers/atelier1");
		test01.setFromDate(String.valueOf(Ntro.calendar().fromString("01/01/2021 12:12",dateFormat).getEpochMiliseconds()));
		test01.setToDate(String.valueOf(Ntro.calendar().fromString("29/06/2021 23:59",dateFormat).getEpochMiliseconds()));
		test01.setStudentId("1234500");
		
		String[] gitMessages = new String[] {
				"Début",
				"En cours",
				"Ça avance",
				"Presque fini",
				"Oups un bogue",
				"Bon, presque fini pour vrai",
				"Prêt à remettre",
				"Autre bogue... grrr",
				"Bon là fini",
				"Atelier 1",
		};
		
		for(int i = 0; i < 10; i++) {
			Commit commit = new Commit();
			
			commit.setCommitId(SecureRandomString.generate(10));
			commit.setCommitMessage(gitMessages[i]);
			commit.setCommitMessageFirstLine(gitMessages[i]);
			commit.setEstimatedEffort(random.nextInt(100));
			commit.setModifiedFiles(new ArrayList<>());
			
			int day = 1 + random.nextInt(29);
			int month = 1 + random.nextInt(5);
			int hour = 8 + random.nextInt(12);
			int minutes = 1 + random.nextInt(60);
			
			String timestamp = String.format("%02d/%02d/2021 %02d:%02d", day, month, hour, minutes);
			
			commit.setTimeStamp(String.valueOf(Ntro.calendar().fromString(timestamp, dateFormat).getEpochMiliseconds()));

			test01.addCommit(commit);
		}
		
		try {
			
			Ntro.jsonService().setPrettyPrinting(true);

			FileWriter writer = new FileWriter(new File("TMP.json"));
			writer.write(Ntro.jsonService().toString(test01));
			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}
}
