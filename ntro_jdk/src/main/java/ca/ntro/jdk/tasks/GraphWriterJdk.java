package ca.ntro.jdk.tasks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.task2.GraphWriter;
import ca.ntro.core.task2.Identifiable;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.LinkSource;
import guru.nidi.graphviz.model.LinkTarget;
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
	public void addCluster(Identifiable clusterSpec) {
		MutableGraph cluster = mutGraph(clusterSpec.getId()).setCluster(true);

		clusters.put(clusterSpec.getId(), cluster);

		graph.add(cluster);
	}

	@Override
	public void addNode(Identifiable nodeSpec) {
		MutableNode node = mutNode(nodeSpec.getId());
		nodes.put(nodeSpec.getId(), node);
		graph.add(node);
	}

	@Override
	public void addNodeToCluster(Identifiable clusterSpec, Identifiable nodeSpec) {
		addNode(nodeSpec);
		MutableGraph cluster = clusters.get(clusterSpec.getId());
		MutableNode node = nodes.get(nodeSpec.getId());

		cluster.add(node);
	}

	@Override
	public void addEdge(Identifiable fromSpec, Identifiable toSpec) {
		MutableGraph fromCluster = clusters.get(fromSpec.getId());
		MutableNode fromNode = nodes.get(fromSpec.getId());
		LinkTarget to = getLinkTarget(toSpec.getId());
		
		if(fromCluster != null) {
			fromCluster.addLink(to);

		}else if(fromNode != null){
			fromNode.addLink(to);
		}
	}

	private LinkTarget getLinkTarget(String id) {
		return (LinkTarget) getNodeOrCluster(id);
	}

	private LinkSource getLinkSource(String id) {
		return (LinkSource) getNodeOrCluster(id);
	}
	
	private Object getNodeOrCluster(String id) {
		Object nodeOrCluster = clusters.get(id);
		if(nodeOrCluster == null) {
			nodeOrCluster = nodes.get(id);
		}
		
		return nodeOrCluster;
	}
	
}
