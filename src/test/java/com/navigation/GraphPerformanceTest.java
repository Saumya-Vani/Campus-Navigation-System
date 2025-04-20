package com.navigation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

public class GraphPerformanceTest {

	private static Graph graph10k;
	private static Graph graph100k;

	private static final String FILE_PATH_NODES_10K = "src/test/resources/buildings_10000.csv";
	private static final String FILE_PATH_EDGES_10K = "src/test/resources/paths_10000.csv";

	private static final String FILE_PATH_NODES_100K = "src/test/resources/buildings_100000.csv";
	private static final String FILE_PATH_EDGES_100K = "src/test/resources/paths_100000.csv";

	@BeforeAll
	public static void setup() {
		graph10k = new Graph();
		graph10k.loadNodesFromResource(FILE_PATH_NODES_10K);
		graph10k.loadEdgesFromResource(FILE_PATH_EDGES_10K);
		System.out.println("10K Graph Loaded: " + graph10k.getNodes().size() + " nodes");

		graph100k = new Graph();
		graph100k.loadNodesFromResource(FILE_PATH_NODES_100K);
		graph100k.loadEdgesFromResource(FILE_PATH_EDGES_100K);
		System.out.println("100K Graph Loaded: " + graph100k.getNodes().size() + " nodes");
	}

	private void logExecutionTime(String label, Runnable task) {
		long start = System.nanoTime();
		task.run();
		long end = System.nanoTime();
		double timeMs = (end - start) / 1e6;
		System.out.println("[" + label + "] executed in " + String.format("%.2f", timeMs) + " ms");
	}

	private void runShortestPathTest(Graph graph, int fromId, int toId, boolean accessible, Duration timeout,
			String label) {
		assertTimeout(timeout, () -> {
			long start = System.nanoTime();
			List<Integer> path = graph.shortestPath(fromId, toId, accessible);
			long end = System.nanoTime();
			double timeMs = (end - start) / 1e6;

			System.out.println("[" + label + "] from=" + fromId + ", to=" + toId + ", accessible=" + accessible
					+ ", time=" + String.format("%.2f", timeMs) + " ms");

			if (path == null || path.isEmpty()) {
				System.err.println("No path found between " + fromId + " → " + toId);
			}

			assertNotNull(path, "Path should not be null");
			assertFalse(path.isEmpty(), "Path should not be empty");
		});
	}

	@Test
	public void testShortestPath10K() {
		System.out.println("\n=== BEGIN: 10K Shortest Path Tests ===");

		runShortestPathTest(graph10k, 9, 8888, false, Duration.ofSeconds(10), "10K Shortest Path (non-accessible)");
		runShortestPathTest(graph10k, 9, 8888, true, Duration.ofSeconds(10), "10K Shortest Path (accessible)");

		System.out.println("=== END: 10K Shortest Path Tests ===\n");
	}

	@Test
	public void testShortestPath100K() {
		System.out.println("\n=== BEGIN: 100K Shortest Path Tests ===");

		runShortestPathTest(graph100k, 40740, 79881, false, Duration.ofSeconds(30),
				"100K Shortest Path (non-accessible)");
		runShortestPathTest(graph100k, 40740, 79881, true, Duration.ofSeconds(30), "100K Shortest Path (accessible)");

		System.out.println("=== END: 100K Shortest Path Tests ===\n");
	}

	@Test
	public void testMultipleRandomPaths10K() {
		System.out.println("\n=== BEGIN: 10K Random Path Tests ===");

		int size = graph10k.getNodes().size();
		for (int i = 0; i < 5; i++) {
			int from = 1 + (int) (Math.random() * size);
			int to = 1 + (int) (Math.random() * size);
			runShortestPathTest(graph10k, from, to, false, Duration.ofSeconds(5), "10K Random Pair " + i);
		}

		System.out.println("=== END: 10K Random Path Tests ===\n");
	}

	@Test
	public void testMultipleRandomPaths100K() {
		System.out.println("\n=== BEGIN: 100K Random Path Tests ===");

		int size = graph100k.getNodes().size();
		for (int i = 0; i < 5; i++) {
			int from = 1 + (int) (Math.random() * size);
			int to = 1 + (int) (Math.random() * size);
			runShortestPathTest(graph100k, from, to, false, Duration.ofSeconds(10), "100K Random Pair " + i);
		}

		System.out.println("=== END: 100K Random Path Tests ===\n");
	}

	@Test
	public void testEdgeInsertion10K() {
		System.out.println("\n=== BEGIN: 10K Edge Insertion ===");

		assertTimeout(Duration.ofSeconds(10), () -> {
			logExecutionTime("10K Edge Insertion", () -> {
				for (int i = 1; i <= 1000; i++) {
					graph10k.addEdge(i, (i % graph10k.getNodes().size()) + 1, 100 + i, false, true);
				}
			});
		});

		System.out.println("=== END: 10K Edge Insertion ===\n");
	}

	@Test
	public void testEdgeInsertion100K() {
		System.out.println("\n=== BEGIN: 100K Edge Insertion ===");

		assertTimeout(Duration.ofSeconds(10), () -> {
			logExecutionTime("100K Edge Insertion", () -> {
				for (int i = 1; i <= 1000; i++) {
					graph100k.addEdge(i, (i % graph100k.getNodes().size()) + 1, 100 + i, false, true);
				}
			});
		});

		System.out.println("=== END: 100K Edge Insertion ===\n");
	}

	@Test
	public void testEdgeRemoval10K() {
		System.out.println("\n=== BEGIN: 10K Edge Removal ===");

		assertTimeout(Duration.ofSeconds(2), () -> {
			logExecutionTime("10K Edge Removal", () -> {
				graph10k.removeEdge(1, 2); // adjust if needed
			});
		});

		System.out.println("=== END: 10K Edge Removal ===\n");
	}

	@Test
	public void testEdgeRemoval100K() {
		System.out.println("\n=== BEGIN: 100K Edge Removal ===");

		assertTimeout(Duration.ofSeconds(2), () -> {
			logExecutionTime("100K Edge Removal", () -> {
				graph100k.removeEdge(1, 2); // adjust if needed
			});
		});

		System.out.println("=== END: 100K Edge Removal ===\n");
	}
}