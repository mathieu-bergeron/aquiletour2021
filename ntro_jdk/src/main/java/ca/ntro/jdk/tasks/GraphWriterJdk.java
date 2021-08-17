package ca.ntro.jdk.tasks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.tasks.GraphWriter;
import ca.ntro.core.tasks.NodeDescription;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphWriterJdk implements GraphWriter {
	
	private MutableGraph graph;
	private Map<String, MutableGraph> clusters = new HashMap<>();
	private Map<String, MutableNode> clusterInvisibleNodes = new HashMap<>();
	private Map<String, MutableNode> nodes = new HashMap<>();
	
	public GraphWriterJdk(String graphName) {
		graph = mutGraph(graphName).setDirected(true)
				.graphAttrs().add(Rank.dir(RankDir.LEFT_TO_RIGHT))
				.graphAttrs().add("compound", "true");
	}

	public void toPng(File file) throws IOException {
		Graphviz.fromGraph(graph).render(Format.PNG).toFile(file);
	}

	public void toSvg(File file) throws IOException {
		Graphviz.fromGraph(graph).render(Format.SVG).toFile(file);
	}

	public void toDot(File file) throws IOException {
		Graphviz.fromGraph(graph).render(Format.DOT).toFile(file);
	}

	@Override
	public void addRootCluster(NodeDescription clusterSpec) {
		MutableGraph cluster = createCluster(clusterSpec);
		graph.add(cluster);
	}

	private MutableGraph createCluster(NodeDescription clusterSpec) {
		if(clusters.containsKey(clusterSpec.getNodeId())) return clusters.get(clusterSpec.getNodeId());
		
		MutableGraph cluster = mutGraph(clusterSpec.getNodeId());
		cluster.setCluster(true);
		cluster.graphAttrs().add(Rank.dir(RankDir.LEFT_TO_RIGHT));
		cluster.graphAttrs().add(Label.of(clusterSpec.getLabel()));
		cluster.setDirected(true);

		clusters.put(clusterSpec.getNodeId(), cluster);

		return cluster;
	}

	private void createClusterInvisibleNode(MutableGraph cluster) {
		if(clusterInvisibleNodes.containsKey(cluster.name().toString())) return;

		String clusterInvisibleNodeId = "__" + cluster.name() + "__";
		MutableNode clusterInvisiableNode = mutNode(clusterInvisibleNodeId);
		clusterInvisiableNode.attrs().add("shape", "none");
		clusterInvisiableNode.attrs().add("style", "invis");
		clusterInvisiableNode.attrs().add("label", "");
		
		cluster.add(clusterInvisiableNode);
		clusterInvisibleNodes.put(cluster.name().toString(), clusterInvisiableNode);
	}

	@Override
	public void addRootNode(NodeDescription nodeSpec) {
		MutableNode node = createNode(nodeSpec);
		graph.add(node);
	}

	private MutableNode createNode(NodeDescription nodeSpec) {
		if(nodes.containsKey(nodeSpec.getNodeId())) return nodes.get(nodeSpec.getNodeId());

		MutableNode node = mutNode(nodeSpec.getNodeId());
		node.attrs().add(Label.of(nodeSpec.getLabel()));
		nodes.put(nodeSpec.getNodeId(), node);
		return node;
	}

	@Override
	public void addSubCluster(NodeDescription clusterSpec, NodeDescription subClusterSpec) {
		MutableGraph cluster = clusters.get(clusterSpec.getNodeId());
		cluster.add(createCluster(subClusterSpec));
	}

	@Override
	public void addSubNode(NodeDescription clusterSpec, NodeDescription subNodeSpec) {
		MutableGraph cluster = clusters.get(clusterSpec.getNodeId());
		cluster.add(createNode(subNodeSpec));
	}

	@Override
	public void addEdge(NodeDescription fromSpec, NodeDescription toSpec) {
		MutableGraph fromCluster = clusters.get(fromSpec.getNodeId());
		MutableGraph toCluster = clusters.get(toSpec.getNodeId());
		
		Link link = null;
		
		if(toCluster != null) {
			createClusterInvisibleNode(toCluster);
			MutableNode toInvisibleNode = clusterInvisibleNodes.get(toSpec.getNodeId());
			link = Link.to(toInvisibleNode);
			link.attrs().add("lhead","cluster_" + toCluster.name());

		}else {
			
			MutableNode toNode = mutNode(toSpec.getNodeId());
			graph.add(toNode);
			link = Link.to(toNode);
		}

		MustNot.beNull(link);
		
		if(fromCluster != null) {
			createClusterInvisibleNode(fromCluster);
			MutableNode fromInvisibleNode = clusterInvisibleNodes.get(fromSpec.getNodeId());

			MutableNode fromNode = mutNode(fromInvisibleNode.name());
			link.attrs().add("ltail","cluster_" + fromCluster.name());
			fromNode.links().add(link);
			graph.add(fromNode);
			
		} else {

			MutableNode fromNode = mutNode(fromSpec.getNodeId());
			fromNode.links().add(link);
			graph.add(fromNode);
		}
	}
}
