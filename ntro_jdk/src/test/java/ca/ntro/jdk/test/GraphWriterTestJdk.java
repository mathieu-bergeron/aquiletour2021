package ca.ntro.jdk.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.task2.GraphWriter;
import ca.ntro.core.task2.Identifiable;
import ca.ntro.jdk.tasks.GraphWriterJdk;


public class GraphWriterTestJdk implements GraphWriter {
	
	private GraphWriterJdk graphWriterJdk;
	
	private class Node {}

	private class Cluster extends Node {
		public Set<String> children = new HashSet<>();
		public void add(String node) {
			children.add(node);
		}
	}

	private class Edge {
		public String from;
		public String to;
		public Edge(String from, String to) {
			this.from = from;
			this.to = to;
		}
	}

	private Map<String, Node> nodes = new HashMap<>();
	private Set<Edge> edges = new HashSet<>();
	
	public GraphWriterTestJdk(String graphName) {
		graphWriterJdk = new GraphWriterJdk(graphName);
	}
	
	public void toFile(File file) throws IOException {
		graphWriterJdk.toFile(file);
	}

	public boolean hasCluster(String clusterId) {
		return nodes.containsKey(clusterId) &&
				nodes.get(clusterId) instanceof Cluster;
	}

	@Override
	public void addCluster(Identifiable cluster) {
		clusters.put(cluster.getId(), new HashSet<>());
		graphWriterJdk.addCluster(cluster);
	}

	public boolean hasNode(String nodeId) {
		return nodes.contains(nodeId);
	}

	@Override
	public void addNode(Identifiable node) {
		nodes.add(node.getId());

		graphWriterJdk.addNode(node);
	}

	@Override
	public void addNodeToCluster(Identifiable cluster, Identifiable node) {
		Set<String> clusterNodes = clusters.get(cluster.getId());
		clusterNodes.add(node.getId());
		nodes.add(node.getId());

		graphWriterJdk.addNodeToCluster(cluster, node);
	}

	public boolean clusterContains(String clusterId, String nodeId) {
		Set<String> clusterNodes = clusters.get(clusterId);
		return clusterNodes.contains(nodeId);
	}

	public boolean hasEdge(String string, String string2) {
		return false;
	}

	@Override
	public void addEdge(Identifiable from, Identifiable to) {
		
	}

}
