package ca.ntro.jdk.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.task2.GraphWriter;
import ca.ntro.jdk.tasks.GraphWriterJdk;


public class GraphWriterTestJdk implements GraphWriter {
	
	private GraphWriterJdk graphWriterJdk;
	private Map<String, Set<String>> clusters = new HashMap<>();
	private Set<String> nodes = new HashSet<>();
	
	public GraphWriterTestJdk(String graphName) {
		graphWriterJdk = new GraphWriterJdk(graphName);
	}
	
	public void toFile(File file) throws IOException {
		graphWriterJdk.toFile(file);
	}

	public boolean hasCluster(String clusterId) {
		return clusters.containsKey(clusterId);
	}

	@Override
	public void addCluster(String clusterId) {
		clusters.put(clusterId, new HashSet<>());
		graphWriterJdk.addCluster(clusterId);
	}

	public boolean hasNode(String nodeId) {
		return nodes.contains(nodeId);
	}

	@Override
	public void addNode(String nodeId) {
		T.call(this);
		nodes.add(nodeId);
		graphWriterJdk.addNode(nodeId);
	}

	@Override
	public void addNodeToCluster(String clusterId, String nodeId) {
		Set<String> clusterNodes = clusters.get(clusterId);
		clusterNodes.add(nodeId);
		nodes.add(nodeId);
	}

	public boolean clusterContains(String clusterId, String nodeId) {
		Set<String> clusterNodes = clusters.get(clusterId);
		return clusterNodes.contains(nodeId);
	}

	public boolean hasEdge(String string, String string2) {
		return false;
	}

}
