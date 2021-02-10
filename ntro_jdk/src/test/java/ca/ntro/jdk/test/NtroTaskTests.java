package ca.ntro.jdk.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.ntro.core.task2.NtroTask;
import ca.ntro.core.task2.NtroTaskImpl;
import ca.ntro.jdk.NtroJdk;
import ca.ntro.jdk.tasks.GraphWriterJdk;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

public class NtroTaskTests {
	
	private static File taskDir = new File("__tasks__");

	@BeforeClass
	public static void initializeNtro() {
		NtroJdk.defaultInitializationTask().execute();
	}

	@BeforeClass
	public static void initializeTaskDir() {
		if(taskDir.exists()) {
			deleteDir(taskDir);
		}
		
		taskDir.mkdirs();
	}

	private static void deleteDir(File aDir) {
		String[] fileNames = aDir.list();
		
		for(String fileName : fileNames) {

			File file = new File(aDir, fileName);
			
			if(file.isDirectory()) {
				deleteDir(aDir);
			}else {
				file.delete();
			}
		}
		
		aDir.delete();
	}
	
	@Before
	public void setUp() {
	}
	
	private GraphWriterTest createGraphWriter(String testName) {
		return new GraphWriterTest(testName);
	}

	private void toFile(String testName, GraphWriterTest writer) throws IOException {
		File outFile = new File(taskDir, testName + ".png");
		GraphWriterJdk writerJdk = writer.toGraphWriterJdk();
		writerJdk.toFile(outFile);
	}

	@Test
	public void simpleTask() throws IOException {
		String testName = "simpleTask";
		GraphWriterTest testWriter = createGraphWriter(testName);
		
		NtroTask taskA = new NtroTaskImpl("A");
		
		taskA.writeGraph(testWriter);

		assertTrue(testWriter.hasNode("A"));

		toFile(testName, testWriter);
	}

	@Test
	public void subTask() throws IOException {
		String testName = "subTask";
		GraphWriterTest testWriter = createGraphWriter(testName);
		
		NtroTask parentTask = new NtroTaskImpl("parent");
		NtroTask childTask = new NtroTaskImpl("child");
		
		parentTask.addSubTask(childTask);
		
		parentTask.writeGraph(testWriter);
		toFile(testName, testWriter);

		assertTrue(testWriter.hasCluster("parent"));
		assertTrue(testWriter.hasNode("child"));
		assertTrue(testWriter.ifClusterContains("parent", "child"));

	}

	@Test
	public void nextTask() throws IOException {
		String testName = "nextTask";
		GraphWriterTest testWriter = createGraphWriter(testName);
		
		NtroTask taskA = new NtroTaskImpl("A");
		NtroTask taskB = new NtroTaskImpl("B");
		
		taskA.addNextTask(taskB);

		taskA.writeGraph(testWriter);
		toFile(testName, testWriter);
		
		assertTrue(testWriter.hasNode("A"));
		assertTrue(testWriter.hasNode("B"));
		assertTrue(testWriter.hasEdge("A", "B"));
	}

	@Test
	public void previousTask() throws IOException {
		String testName = "previousTask";
		GraphWriterTest testWriter = createGraphWriter(testName);
		
		NtroTask taskA = new NtroTaskImpl("A");
		NtroTask taskB = new NtroTaskImpl("B");
		
		taskB.addPreviousTask(taskA);

		taskB.writeGraph(testWriter);
		toFile(testName, testWriter);
		
		assertTrue(testWriter.hasNode("A"));
		assertTrue(testWriter.hasNode("B"));
		assertTrue(testWriter.hasEdge("A", "B"));
	}

	@Test
	public void levels() throws IOException {
		String testName = "levels";
		GraphWriterTest testWriter = createGraphWriter(testName);
		
		NtroTask taskA = new NtroTaskImpl("A");
		NtroTask taskB = new NtroTaskImpl("B");
		NtroTask taskC = new NtroTaskImpl("C");
		NtroTask taskD = new NtroTaskImpl("D");
		
		taskA.addSubTask(taskB);
		taskB.addSubTask(taskC);
		taskC.addSubTask(taskD);
		
		taskA.writeGraph(testWriter);
		toFile(testName, testWriter);
		
		assertTrue(testWriter.hasCluster("A"));
		assertTrue(testWriter.hasCluster("B"));
		assertTrue(testWriter.hasCluster("C"));
		assertTrue(testWriter.hasNode("D"));
		assertTrue(testWriter.ifClusterContains("A", "B"));
		assertTrue(testWriter.ifClusterContains("B", "C"));
		assertTrue(testWriter.ifClusterContains("C", "D"));
	}

	@Test
	public void mixed() throws IOException {
		String testName = "mixed";
		GraphWriterTest testWriter = createGraphWriter(testName);
		
		NtroTask taskA = new NtroTaskImpl("A");
		NtroTask taskB = new NtroTaskImpl("B");
		NtroTask taskC = new NtroTaskImpl("C");
		
		taskA.addNextTask(taskB);
		taskB.addNextTask(taskC);

		NtroTask taskA1 = new NtroTaskImpl("A1");
		NtroTask taskA2 = new NtroTaskImpl("A2");
		NtroTask taskA3 = new NtroTaskImpl("A3");
		
		taskA.addSubTask(taskA1);
		taskA.addSubTask(taskA2);
		taskA.addSubTask(taskA3);
		
		taskA1.addNextTask(taskA2);
		taskA1.addNextTask(taskA3);

		NtroTask taskB1 = new NtroTaskImpl("B1");
		NtroTask taskB2 = new NtroTaskImpl("B2");
		NtroTask taskB3 = new NtroTaskImpl("B3");
		
		taskB.addSubTask(taskB1);
		taskB.addSubTask(taskB2);
		taskB.addSubTask(taskB3);
		
		taskB1.addNextTask(taskB3);
		taskB2.addNextTask(taskB3);

		NtroTask taskD = new NtroTaskImpl("D");
		
		taskB2.addNextTask(taskD);
		taskC.addNextTask(taskD);

		taskA.writeGraph(testWriter);

		toFile(testName, testWriter);
	}

	@After
	public void tearDown() {
	}

}
