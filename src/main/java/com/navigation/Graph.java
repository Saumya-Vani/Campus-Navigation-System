package com.navigation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Graph {

	// Stores all nodes in the graph keyed by their ID
	private Map<Integer, Node> nodes;

	// Adjacency list where each node ID maps to a list of outgoing edges
	private Map<Integer, List<Edge>> adjList;

	// Public getter to expose nodes (used for testing and performance analysis)
	public Map<Integer, Node> getNodes() {
	    return nodes;
	}

	// Constructor initializes the graph structures
	public Graph() {
		nodes = new HashMap<>();
		adjList = new HashMap<>();
	}

	/**
	 * Loads nodes from a CSV file and adds them to the graph.
	 * Expected CSV format: id,name
	 */
	public void loadNodesFromResource(String resourcePath) {
	    try (BufferedReader br = new BufferedReader(new FileReader(resourcePath))) {
	        String line = br.readLine(); // Skip header line
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            int id = Integer.parseInt(parts[0].trim());
	            String name = parts[1].trim();
	            addNode(id, name);
	        }
	    } catch (IOException e) {
	        System.err.println("Failed to load nodes file: " + e.getMessage());
	    }
	}

	/**
	 * Loads edges from a CSV file and adds them to the graph.
	 * Expected CSV format: fromId,toId,distance,oneWay,accessible
	 */
	public void loadEdgesFromResource(String resourcePath) {
	    try (BufferedReader br = new BufferedReader(new FileReader(resourcePath))) {
	        String line = br.readLine(); // Skip header line
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            int fromId = Integer.parseInt(parts[0].trim());
	            int toId = Integer.parseInt(parts[1].trim());
	            double dist = Double.parseDouble(parts[2].trim());
	            boolean oneWay = Boolean.parseBoolean(parts[3].trim());
	            boolean accessible = Boolean.parseBoolean(parts[4].trim());
	            addEdge(fromId, toId, dist, oneWay, accessible);
	        }
	    } catch (IOException e) {
	        System.err.println("Failed to load edges file: " + e.getMessage());
	    }
	}

	// Adds a node to the graph if it doesn't already exist
	public void addNode(int id, String name) {
		if (!nodes.containsKey(id)) {
			nodes.put(id, new Node(id, name));
			adjList.put(id, new ArrayList<>());
		}
	}

	// Removes a node and all edges pointing to it
	public void removeNode(int id) {
		if (!nodes.containsKey(id)) return;

		nodes.remove(id);
		adjList.remove(id);

		// Remove all incoming edges pointing to this node
		for (List<Edge> edges : adjList.values()) {
			edges.removeIf(e -> e.toId == id);
		}
	}

	/**
	 * Adds an edge from fromId to toId with optional one-way and accessibility flag.
	 * If not one-way, it adds a reverse edge as well.
	 */
	public void addEdge(int fromId, int toId, double weight, boolean oneWay, boolean accessible) {
		if (!nodes.containsKey(fromId) || !nodes.containsKey(toId))
			return;

		adjList.get(fromId).add(new Edge(fromId, toId, weight, accessible));

		if (!oneWay) {
			adjList.get(toId).add(new Edge(toId, fromId, weight, accessible));
		}
	}

	// Removes an edge between two nodes in both directions
	public void removeEdge(int fromId, int toId) {
		if (adjList.containsKey(fromId)) {
			adjList.get(fromId).removeIf(e -> e.toId == toId);
		}
		if (adjList.containsKey(toId)) {
			adjList.get(toId).removeIf(e -> e.toId == fromId);
		}
	}

	// Updates the weight and accessibility of an edge (assumes undirected graph)
	public void updatePath(int fromId, int toId, double dist, boolean accessible) {
		if (!adjList.containsKey(fromId)) return;

		for (Edge e : adjList.get(fromId)) {
			if (e.toId == toId) {
				e.weight = dist;
				e.accessible = accessible;
			}
		}
		for (Edge e : adjList.get(toId)) {
			if (e.toId == fromId) {
				e.weight = dist;
				e.accessible = accessible;
			}
		}
	}

	/**
	 * Implements Dijkstra's algorithm to find the shortest path.
	 * @param startId Starting building ID
	 * @param endId   Destination building ID
	 * @param accessibleOnly Whether to use only accessible paths
	 * @return List of building IDs in the shortest path
	 */
	public List<Integer> shortestPath(int startId, int endId, boolean accessibleOnly) {
		Map<Integer, Double> distMap = new HashMap<>();
		Map<Integer, Integer> prev = new HashMap<>();

		// Initialize distances to infinity
		for (int id : nodes.keySet()) {
			distMap.put(id, Double.POSITIVE_INFINITY);
		}
		distMap.put(startId, 0.0);  // Distance to self is 0

		// Helper class for priority queue
		class NodeDist implements Comparable<NodeDist> {
			int id;
			double dist;

			NodeDist(int id, double dist) {
				this.id = id;
				this.dist = dist;
			}

			public int compareTo(NodeDist other) {
				return Double.compare(this.dist, other.dist);
			}
		}

		// Priority queue for Dijkstra's algorithm
		PriorityQueue<NodeDist> pq = new PriorityQueue<>();
		pq.add(new NodeDist(startId, 0.0));

		while (!pq.isEmpty()) {
			NodeDist nd = pq.poll();

			// Skip outdated entries
			if (nd.dist > distMap.get(nd.id)) continue;

			// Early exit if destination is reached
			if (nd.id == endId) break;

			for (Edge e : adjList.get(nd.id)) {
				if (accessibleOnly && !e.accessible) continue;

				double newDist = distMap.get(nd.id) + e.weight;

				if (newDist < distMap.get(e.toId)) {
					distMap.put(e.toId, newDist);
					prev.put(e.toId, nd.id);
					pq.add(new NodeDist(e.toId, newDist));
				}
			}
		}

		// Reconstruct path from endId to startId using predecessor map
		List<Integer> path = new ArrayList<>();
		if (!prev.containsKey(endId) && startId != endId) return path;

		for (Integer at = endId; at != null; at = prev.get(at)) {
			path.add(at);
			if (at == startId) break;
		}

		Collections.reverse(path);  // Convert from end→start to start→end
		return path;
	}

	// Prints all nodes in the graph (used in CLI)
	public void listBuildings() {
		for (Node node : nodes.values()) {
			System.out.printf("%d: %s%n", node.id, node.name);
		}
	}

	// Displays the building names along the path (user-friendly)
	public void printPath(List<Integer> path) {
		if (path == null || path.isEmpty()) {
			System.out.println("No path found.");
			return;
		}
		System.out.println("Route:");
		for (int i = 0; i < path.size() - 1; i++) {
			Node from = nodes.get(path.get(i));
			Node to = nodes.get(path.get(i + 1));
			System.out.printf("%s -> %s%n", from.name, to.name);
		}
	}
}
