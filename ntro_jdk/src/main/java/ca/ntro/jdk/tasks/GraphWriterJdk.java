package ca.ntro.jdk.tasks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.task2.GraphWriter;
import ca.ntro.core.task2.Identifiable;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
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
		System.out.println(graph);
		Graphviz.fromGraph(graph).height(100).render(Format.PNG).toFile(file);
	}

	@Override
	public void addCluster(Identifiable clusterSpec) {
		System.out.println("Jdk.addCluster: " + clusterSpec.getId());

		MutableGraph cluster = createCluster(clusterSpec);
		graph.add(cluster);
	}

	private MutableGraph createCluster(Identifiable clusterSpec) {
		if(clusters.containsKey(clusterSpec.getId())) return clusters.get(clusterSpec.getId());
		
		MutableGraph cluster = mutGraph(clusterSpec.getId());
		cluster.setCluster(true);

		cluster.graphAttrs().add(Label.of(clusterSpec.getId()));
		
		clusters.put(clusterSpec.getId(), cluster);

		return cluster;
	}

	@Override
	public void addNode(Identifiable nodeSpec) {
		MutableNode node = createNode(nodeSpec);
		graph.add(node);
	}

	private MutableNode createNode(Identifiable nodeSpec) {
		if(nodes.containsKey(nodeSpec.getId())) return nodes.get(nodeSpec.getId());

		MutableNode node = mutNode(nodeSpec.getId());
		nodes.put(nodeSpec.getId(), node);
		return node;
	}

	@Override
	public void addSubCluster(Identifiable clusterSpec, Identifiable subClusterSpec) {
		MutableGraph cluster = clusters.get(clusterSpec.getId());
		cluster.add(createCluster(subClusterSpec));
	}

	@Override
	public void addSubNode(Identifiable clusterSpec, Identifiable subNodeSpec) {
		MutableGraph cluster = clusters.get(clusterSpec.getId());
		cluster.add(createNode(subNodeSpec));
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
	
	private Object getNodeOrCluster(String id) {
		Object nodeOrCluster = clusters.get(id);
		if(nodeOrCluster == null) {
			nodeOrCluster = nodes.get(id);
		}
		
		return nodeOrCluster;
	}


	
}
