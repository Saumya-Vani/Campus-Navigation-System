# Campus Navigation System

## Project Overview
The **Campus Navigation System** is a CLI-based application that models a university campus as a graph to compute the shortest paths between buildings. It supports weighted, directed edges (including one-way routes), accessibility constraints, and dynamic updates. The system includes performance benchmarking on large datasets (10 000 and 100 000 nodes) and offers a simple command-line interface for interactive use.

## Key Features
- **Shortest Path Computation**: Find the shortest route between any two buildings using Dijkstra’s algorithm.  
- **Accessible Path Support**: Filter routes to include only accessible paths.  
- **One-Way Path Modeling**: Support directed edges to represent one-way walkways or roads.  
- **Dynamic Graph Updates**: Insert and remove edges at runtime.  
- **Custom Data Structures**: Graph, Node, and Edge implementations built from scratch (no Java built-in collections).  
- **CSV Dataset Loader**: Load building and path data from CSV files.  
- **Performance Benchmarking**: Measure time for pathfinding, edge insertion, and edge removal.  
- **Randomized Testing**: Run multiple random path queries for stress testing.  
- **JUnit 5 Test Coverage**: Automated tests with timeouts and console logging.  
- **Optional Graphing**: Python script to visualize performance metrics.

## Project Structure
```
CampusNavigation
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── navigation/
│   │   │           ├── CampusNavigation.java 
│   │   │           ├── Edge.java 
│   │   │           ├── Graph.java
│   │   │           ├── NavigationService.java
│   │   │           └── Node.java
│   │   └── resources/
│   │       ├── datasetgenerator.py
│   │       ├── buildings.csv
│   │       └── paths.csv
│   ├── test/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── navigation/
│   │   │           └── GraphPerformanceTest.java
│   │   └── resources/
│   │       ├── buildings_100000.csv
│   │       ├── paths_100000.csv
│   │       ├── buildings_10000.csv
│   │       └── paths_10000.csv
├── target/
├── pom.xml
```

## CLI Provided

1. **List Buildings**  
   - Display all buildings with their IDs.  
2. **Find Shortest Path**  
   - Compute and display the shortest route between two building IDs.  
3. **Add Building**  
   - Insert a new building node into the graph.
4. **Remove Building**  
   - Delete a building and all its associated paths.   
5. **Add Path**  
   - Create a new edge between two buildings with specified weight and flags.
6. **Remove Path**  
   - Delete an existing edge between two buildings.
7. **Update Path**
    – Modify the distance or accessibility of an existing path.
8. **Exit**
     – Terminate the application.
   
## Usage

```bash
=== Campus Navigation System ===
1. List Buildings
2. Find Shortest Path
3. Add Building
4. Remove Building
5. Add Path
6. Remove Path
7. Update Path
8. Exit
Choose an option: 
```

