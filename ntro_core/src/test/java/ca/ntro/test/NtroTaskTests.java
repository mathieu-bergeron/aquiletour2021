package ca.ntro.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.ntro.core.task2.NtroTask;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

import static guru.nidi.graphviz.model.Factory.*;
import static guru.nidi.graphviz.attribute.Attributes.attr;

import java.io.File;
import java.io.IOException;

public class NtroTaskTests {
	
	public Graph toGraph(NtroTask task) {
		
		return null;
	}

	@Before
	public void setUp() {
	}

	@Test
	public void createTask() throws IOException {
		
		Graph g = graph("example1").directed()
		        .graphAttr().with(Rank.dir(RankDir.LEFT_TO_RIGHT))
		        .nodeAttr().with(Font.name("arial"))
		        .linkAttr().with("class", "link-class")
		        .with(
		                node("a").with(Color.RED).link(node("b")),
		                node("b").link(
		                        to(node("c")).with(attr("weight", 5), Style.DASHED)
		                )
		        );

		Graphviz.fromGraph(g).height(100).render(Format.PNG).toFile(new File("__tasks__/ex1.png"));
	}

	@After
	public void tearDown() {
	}

}
