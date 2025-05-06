package com.navigation;

/**
 * The Edge class represents a directional connection between two buildings (nodes)
 * in a campus navigation graph. Each edge has a weight (distance) and a flag
 * indicating if it is accessible (e.g., for wheelchair access).
 */
public class Edge {

    // Source node ID
	int fromId;

    // Destination node ID
    int toId;

    // Distance or cost associated with the edge
    double weight;

    // Indicates whether the edge is accessible (e.g., wheelchair-friendly)
    boolean accessible;

    /**
     * Constructor to initialize all attributes of an edge.
     *
     * @param fromId     Source building ID
     * @param toId       Destination building ID
     * @param weight     Distance or cost of travel between buildings
     * @param accessible Whether the path is accessible
     */
    public Edge(int fromId, int toId, double weight, boolean accessible) {
        this.fromId = fromId;
        this.toId = toId;
        this.weight = weight;
        this.accessible = accessible;
    }

    // Getter for the source node ID
	public int getFromId() {
		return fromId;
	}

    // Setter for the source node ID
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

    // Getter for the destination node ID
	public int getToId() {
		return toId;
	}

    // Setter for the destination node ID
	public void setToId(int toId) {
		this.toId = toId;
	}

    // Getter for the edge weight (e.g., distance in meters)
	public double getWeight() {
		return weight;
	}

    // Setter for the edge weight
	public void setWeight(double weight) {
		this.weight = weight;
	}

    // Returns true if the edge is accessible
	public boolean isAccessible() {
		return accessible;
	}

    // Setter to define whether the edge is accessible
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

    /**
     * Returns a string representation of the edge.
     */
	@Override
	public String toString() {
		return "Edge [fromId=" + fromId + ", toId=" + toId + ", weight=" + weight + ", accessible=" + accessible + "]";
	}
}
