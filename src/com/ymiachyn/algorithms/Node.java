package com.ymiachyn.algorithms;

public class Node {
	
	private int g, h, rowIdx, colIdx;
	private Node predecessor;
	private boolean blocked = false;
	
	public Node(int rowIndex, int colIndex) {
		this.rowIdx = rowIndex;
		this.colIdx = colIndex;
	}
	
	/**
	 * Get F-value that is sum of g and h
	 * @return
	 */
	public int getF() {
		return this.g + this.h;
	}

	/**
	 * Get how far away this node is
	 * from starting point
	 * @return
	 */
	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	/**
	 * How far away this node is from the
	 * final node (heuristic)
	 * @return
	 */
	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getRowIdx() {
		return rowIdx;
	}

	public void setRowIdx(int rowIndex) {
		this.rowIdx = rowIndex;
	}

	public int getColIdx() {
		return colIdx;
	}

	public void setColIdx(int colIndex) {
		this.colIdx = colIndex;
	}

	public Node getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked() {
		this.blocked = true;
	}

	@Override
	public String toString() {
		return "Node (" + rowIdx +"," + colIdx + ") " + "[g=" + g + ", h=" + h + ", F=" + getF() + "]";
	}
	
	@Override
	public boolean equals(Object node2) {
		Node otherNode = (Node) node2;
		return this.rowIdx == otherNode.getRowIdx() && 
				this.colIdx == otherNode.getColIdx();
	}

}
