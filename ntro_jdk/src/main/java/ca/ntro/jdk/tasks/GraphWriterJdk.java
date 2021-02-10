package ca.ntro.jdk.tasks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.task2.GraphWriter;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphWriterJdk implements GraphWriter {
	
	private MutableGraph graph;
	private Map<String, MutableGraph> clusters = new HashMap<>();
	private Map<String, MutableNode> nodes = new HashMap<>();
	
	public GraphWriterJdk(String graphName) {
		graph = mutGraph(graphName).setDirected(true)
				.graphAttrs().add(Rank.dir(RankDir.LEFT_TO_RIGHT));
	}

	public void toFile(File file) throws IOException {
		Graphviz.fromGraph(graph).height(100).render(Format.PNG).toFile(file);
	}

	@Override
	public void addCluster(String clusterId) {
		MutableGraph cluster = mutGraph(clusterId).setCluster(true);

		clusters.put(clusterId, cluster);

		graph.add(cluster);
	}

	@Override
	public void addNode(String nodeId) {
		MutableNode node = mutNode(nodeId);
		nodes.put(nodeId, node);
		graph.add(node);
	}

	@Override
	public void addNodeToCluster(String clusterId, String nodeId) {
		addNode(nodeId);
		MutableGraph cluster = clusters.get(clusterId);
		MutableNode node = nodes.get(nodeId);

		cluster.add(node);
	}
}
