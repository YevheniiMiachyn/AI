package com.ymiachyn.algorithms;

import java.util.List;
import java.util.Stack;

public class DepthFirstSearch {
	private Stack<Vertex> stack;
	
	public DepthFirstSearch() {
		this.stack = new Stack<>();
	}
	
	public void bfs(List<Vertex> vertexes) {
		vertexes.stream().forEach(v -> {
			if(!v.isVisited()) {
				v.setVisited(true);
				//dfsWithStack(v);
				dfsRecursive(v);
			}
		});
	}
	
	private void dfsRecursive(Vertex v) {
		System.out.println(v);
		
		v.getAdjacentList().forEach(t -> {
			if(!t.isVisited()) {
				t.setVisited(true);
				dfsRecursive(t);
			}
		});
	}

	private void dfsWithStack(Vertex v) {
		this.stack.add(v); //add to the end
		while(!this.stack.isEmpty()) {
			Vertex vertex = this.stack.pop();
			System.out.println(vertex);
			
			vertex.getAdjacentList().forEach(t -> {
				if(!t.isVisited()) {
					t.setVisited(true);
					this.stack.push(t); //add on top of stack
				}
			});
		}
	}
}
