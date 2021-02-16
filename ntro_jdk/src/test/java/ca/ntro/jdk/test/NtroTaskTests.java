package ca.ntro.jdk.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.ntro.core.task2.NtroTask;
import ca.ntro.jdk.NtroJdk;
import ca.ntro.jdk.tasks.GraphWriterJdk;

import static org.junit.Assert.assertFalse;
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

	@Test
	public void simpleTask() throws IOException {
		String testName = "simpleTask";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		
		taskA.asGraph().writeGraph(writer);

		assertTrue(taskA.asNode().isRoot());
		assertTrue(taskA.asNode().isNode());
		assertTrue(taskA.asNode().isRootNode());

		assertFalse(taskA.asNode().isCluster());
		assertFalse(taskA.asNode().isRootCluster());
		
		taskA.asGraph().forEachEdge((from, to) -> assertTrue(false)); // should not have any edge

		writer.toFile(new File(taskDir, testName + ".png"));
	}

	@Test
	public void subTask() throws IOException {
		String testName = "subTask";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask parentTask = new NtroTaskAsyncTest("parent");
		NtroTask childTask = new NtroTaskAsyncTest("child");
		
		parentTask.addSubTask(childTask);
		
		childTask.asGraph().writeGraph(writer);

		assertTrue(parentTask.asNode().isRoot());
		assertTrue(parentTask.asNode().isCluster());

		assertFalse(childTask.asNode().isRoot());
		assertFalse(childTask.asNode().isCluster());

		parentTask.asGraph().forEachEdge((from, to) -> assertTrue(false)); // should not have edges
		childTask.asGraph().forEachEdge((from, to) -> assertTrue(false)); // should not have edges

		writer.toFile(new File(taskDir, testName + ".png"));
	}

	@Test
	public void nextTask() throws IOException {
		String testName = "nextTask";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		NtroTask taskB = new NtroTaskAsyncTest("B");
		
		taskA.addNextTask(taskB);

		assertTrue(taskA.asNode().isRootNode());
		assertTrue(taskB.asNode().isRootNode());

		taskA.asGraph().forEachEdge((from, to) -> {
			assertTrue(from == taskA && to == taskB);
		});

		taskB.asGraph().forEachEdge((from, to) -> {
			assertTrue(from == taskA && to == taskB);
		});

		taskB.asGraph().writeGraph(writer);
	}

	@Test
	public void previousTask() throws IOException {
		String testName = "previousTask";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		NtroTask taskB = new NtroTaskAsyncTest("B");
		
		taskB.addPreviousTask(taskA);

		assertTrue(taskA.asNode().isRootNode());
		assertTrue(taskB.asNode().isRootNode());

		taskA.asGraph().forEachEdge((from, to) -> {
			assertTrue(from == taskA && to == taskB);
		});

		taskB.asGraph().forEachEdge((from, to) -> {
			assertTrue(from == taskA && to == taskB);
		});

		taskB.asGraph().writeGraph(writer);
	}

	@Test
	public void levels() throws IOException {
		String testName = "levels";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		NtroTask taskB = new NtroTaskAsyncTest("B");
		NtroTask taskC = new NtroTaskAsyncTest("C");
		NtroTask taskD = new NtroTaskAsyncTest("D");
		
		taskA.addSubTask(taskB);
		taskB.addSubTask(taskC);
		taskC.addSubTask(taskD);

		assertTrue(taskA.asNode().isRootCluster());
		assertTrue(taskB.asNode().isCluster());
		assertTrue(taskC.asNode().isCluster());
		assertTrue(taskD.asNode().isNode());

		assertFalse(taskB.asNode().isRoot());
		assertFalse(taskC.asNode().isRoot());
		assertFalse(taskD.asNode().isRoot());

		taskA.asGraph().forEachEdge((from, to) -> assertTrue(false)); // should not have edges
		taskB.asGraph().forEachEdge((from, to) -> assertTrue(false)); 
		taskC.asGraph().forEachEdge((from, to) -> assertTrue(false)); 
		taskD.asGraph().forEachEdge((from, to) -> assertTrue(false)); 
		
		taskD.asGraph().writeGraph(writer);
	}

	@Test
	public void mixed() throws IOException {
		String testName = "mixed";
		GraphWriterTest testWriter = createGraphWriter(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		NtroTask taskB = new NtroTaskAsyncTest("B");
		NtroTask taskC = new NtroTaskAsyncTest("C");
		
		taskA.addNextTask(taskB);
		taskB.addNextTask(taskC);

		NtroTask taskA1 = new NtroTaskAsyncTest("A1");
		NtroTask taskA2 = new NtroTaskAsyncTest("A2");
		NtroTask taskA3 = new NtroTaskAsyncTest("A3");
		
		taskA.addSubTask(taskA1);
		taskA.addSubTask(taskA2);
		taskA.addSubTask(taskA3);
		
		taskA1.addNextTask(taskA2);
		taskA1.addNextTask(taskA3);

		NtroTask taskB1 = new NtroTaskAsyncTest("B1");
		NtroTask taskB2 = new NtroTaskAsyncTest("B2");
		NtroTask taskB3 = new NtroTaskAsyncTest("B3");
		
		taskB.addSubTask(taskB1);
		taskB.addSubTask(taskB2);
		taskB.addSubTask(taskB3);
		
		taskB1.addNextTask(taskB3);
		taskB2.addNextTask(taskB3);

		NtroTask taskD = new NtroTaskAsyncTest("D");
		
		taskB2.addNextTask(taskD);
		taskC.addNextTask(taskD);

		taskC.asGraph().writeGraph(testWriter);
	}

	@After
	public void tearDown() {
	}

}
