

import java.util.*;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;


/*
 * SD2x Homework #6
 * This is an implementation of Breadth First Search (BFS) on a graph.
 * You may modify and submit this code if you'd like.
 */

public class BreadthFirstSearch {
	protected Graph graph;
	protected List<Node> exploredVertices;

	public BreadthFirstSearch(Graph graphToSearch) {
		graph = graphToSearch;
	}
	
	/**
	 * This method was discussed in the lesson
	 */
	private Map<Node, Node> bfs(Node initialVertex) {
		if (!graph.containsNode(initialVertex)) {
			return new HashMap<Node, Node>();
		}
		// Initialize data structures.
		exploredVertices = new LinkedList<>();
		Queue<Edge> queueingStructure = new LinkedList<>();
		Map<Node, Node> parentMap = new HashMap<>();
		// Populate queue with first edge.
		Edge initialEdge = new Edge(null, initialVertex);
		queueingStructure.add(initialEdge);
		while (!queueingStructure.isEmpty()) {
			Edge currentEdge = queueingStructure.remove();
			Node parent = currentEdge.getSource();
			Node currentVertex = currentEdge.getDestination();
			if (isExplored(currentVertex)) {
				continue;
			}

			exploredVertices.add(currentVertex);
			// We will be able to find out a Node's parent with this structure.
			parentMap.put(currentVertex, parent);
			// Add all of currentVertex's neighbors to the queue.
			Set<Node> neighbors = graph.getNodeNeighbors(currentVertex);
			addNeighborsToQueue(queueingStructure, currentVertex, neighbors);
		}

		return parentMap;
	}

	private Set<String> bfs(Node initialVertex, int distance) {
		if (!graph.containsNode(initialVertex)) {
			return new HashSet<>();
		}

		// Initialize data structures.
		exploredVertices = new LinkedList<>();
		Queue<Entry<Integer, List<Node>>> queueingStructure = new LinkedList<>();
		Set<String> nodesWithinDistance = new HashSet<>();
		// Populate queue with first vertex.
		List<Node> foo = new LinkedList<Node>();
		foo.add(initialVertex);
		// Which is at a distance of 0 from itself.
		Entry<Integer, List<Node>> initialTier = new SimpleImmutableEntry<>(0, foo);
		queueingStructure.add(initialTier);
		while (!queueingStructure.isEmpty()) {
			Entry<Integer, List<Node>> currentTier = queueingStructure.remove();
			int currentDistance = currentTier.getKey();
			List<Node> vertices = currentTier.getValue();
			// When this is true, we will have added every element to the set that
			// is less than or equal to the specified distance.
			if (currentDistance > distance) {
				break;
			}

			// We will be adding all of the neighbors the queue, which are one unit
			// higher than the current tier.
			int nextDistance = currentDistance + 1;
			for (Node currentVertex : vertices) {
				if (isExplored(currentVertex)) {
					continue;
				}

				if (currentVertex != initialVertex) {
					nodesWithinDistance.add(currentVertex.getElement());
				}

				exploredVertices.add(currentVertex);
				Set<Node> neighbors = graph.getNodeNeighbors(currentVertex);
				addNeighborsToQueue(queueingStructure, neighbors, nextDistance);
			}

		}

		return nodesWithinDistance;
	}

	private void addNeighborsToQueue(Queue<Edge> queue, Node parent, Set<Node> neighbors) {
		for (Node neighbor : neighbors) {
			if (isExplored(neighbor)) {
				continue;
			}

			Edge nextEdge = new Edge(parent, neighbor);
			queue.add(nextEdge);
		}
	}


	private void addNeighborsToQueue(Queue<Entry<Integer, List<Node>>> queue, Set<Node> neighbors, int distance) {
		for (Node neighbor : neighbors) {
			if (isExplored(neighbor)) {
				continue;
			}

			List<Node> neighborList = new LinkedList<>(neighbors);
			Entry<Integer, List<Node>> nextPair = new SimpleImmutableEntry<>(distance, neighborList);
			queue.add(nextPair);
		}
	}

	private boolean isExplored(Node n) {
		return exploredVertices.contains(n);
	}

	public List<Node> generateWalk(Node start, Node end) {
		Map<Node, Node> parentDict = bfs(start);
		if (!(parentDict.containsKey(start) && parentDict.containsKey(end))) {
			return new LinkedList<>();
		}

		List<Node> walk = new LinkedList<>();
		walk.add(end);
		// We iterate from the end of the walk to the beginning.
		Node currentVertex = end;
		while (true) {
			if (!parentDict.containsKey(currentVertex)) {
				break;
			}

			Node parentVertex = parentDict.get(currentVertex);
			// When we arrive back at the start, we are done.
			if (parentVertex == start) {
				break;
			}

			walk.add(parentVertex);
			currentVertex = parentVertex;
		}

		// Ensure walk is in order from start to end.
		Collections.reverse(walk);
		return walk;
	}

	public Set<String> nodesWithinDistance(Node start, int distance) {
		Set<String> nodes = bfs(start, distance);
		return nodes;
	}
}
