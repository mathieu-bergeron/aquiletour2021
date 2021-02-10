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
		public Node parent;
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
		public void write(GraphWriter writer, Set<Node> visitedNodes) {
			if(visitedNodes.contains(this)) return;
			visitedNodes.add(this);

			if(parent == null) {
				writer.addRootNode(this);
			}else {
				parent.write(writer, visitedNodes);
				writer.addSubNode(parent, this);
			}
		}
	}

	private class Cluster extends Node {
		public Set<Node> children = new HashSet<>();
		public Cluster(String id) {
			super(id);
		}
		public void add(Node node) {
			children.add(node);
			node.parent = this;
		}
		@Override
		public void write(GraphWriter writer, Set<Node> visitedNodes) {
			if(visitedNodes.contains(this)) return;
			visitedNodes.add(this);
			
			if(parent == null) {
				writer.addRootCluster(this);
			}else {
				parent.write(writer, visitedNodes);
				writer.addSubCluster(parent, this);
			}

			children.forEach(n -> n.write(writer, visitedNodes));
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

		nodes.values().forEach(n -> n.write(writer, new HashSet<>()));
		edges.forEach(e -> e.write(writer));

		return writer;
	}

	public boolean hasCluster(String clusterId) {
		return nodes.containsKey(clusterId) &&
				nodes.get(clusterId) instanceof Cluster;
	}

	@Override
	public void addRootCluster(Identifiable clusterSpec) {
		nodes.put(clusterSpec.getId(), new Cluster(clusterSpec.getId()));
	}

	public boolean hasNode(String nodeId) {
		return nodes.containsKey(nodeId) &&
				nodes.get(nodeId) instanceof Node;
	}

	@Override
	public void addRootNode(Identifiable nodeSpec) {
		nodes.put(nodeSpec.getId(), new Node(nodeSpec.getId()));
	}

	@Override
	public void addSubCluster(Identifiable cluster, Identifiable subClusterSpec) {
		Cluster subCluster = new Cluster(subClusterSpec.getId());
		nodes.put(subClusterSpec.getId(), subCluster);
		getCluster(cluster.getId()).add(subCluster);
	}

	@Override
	public void addSubNode(Identifiable cluster, Identifiable subNodeSpec) {
		Node subNode = new Node(subNodeSpec.getId());
		nodes.put(subNodeSpec.getId(), subNode);
		getCluster(cluster.getId()).add(subNode);
	}

	public boolean ifClusterContains(String clusterId, String nodeId) {
		return getCluster(clusterId).children.contains(getNode(nodeId));
	}

	private Node getNode(String nodeId) {
		return (Node) nodes.get(nodeId);
	}

	private Cluster getCluster(String clusterId) {
		Cluster cluster = (Cluster) nodes.get(clusterId);
		return cluster;
	}

	public boolean hasEdge(String fromId, String toId) {
		
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
