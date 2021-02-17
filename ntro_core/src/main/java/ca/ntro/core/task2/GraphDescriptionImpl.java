package ca.ntro.core.task2;

import java.util.HashSet;
import java.util.Set;

public class GraphDescriptionImpl implements GraphDescription {
	
	private Set<NodeDescription> nodes = new HashSet<>();
	private Set<NodeDescription> startNodes = new HashSet<>();
	private Set<EdgeDescription> edges = new HashSet<>();
}
