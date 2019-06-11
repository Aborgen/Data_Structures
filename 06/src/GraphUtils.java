

import java.util.*;

/*
 * SD2x Homework #6
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the signature of any of the methods!
 */

public class GraphUtils {

	public static int minDistance(Graph graph, String src, String dest) {
		if (graph == null || !validateString(src) || !validateString(dest)) {
			return -1;
		}

		if (!graph.containsElement(src) || !graph.containsElement(dest)) {
			return -1;
		}

		if (src.compareTo(dest) == 0) {
			return 0;
		}

		BreadthFirstSearch bfSearcher = new BreadthFirstSearch(graph);
		Node start = graph.getNode(src);
		Node end = graph.getNode(dest);
		List<Node> walk = bfSearcher.generateWalk(start, end);
		int walkLength = walk.size();
		// Means there is no possible path.
		if (walkLength == 0) {
			return -1;
		}

		return walkLength;
	}
	

	public static Set<String> nodesWithinDistance(Graph graph, String src, int distance) {
		if (graph == null || !validateString(src) || !graph.containsElement(src) || distance < 1) {
			return null;
		}

		if (graph.numNodes == 1 && distance >= 1) {
			return new HashSet<>();
		}

		BreadthFirstSearch bfSearcher = new BreadthFirstSearch(graph);
		Node start = graph.getNode(src);
		Set<String> nodes = bfSearcher.nodesWithinDistance(start, distance);
		if (nodes.size() == 0) {
			return null;
		}

		return nodes;
	}


	public static boolean isHamiltonianPath(Graph g, List<String> values) {
		if (g == null || values == null || values.isEmpty()) {
			return false;
		}
		// A Hamiltonian cycle requires that each vertex in the graph
		// appear once, except for the beginning and the end, which is identical.
		if (g.numNodes != values.size() - 1) {
			return false;
		}
		// Ensure that the first and last vertex are identical.
		String firstValue = values.get(0);
		String lastValue = values.get(values.size() - 1);
		if (firstValue.compareTo(lastValue) != 0) {
			return false;
		}

		LinkedList<Node> visitedVertices = new LinkedList<>();
		// Ignore the first string within values, as we have already established that it is the same as the last.
		Node currentVertex = null;
		for (int i = 1; i < values.size(); i++) {
			String value = values.get(i);
			if (!validateString(value) || !g.containsElement(value)) {
				return false;
			}

			Node nextVertex = g.getNode(value);
			if (currentVertex != null) {
				if (!ensureConnected(g, currentVertex, nextVertex)) {
					return false;
				}
				// This indicates that the vertex has previously been visited, which isn't Hamiltonian.
				if (visitedVertices.contains(nextVertex)) {
					return false;
				}
			}

			currentVertex = nextVertex;
			visitedVertices.add(nextVertex);
		}

		return true;
	}

	private static boolean ensureConnected(Graph graph, Node v1, Node v2) {
		Set<Node> neighbors = graph.getNodeNeighbors(v1);
		if (!neighbors.contains(v2)) {
			return false;
		}

		return true;
	}

	private static boolean validateString(String string) {
		return string != null && !string.isEmpty();
	}
}
