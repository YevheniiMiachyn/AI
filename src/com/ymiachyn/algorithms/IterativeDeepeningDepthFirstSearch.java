package com.ymiachyn.algorithms;

import java.util.Stack;

public class IterativeDeepeningDepthFirstSearch {
	private Vertex targetVertex;
	private volatile boolean targetFound;
	
	public IterativeDeepeningDepthFirstSearch(Vertex targetVortex) {
		this.targetVertex = targetVortex;
	}
	
	public void iddfs(Vertex root) {
		int depth = 0;
		
		while (!isTargetFound()) {
			System.out.println();
			//iteration always starts with root node for every 
			//level iteration
			dfs(root, depth);
			//set next depth level allowed to go
			depth++;
			
		}
	}
	
	private void dfs(Vertex srcVtx, int depth) {
		Stack<Vertex> stack = new Stack<>();
		stack.push(srcVtx);
		
		while(!stack.isEmpty()) {
			Vertex poppedVertex = stack.pop();
			System.out.print(poppedVertex + " ");
			
			//once found - stop searching
			if(poppedVertex.equals(targetVertex)) {
				System.out.println("\nTarget " + targetVertex + " found at depth " + ++depth);
				this.targetFound = true;
				return;
			}
			
			//prevent to go any deeper into the three other than 
			//current depth
			if(poppedVertex.getDepthLevel() >= depth) {
				continue;
			}
			
			poppedVertex.getAdjacentList().forEach(adjacentVertex -> {
				//set depth level for next layer of adjacent nodes
				//and add these to top of the stack
				adjacentVertex.setDepthLevel(poppedVertex.getDepthLevel()+1);
				stack.push(adjacentVertex);
			});
		}
	}

	public boolean isTargetFound() {
		return targetFound;
	}

	public void setTargetFound(boolean targetFound) {
		this.targetFound = targetFound;
	}
}
