package com.navigation;

import java.util.List;

/**
 * NavigationService acts as a controller between the CLI and the Graph.
 * It wraps Graph operations and exposes clean methods for building/path management and navigation.
 */
class NavigationService {

    // Underlying graph that models the campus layout
    private Graph graph;

    /**
     * Constructor initializes the graph using node and edge CSV resources.
     *
     * @param nodesResource Path to the buildings CSV
     * @param edgesResource Path to the paths CSV
     */
    public NavigationService(String nodesResource, String edgesResource) {
        graph = new Graph();
        graph.loadNodesFromResource(nodesResource);
        graph.loadEdgesFromResource(edgesResource);
    }

    /**
     * Lists all buildings by delegating to the graph.
     * Useful for displaying available nodes to users.
     */
    public void listBuildings() {
        graph.listBuildings();
    }

    /**
     * Finds and prints the shortest path between two buildings.
     *
     * @param fromId         Source building ID
     * @param toId           Destination building ID
     * @param accessibleOnly Whether to restrict search to accessible paths only
     */
    public void navigate(int fromId, int toId, boolean accessibleOnly) {
        List<Integer> path = graph.shortestPath(fromId, toId, accessibleOnly);
        graph.printPath(path);
    }

    /**
     * Adds a new building (node) to the campus graph.
     *
     * @param id   Unique building ID
     * @param name Human-readable name of the building
     */
    public void addBuilding(int id, String name) {
        graph.addNode(id, name);
        System.out.println("Building added: " + id + ": " + name);
    }

    /**
     * Removes an existing building (node) from the campus graph.
     *
     * @param id ID of the building to be removed
     */
    public void removeBuilding(int id) {
        graph.removeNode(id);
        System.out.println("Building removed: " + id);
    }

    /**
     * Adds a new path (edge) between two buildings.
     *
     * @param fromId     Source building ID
     * @param toId       Destination building ID
     * @param dist       Distance or cost between buildings
     * @param oneWay     Whether the path is one-directional
     * @param accessible Whether the path is accessible
     */
    public void addPath(int fromId, int toId, double dist, boolean oneWay, boolean accessible) {
        graph.addEdge(fromId, toId, dist, oneWay, accessible);
        System.out.println("Path added from " + fromId + " to " + toId);
    }

    /**
     * Removes a path (edge) between two buildings.
     *
     * @param fromId Source building ID
     * @param toId   Destination building ID
     */
    public void removePath(int fromId, int toId) {
        graph.removeEdge(fromId, toId);
        System.out.println("Path removed from " + fromId + " to " + toId);
    }

    /**
     * Updates the distance and accessibility of an existing path.
     *
     * @param fromId     Source building ID
     * @param toId       Destination building ID
     * @param dist       New distance
     * @param accessible New accessibility flag
     */
    public void updatePath(int fromId, int toId, double dist, boolean accessible) {
        graph.updatePath(fromId, toId, dist, accessible);
        System.out.println("Path updated from " + fromId + " to " + toId);
    }
}
