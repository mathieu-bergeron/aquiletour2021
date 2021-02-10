package ca.ntro.jdk.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.task2.GraphWriter;
import ca.ntro.core.task2.Identifiable;
import ca.ntro.jdk.tasks.GraphWriterJdk;


public class GraphWriterTest implements GraphWriter {
	
	private class Node implements Identifiable {
		public String id;
		public Node(String id) {
			this.id = id;
		}
		@Override
		public String getId() {
			return id;
		}
		@Override
		public int hashCode() {
			return id.hashCode();
		}
		@Override 
		public boolean equals(Object other) {
			if(!(other instanceof Node)) return false;
			return ((Node)other).id.equals(id);
		}
		public void write(GraphWriter writer) {
			writer.addNode(this);
		}
	}

	private class Cluster extends Node {
		public Set<Node> children = new HashSet<>();
		public Cluster(String id) {
			super(id);
		}
		public void add(Node node) {
			children.add(node);
		}
		@Override
		public void write(GraphWriter writer) {
			writer.addCluster(this);
			children.forEach(n -> writer.addNodeToCluster(this, n));
		}
	}

	private class Edge {
		public Node from;
		public Node to;
		public Edge(Node from, Node to) {
			this.from = from;
			this.to = to;
		}
		@Override
		public int hashCode() {
			return from.hashCode() + to.hashCode();
		}
		@Override
		public boolean equals(Object other) {
			if(!(other instanceof Edge)) return false;
			return ((Edge) other).from.equals(from) 
					&&
					((Edge) other).to.equals(to);
		}
		public void write(GraphWriter writer) {
			writer.addEdge(from, to);
		}
	}

	private String graphName;
	private Map<String, Node> nodes = new HashMap<>();
	private Set<Edge> edges = new HashSet<>();
	
	public GraphWriterTest(String graphName) {
		this.graphName = graphName;
	}
	
	public GraphWriterJdk toGraphWriterJdk() {
		GraphWriterJdk writer = new GraphWriterJdk(graphName);

		nodes.values().forEach(n -> n.write(writer));
		edges.forEach(e -> e.write(writer));

		return writer;
	}

	public boolean hasCluster(String clusterId) {
		return nodes.containsKey(clusterId) &&
				nodes.get(clusterId) instanceof Cluster;
	}

	@Override
	public void addCluster(Identifiable clusterSpec) {
		nodes.put(clusterSpec.getId(), new Cluster(clusterSpec.getId()));
	}

	public boolean hasNode(String nodeId) {
		return nodes.containsKey(nodeId) &&
				nodes.get(nodeId) instanceof Node;
	}

	@Override
	public void addNode(Identifiable nodeSpec) {
		nodes.put(nodeSpec.getId(), new Node(nodeSpec.getId()));
	}

	@Override
	public void addNodeToCluster(Identifiable clusterSpec, Identifiable nodeSpec) {
		Node node = nodes.get(nodeSpec.getId());
		if(node == null) {
			node = new Node(nodeSpec.getId());
			nodes.put(nodeSpec.getId(), node);
		}

		getCluster(clusterSpec.getId()).add(node);
	}

	@Override
	public void addClusterToCluster(Identifiable parentCluster, Identifiable childCluster) {
		Node node = nodes.get(childCluster.getId());
		if(node == null) {
			node = new Node(childCluster.getId());
			nodes.put(childCluster.getId(), node);
		}

		getCluster(parentCluster.getId()).add(node);
	}

	public boolean ifClusterContains(String clusterId, String nodeId) {
		return getCluster(clusterId).children.contains(getNode(nodeId));
	}

	private Node getNode(String nodeId) {
		return (Node) nodes.get(nodeId);
	}

	private Cluster getCluster(String clusterId) {
		return (Cluster) nodes.get(clusterId);
	}

	public boolean hasEdge(String fromId, String toId) {
		System.out.println(edges.size());
		
		
		Node from = getNode(fromId);
		Node to = getNode(toId);
		
		if(from != null && to != null) {
			return edges.contains(new Edge(from,to));
		}

		return false;
	}

	@Override
	public void addEdge(Identifiable fromSpec, Identifiable toSpec) {
		Node from = getNode(fromSpec.getId());
		Node to = getNode(toSpec.getId());
		
		edges.add(new Edge(from, to));
	}


}
