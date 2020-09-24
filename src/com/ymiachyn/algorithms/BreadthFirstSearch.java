package com.ymiachyn.algorithms;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {
	
	public void traverse(Vertex root) {
		Queue<Vertex> nodes = new LinkedList<>();
		
		root.setVisited(true);
		nodes.add(root);
		
		while(!nodes.isEmpty()) {
			Vertex actualVertex = nodes.remove();
			System.out.println("Visited vertex: " + actualVertex);
			
			for(Vertex v : actualVertex.getAdjacentList()) {
				if(!v.isVisited()) {
					v.setVisited(true);
					nodes.add(v);
				}
			}
		}
	}

}
