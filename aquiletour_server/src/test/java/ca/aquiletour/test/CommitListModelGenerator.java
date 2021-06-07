package ca.aquiletour.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ca.aquiletour.core.pages.git.commit_list.models.CommitListModel;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.server.MessageServiceWebserver;
import ca.aquiletour.server.backend.AquiletourBackendService;
import ca.ntro.jdk.services.LocalStoreFiles;
import ca.ntro.jdk.web.NtroWebServer;
import ca.ntro.services.Ntro;

@RunWith(JUnit4.class)
public class CommitListModelGenerator {
	
	@BeforeClass
	public static void initializeNtro(){
		NtroWebServer.defaultInitializationTask(AquiletourBackendService.class, LocalStoreFiles.class, MessageServiceWebserver.class, null, null)
		             .execute();
	}

	@Test
	public void test01() {
		
		CommitListModel test01 = new CommitListModel();
		
		test01.setCourseId("StruDon");
		test01.setSemesterId("H2021");
		test01.setExercisePath("/ateliers/atelier1");
		test01.setFromDate("-1");
		test01.setToDate("-1");
		test01.setStudentId("1234500");
		
		for(int i = 0; i < 10; i++) {
			Commit commit = new Commit();
			
			commit.setCommitId("asfd");
			commit.setCommitMessage("Atelier 1");
			commit.setCommitMessageFirstLine("Atelier 1");
			commit.setEstimatedEffort(10);
			commit.setModifiedFiles(new ArrayList<>());
			commit.setTimeStamp("00002330");
			
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
