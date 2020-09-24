package com.ymiachyn.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	private String name;
	private boolean visited;
	private int depthLevel = 0;
	private List<Vertex> adjacentList;
	
	public Vertex(String name) {
		this.name = name;
		this.adjacentList = new ArrayList<>();
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public List<Vertex> getAdjacentList() {
		return adjacentList;
	}

	public void addNeighbor(Vertex neighbor) {
		this.adjacentList.add(neighbor);
	}

	public int getDepthLevel() {
		return depthLevel;
	}

	public void setDepthLevel(int depthLevel) {
		this.depthLevel = depthLevel;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Vertex [name=" + name + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}

	
}
