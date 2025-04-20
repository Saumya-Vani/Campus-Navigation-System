package com.navigation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Graph {
	private Map<Integer, Node> nodes;
	private Map<Integer, List<Edge>> adjList;

	public Map<Integer, Node> getNodes() {
	    return nodes;
	}

	public Graph() {
		nodes = new HashMap<>();
		adjList = new HashMap<>();
	}

	public void loadNodesFromResource(String resourcePath) {
	    try (BufferedReader br = new BufferedReader(new FileReader(resourcePath))) {
	        String line = br.readLine(); // skip header
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

	public void loadEdgesFromResource(String resourcePath) {
	    try (BufferedReader br = new BufferedReader(new FileReader(resourcePath))) {
	        String line = br.readLine(); // skip header
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

	public void addNode(int id, String name) {
		if (!nodes.containsKey(id)) {
			nodes.put(id, new Node(id, name));
			adjList.put(id, new ArrayList<>());
		}
	}

	public void removeNode(int id) {
		if (!nodes.containsKey(id))
			return;
		nodes.remove(id);
		adjList.remove(id);
		for (List<Edge> edges : adjList.values()) {
			edges.removeIf(e -> e.toId == id);
		}
	}

	public void addEdge(int fromId, int toId, double weight, boolean oneWay, boolean accessible) {
		if (!nodes.containsKey(fromId) || !nodes.containsKey(toId))
			return;
		adjList.get(fromId).add(new Edge(fromId, toId, weight, accessible));
		if (!oneWay) {
			adjList.get(toId).add(new Edge(toId, fromId, weight, accessible));
		}
	}

	public void removeEdge(int fromId, int toId) {
		if (adjList.containsKey(fromId)) {
			adjList.get(fromId).removeIf(e -> e.toId == toId);
		}
		if (adjList.containsKey(toId)) {
			adjList.get(toId).removeIf(e -> e.toId == fromId);
		}
	}

	public void updatePath(int fromId, int toId, double dist, boolean accessible) {
		if (!adjList.containsKey(fromId))
			return;
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

	public List<Integer> shortestPath(int startId, int endId, boolean accessibleOnly) {
		Map<Integer, Double> distMap = new HashMap<>();
		Map<Integer, Integer> prev = new HashMap<>();
		for (int id : nodes.keySet()) {
			distMap.put(id, Double.POSITIVE_INFINITY);
		}
		distMap.put(startId, 0.0);

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

		PriorityQueue<NodeDist> pq = new PriorityQueue<>();
		pq.add(new NodeDist(startId, 0.0));

		while (!pq.isEmpty()) {
			NodeDist nd = pq.poll();
			if (nd.dist > distMap.get(nd.id))
				continue;
			if (nd.id == endId)
				break;
			for (Edge e : adjList.get(nd.id)) {
				if (accessibleOnly && !e.accessible)
					continue;
				double newDist = distMap.get(nd.id) + e.weight;
				if (newDist < distMap.get(e.toId)) {
					distMap.put(e.toId, newDist);
					prev.put(e.toId, nd.id);
					pq.add(new NodeDist(e.toId, newDist));
				}
			}
		}

		List<Integer> path = new ArrayList<>();
		if (!prev.containsKey(endId) && startId != endId)
			return path;
		for (Integer at = endId; at != null; at = prev.get(at)) {
			path.add(at);
			if (at == startId)
				break;
		}
		Collections.reverse(path);
		return path;
	}

	public void listBuildings() {
		for (Node node : nodes.values()) {
			System.out.printf("%d: %s%n", node.id, node.name);
		}
	}

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