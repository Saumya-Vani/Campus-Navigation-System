package com.navigation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class PerformanceTest {

	@Test
    public void test10kDatasetPerformance() {
        runPerformanceTest("src/test/resources/buildings_10000.csv", 
                           "src/test/resources/paths_10000.csv", 
                           10000);
    }

    @Test
    public void test100kDatasetPerformance() {
        runPerformanceTest("src/test/resources/buildings_100000.csv", 
                           "src/test/resources/paths_100000.csv", 
                           100000);
    }

    private void runPerformanceTest(String nodeFile, String edgeFile, int toId) {
        Graph graph = new Graph();

        long startTime = System.nanoTime();
        graph.loadNodesFromResource(nodeFile);
        long nodeLoadTime = System.nanoTime();

        graph.loadEdgesFromResource(edgeFile);
        long edgeLoadTime = System.nanoTime();

        int fromId = 1;

        List<Integer> path = graph.shortestPath(fromId, toId, false);
        long pathTime = System.nanoTime();

        // Logs
        System.out.println("\n=== Performance Test: " + nodeFile + " ===");
        System.out.printf("Node load time: %.2f ms\n", (nodeLoadTime - startTime) / 1e6);
        System.out.printf("Edge load time: %.2f ms\n", (edgeLoadTime - nodeLoadTime) / 1e6);
        System.out.printf("Pathfinding time: %.2f ms\n", (pathTime - edgeLoadTime) / 1e6);

        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");
    }
}
