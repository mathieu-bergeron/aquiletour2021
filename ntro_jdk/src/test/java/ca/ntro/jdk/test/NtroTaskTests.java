package ca.ntro.jdk.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.jdk.NtroJdk;
import ca.ntro.jdk.tasks.GraphTraceWriterJdk;
import ca.ntro.jdk.tasks.GraphWriterJdk;
import ca.ntro.jdk.test.tasks.NtroTaskAsyncTest;
import ca.ntro.jdk.test.tasks.TraceTester;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

@Ignore
public class NtroTaskTests {
	
	private static File graphDir = new File("__task_graphs__");

	@BeforeClass
	public static void initializeNtro() {
		NtroJdk.defaultInitializationTask().execute();
	}

	@BeforeClass
	public static void initializeTaskDir() {
		if(graphDir.exists()) {
			Utils.deleteDir(graphDir);
		}
		
		graphDir.mkdirs();
	}

	
	@Before
	public void setUp() {
	}

	private void writeFiles(GraphWriterJdk writer, String testName) throws IOException {
		File svgFile = new File(graphDir, testName + ".svg");
		File dotFile = new File(graphDir, testName + ".dot");
		
		writer.toSvg(svgFile);
		writer.toDot(dotFile);
	}
	
	@Test
	public void simpleTask() throws IOException {
		String testName = "simpleTask";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		
		taskA.asGraph().getGraphDescription().write(writer);
		
		assertTrue(taskA.equals(taskA));

		assertTrue(taskA.asNode().isRoot());
		assertTrue(taskA.asNode().isNode());
		assertTrue(taskA.asNode().isRootNode());

		assertFalse(taskA.asNode().isCluster());
		assertFalse(taskA.asNode().isRootCluster());
		
		taskA.asGraph().forEachEdge((from, to) -> assertTrue(false)); // should not have any edge
		
		NtroTask otherA = new NtroTaskAsyncTest("A");
		
		assertTrue(taskA.equals(otherA));
		assertTrue(taskA.asGraph().isSameGraphAs(otherA.asGraph()));

		NtroTask taskB = new NtroTaskAsyncTest("B");
		
		assertFalse(taskA.equals(taskB));

		writeFiles(writer, testName);
	}

	@Test
	public void subTask() throws IOException {
		String testName = "subTask";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask parentTask = new NtroTaskAsyncTest("parent");
		NtroTask childTask = new NtroTaskAsyncTest("child");
		
		parentTask.addSubTask(childTask);
		
		childTask.asGraph().getGraphDescription().write(writer);

		assertTrue(parentTask.asNode().isRoot());
		assertTrue(parentTask.asNode().isCluster());

		assertFalse(childTask.asNode().isRoot());
		assertFalse(childTask.asNode().isCluster());

		parentTask.asGraph().forEachEdge((from, to) -> assertTrue(false)); // should not have edges
		childTask.asGraph().forEachEdge((from, to) -> assertTrue(false)); // should not have edges
		
		parentTask.asNode().forEachSubNode(sn -> assertTrue(sn.asTask().equals(childTask)));
		assertTrue(childTask.asNode().getParentNode().asTask().equals(parentTask));

		assertTrue(parentTask.asGraph().isSameGraphAs(childTask.asGraph()));

		NtroTask otherParent = new NtroTaskAsyncTest("parent");
		NtroTask otherChild = new NtroTaskAsyncTest("child");
		
		otherParent.addSubTask(otherChild);
		
		assertTrue(otherParent.asGraph().isSameGraphAs(parentTask.asGraph()));
		assertTrue(otherParent.asGraph().isSameGraphAs(childTask.asGraph()));
		assertTrue(otherChild.asGraph().isSameGraphAs(parentTask.asGraph()));
		assertTrue(otherChild.asGraph().isSameGraphAs(childTask.asGraph()));

		NtroTask sameParent = new NtroTaskAsyncTest("parent");
		NtroTask sameChild = new NtroTaskAsyncTest("child");
		NtroTask butHasNext = new NtroTaskAsyncTest("next");
		
		sameParent.addSubTask(sameChild);
		sameChild.addNextTask(butHasNext);
		
		assertTrue(sameParent.equals(parentTask));
		assertFalse(sameParent.asGraph().isSameGraphAs(parentTask.asGraph()));

		NtroTask biggerParent = new NtroTaskAsyncTest("parent");
		NtroTask biggerChild = new NtroTaskAsyncTest("child");
		NtroTask grandChild = new NtroTaskAsyncTest("grandChild");
		
		biggerParent.addSubTask(biggerChild);
		biggerChild.addSubTask(grandChild);
		
		assertFalse(biggerParent.equals(parentTask));
		assertFalse(biggerChild.equals(childTask));
		assertFalse(biggerParent.asGraph().isSameGraphAs(parentTask.asGraph()));
		
		writeFiles(writer, testName);
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
		
		assertTrue(taskA.asGraph().isSameGraphAs(taskB.asGraph()));

		NtroTask otherA = new NtroTaskAsyncTest("A");
		NtroTask otherB = new NtroTaskAsyncTest("B");
		
		otherB.addPreviousTask(otherA);

		assertTrue(otherA.asGraph().isSameGraphAs(taskA.asGraph()));
		assertTrue(otherA.asGraph().isSameGraphAs(taskB.asGraph()));
		assertTrue(otherB.asGraph().isSameGraphAs(taskA.asGraph()));
		assertTrue(otherB.asGraph().isSameGraphAs(taskB.asGraph()));

		taskB.asGraph().getGraphDescription().write(writer);

		writeFiles(writer, testName);
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

		assertTrue(taskA.asGraph().isSameGraphAs(taskB.asGraph()));

		taskB.asGraph().getGraphDescription().write(writer);

		writeFiles(writer, testName);
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

		assertTrue(taskA.asGraph().isSameGraphAs(taskD.asGraph()));
		assertTrue(taskB.asGraph().isSameGraphAs(taskD.asGraph()));
		assertTrue(taskC.asGraph().isSameGraphAs(taskD.asGraph()));
		
		taskA.asNode().forEachSubNode(sn -> {
			assertTrue(sn.asTask().equals(taskB));
		});

		taskA.asNode().forEachSubNodeTransitive(sn -> {
			assertTrue(sn.asTask().equals(taskB)
					|| sn.asTask().equals(taskC)
					|| sn.asTask().equals(taskD));
		});
		
		taskB.asNode().getParentNode().equals(taskB);
		taskB.asNode().forEachSubNode(sn -> {
			assertTrue(sn.asTask().equals(taskC));
		});
		taskB.asNode().forEachSubNodeTransitive(sn -> {
			assertTrue(sn.asTask().equals(taskC) 
					|| sn.asTask().equals(taskD));
		});

		NtroTask otherC = new NtroTaskAsyncTest("C");
		NtroTask otherD = new NtroTaskAsyncTest("D");
		
		otherC.addSubTask(otherD);
		
		assertTrue(otherC.equals(taskC));
		assertFalse(otherC.asGraph().isSameGraphAs(taskC.asGraph()));
		
		taskD.asGraph().getGraphDescription().write(writer);

		writeFiles(writer, testName);
	}

	@Test
	public void simpleGraph() throws IOException {
		String testName = "simpleGraph";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		NtroTask taskB = new NtroTaskAsyncTest("B");
		NtroTask taskC = new NtroTaskAsyncTest("C");
		
		taskA.addSubTask(taskB);
		taskA.addSubTask(taskC);
		
		taskB.addNextTask(taskC);
		
		taskC.asGraph().getGraphDescription().write(writer);

		writeFiles(writer, testName);

		TraceTester traceTester = traceTesterForSimpleGraph();
		
		GraphTraceConnector trace = taskA.execute();
		trace.addGraphWriter(new GraphTraceWriterJdk(new File(graphDir, testName)));
		trace.addTaskStateListener(traceTester);

		traceTester = traceTesterForSimpleGraph();
		taskC.asGraph().resetGraph();
		trace = taskA.execute();
		
		NtroTask newC = new NtroTaskAsyncTest("C");
		
		// XXX: replacing during execution
		taskC.replaceWith(newC);
		
		trace.addTaskStateListener(traceTester);
	}

	private TraceTester traceTesterForSimpleGraph() {
		TraceTester traceTester = new TraceTester();

		traceTester.mustFinishBefore("A_B", "A");
		traceTester.mustFinishBefore("A_C", "A");
		traceTester.mustFinishBefore("A_B", "A_C");
		return traceTester;
	}

	@Test
	public void biggerGraph() throws IOException {
		String testName = "biggerGraph";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
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

		NtroTask taskA3Bis = new NtroTaskAsyncTest("A3");
		NtroTask taskA3A = new NtroTaskAsyncTest("A3A");
		NtroTask taskA3B = new NtroTaskAsyncTest("A3B");

		taskA3Bis.addSubTask(taskA3A);
		taskA3Bis.addSubTask(taskA3B);
		taskA3A.addNextTask(taskA3B);
		
		taskA3.replaceWith(taskA3Bis);

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

		taskC.asGraph().getGraphDescription().write(writer);

		writeFiles(writer, testName);

		/*
		Node nodeA = taskB3.asGraph().findNodeById("A");
		assertTrue(nodeA.asTask().equals(taskA));

		Node nodeB = taskD.asGraph().findNodeById("B");
		assertTrue(nodeB.asTask().equals(taskB));

		Node nodeB3 = taskA.asGraph().findNodeById("B3");
		assertTrue(nodeB3.asTask().equals(taskB3));

		Node nodeD = taskA.asGraph().findNodeById("D");
		assertTrue(nodeD.asTask().equals(taskD));
		*/
		
		GraphTraceConnector trace = taskA.execute();
		trace.addGraphWriter(new GraphTraceWriterJdk(new File(graphDir, testName)));
		
		TraceTester traceTester = traceTesterForBiggerGraph();
		//trace.addTaskStateListener(traceTester);
		
		NtroTask taskB3Bis = new NtroTaskAsyncTest("B3");
		NtroTask taskB3A = new NtroTaskAsyncTest("B3A");
		NtroTask taskB3B = new NtroTaskAsyncTest("B3B");
		
		taskB3Bis.addSubTask(taskB3A);
		taskB3Bis.addSubTask(taskB3B);

		taskB3A.addNextTask(taskB3B);
		
		// XXX: replacing during execution
		taskB3.replaceWith(taskB3Bis);

		writer = new GraphWriterJdk(testName);
		taskA.asGraph().getGraphDescription().write(writer);
		writeFiles(writer, testName);
	}

	private TraceTester traceTesterForBiggerGraph() {
		TraceTester traceTester = new TraceTester();

		traceTester.mustFinishBefore("A1", "A");
		traceTester.mustFinishBefore("A2", "A");
		traceTester.mustFinishBefore("A3", "A");
		traceTester.mustFinishBefore("A1", "A3");
		traceTester.mustFinishBefore("A1", "A2");
		traceTester.mustFinishBefore("A", "B");
		traceTester.mustFinishBefore("A1", "B");
		traceTester.mustFinishBefore("A2", "B");
		traceTester.mustFinishBefore("A3", "B");
		traceTester.mustFinishBefore("B", "C");
		traceTester.mustFinishBefore("B1", "B");
		traceTester.mustFinishBefore("B2", "B");
		traceTester.mustFinishBefore("B3", "B");
		traceTester.mustFinishBefore("B1", "B3");
		traceTester.mustFinishBefore("B2", "B3");
		traceTester.mustFinishBefore("C", "D");
		traceTester.mustFinishBefore("B2", "D");

		return traceTester;
	}
	
	@Test
	public void cycle() throws IOException {
		String testName = "cycle";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		NtroTask taskB = new NtroTaskAsyncTest("B");
		NtroTask taskC = new NtroTaskAsyncTest("C");
		
		taskA.addSubTask(taskB);
		taskA.addSubTask(taskC);
		
		taskB.addNextTask(taskC);
		taskC.addNextTask(taskB);
		
		taskC.asGraph().getGraphDescription().write(writer);

		writeFiles(writer, testName);
		
		GraphTraceConnector trace = taskC.execute();
		trace.addGraphWriter(new GraphTraceWriterJdk(new File(graphDir, testName)));
	}

	@Test
	public void biggerCycle() throws IOException {
		String testName = "biggerCycle";
		GraphWriterJdk writer = new GraphWriterJdk(testName);
		
		NtroTask taskA = new NtroTaskAsyncTest("A");
		NtroTask taskB = new NtroTaskAsyncTest("B");
		NtroTask taskC = new NtroTaskAsyncTest("C");
		NtroTask taskD = new NtroTaskAsyncTest("D");
		NtroTask taskE = new NtroTaskAsyncTest("E");
		NtroTask taskF = new NtroTaskAsyncTest("F");
		
		taskA.addSubTask(taskB);
		taskA.addSubTask(taskC);
		taskA.addSubTask(taskD);
		taskA.addSubTask(taskE);
		taskA.addSubTask(taskF);
		
		taskB.addNextTask(taskC);
		taskC.addNextTask(taskD);
		taskD.addNextTask(taskE);
		taskE.addNextTask(taskF);
		taskF.addNextTask(taskB);
		
		taskC.asGraph().getGraphDescription().write(writer);

		writeFiles(writer, testName);
		
		GraphTraceConnector trace = taskC.execute();
		trace.addGraphWriter(new GraphTraceWriterJdk(new File(graphDir, testName)));
	}
	

	@After
	public void tearDown() {
	}

}
