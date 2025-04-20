package com.navigation;

public class Edge {

	int fromId;
    int toId;
    double weight;
    boolean accessible;

    public Edge(int fromId, int toId, double weight, boolean accessible) {
        this.fromId = fromId;
        this.toId = toId;
        this.weight = weight;
        this.accessible = accessible;
    }

	/**
	 * @return the fromId
	 */
	public int getFromId() {
		return fromId;
	}

	/**
	 * @param fromId the fromId to set
	 */
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	/**
	 * @return the toId
	 */
	public int getToId() {
		return toId;
	}

	/**
	 * @param toId the toId to set
	 */
	public void setToId(int toId) {
		this.toId = toId;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the accessible
	 */
	public boolean isAccessible() {
		return accessible;
	}

	/**
	 * @param accessible the accessible to set
	 */
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	@Override
	public String toString() {
		return "Edge [fromId=" + fromId + ", toId=" + toId + ", weight=" + weight + ", accessible=" + accessible + "]";
	}
}
