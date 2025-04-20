package com.navigation;

import java.util.List;

class NavigationService {
    private Graph graph;

    public NavigationService(String nodesResource, String edgesResource) {
        graph = new Graph();
        graph.loadNodesFromResource(nodesResource);    
        graph.loadEdgesFromResource(edgesResource);
    }

    public void listBuildings() {
        graph.listBuildings();
    }

    public void navigate(int fromId, int toId, boolean accessibleOnly) {
        List<Integer> path = graph.shortestPath(fromId, toId, accessibleOnly);
        graph.printPath(path);
    }

    public void addBuilding(int id, String name) {
        graph.addNode(id, name);
        System.out.println("Building added: " + id + ": " + name);
    }

    public void removeBuilding(int id) {
        graph.removeNode(id);
        System.out.println("Building removed: " + id);
    }

    public void addPath(int fromId, int toId, double dist, boolean oneWay, boolean accessible) {
        graph.addEdge(fromId, toId, dist, oneWay, accessible);
        System.out.println("Path added from " + fromId + " to " + toId);
    }

    public void removePath(int fromId, int toId) {
        graph.removeEdge(fromId, toId);
        System.out.println("Path removed from " + fromId + " to " + toId);
    }

    public void updatePath(int fromId, int toId, double dist, boolean accessible) {
        graph.updatePath(fromId, toId, dist, accessible);
        System.out.println("Path updated from " + fromId + " to " + toId);
    }
}