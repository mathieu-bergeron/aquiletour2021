package ca.ntro.core.task2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class GraphWriterMemory implements GraphWriter {

	private class Node implements NodeDescription {
		public NodeDescription wrappedSpec;
		public Node parent;
		public Node(NodeDescription wrappedSpec) {
			this.wrappedSpec = wrappedSpec;
		}
		@Override
		public String getId() {
			return wrappedSpec.getId();
		}
		@Override
		public String getLabel() {
			return wrappedSpec.getLabel();
		}
		@Override
		public int hashCode() {
			return Objects.hash(wrappedSpec);
		}
		@Override 
		public boolean equals(Object other) {
			if(this == other) return true;
			if(!(other instanceof Node)) return false;
			return ((Node)other).wrappedSpec.equals(wrappedSpec);
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
		public Cluster(NodeDescription wrappedSpec) {
			super(wrappedSpec);
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
			return Objects.hash(from, to);
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

	private Map<String, Node> nodes = new HashMap<>();
	private Set<Edge> edges = new HashSet<>();
	
	public boolean hasCluster(String clusterId) {
		return nodes.containsKey(clusterId) &&
				nodes.get(clusterId) instanceof Cluster;
	}

	@Override
	public void addRootCluster(NodeDescription clusterSpec) {
		nodes.put(clusterSpec.getId(), new Cluster(clusterSpec));
	}

	public boolean hasNode(String nodeId) {
		return nodes.containsKey(nodeId) &&
				nodes.get(nodeId) instanceof Node;
	}

	@Override
	public void addRootNode(NodeDescription nodeSpec) {
		nodes.put(nodeSpec.getId(), new Node(nodeSpec));
	}

	@Override
	public void addSubCluster(NodeDescription cluster, NodeDescription subClusterSpec) {
		Cluster subCluster = new Cluster(subClusterSpec);
		nodes.put(subClusterSpec.getId(), subCluster);
		getCluster(cluster.getId()).add(subCluster);
	}

	@Override
	public void addSubNode(NodeDescription cluster, NodeDescription subNodeSpec) {
		Node subNode = new Node(subNodeSpec);
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

	@Override
	public void addEdge(NodeDescription fromSpec, NodeDescription toSpec) {
		Node from = getNode(fromSpec.getId());
		Node to = getNode(toSpec.getId());
		
		edges.add(new Edge(from, to));
	}
}

