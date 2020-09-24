package com.ymiachyn.algorithms;

import static com.ymiachyn.Constants.HOR_VER_COST;
import static com.ymiachyn.Constants.NUM_COLS;
import static com.ymiachyn.Constants.NUM_ROWS;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStarSearch {
	private Node[][] searchSpace;
	private Node startNode, finalNode;
	//evaluated Nodes
	private List<Node> closedSet;
	//not evaluated Nodes
	private Queue<Node> openSet;
	
	public  AStarSearch() {
		this.searchSpace = new Node[NUM_ROWS][NUM_COLS];
		this.closedSet = new ArrayList<>();
		this.openSet = new PriorityQueue<>(new NodeComparator());
		initSearchSpace();
	}

	private void initSearchSpace() {
		for(int rowIdx = 0; rowIdx < NUM_ROWS; rowIdx++) {
			for(int colIdx = 0; colIdx < NUM_COLS; colIdx++) {
				Node node = new Node(rowIdx, colIdx);
				this.searchSpace[rowIdx][colIdx] = node;
			}
		}
		
		this.searchSpace[1][7].setBlocked();
		this.searchSpace[2][3].setBlocked();
		this.searchSpace[2][4].setBlocked();
		this.searchSpace[2][5].setBlocked();
		this.searchSpace[2][6].setBlocked();
		this.searchSpace[2][7].setBlocked();
		
		this.startNode = this.searchSpace[3][3];
		this.finalNode = this.searchSpace[1][6];
	}
	
	public void showPath() {
		System.out.println("\n\nShortest path with A*_Search:");
		Node node = this.finalNode;
		
		while(node != null) {
			System.out.println(node);
			node = node.getPredecessor();
		}
	}
	
	public int manhattanHeuristic(Node n1, Node n2) {
		return (Math.abs(n1.getRowIdx() - n2.getRowIdx()) +
				Math.abs(n1.getColIdx() - n2.getColIdx())) * 10;
	}
	
	public void search() {
		//initialize starting node h-value - distance to final node
		startNode.setH(manhattanHeuristic(startNode, finalNode));
		openSet.add(startNode);
		
		while(!openSet.isEmpty()) {
			//get Node with smallest F-value
			Node currentNode = openSet.peek();
			System.out.println(currentNode + " Predecessor is: " + (
					currentNode.getPredecessor() == null ? "None" : currentNode.getPredecessor()));
			
			//search is over!
			if(currentNode.equals(finalNode)) return;
			
			/* remove node from nodes to process and
			 * add to set of processed nodes
			 */
			openSet.remove(currentNode);
			closedSet.add(currentNode);
			
			//go over neighbors of the node
			getAllNeighbors(currentNode).forEach(neighbor -> {
				//if node already processed - continue to next one if any
				if(closedSet.contains(neighbor)) return;
				
				//add to queue of nodes to process if not already there
				if(!openSet.contains(neighbor)) openSet.add(neighbor);
				
				//set predecessor in order to track the shortest path
				neighbor.setPredecessor(currentNode);
			});
		}
	}
	
	private List<Node> getAllNeighbors(Node node){
		List<Node> neighbors = new ArrayList<>();
		int rowToProcess, colToProcess;
		Node nodeToProcess;
		
		int nodeRow = node.getRowIdx();
		int nodeCol = node.getColIdx();
		
		//node above
		rowToProcess = nodeRow - 1;
		colToProcess = nodeCol;
		if(rowToProcess >= 0 &&
				!(nodeToProcess = searchSpace[rowToProcess][colToProcess]).isBlocked()) {
			setNeighborData(node, nodeToProcess, neighbors);
		}
		
		//node below
		rowToProcess = nodeRow + 1;
		colToProcess = nodeCol;
		if(rowToProcess > NUM_ROWS &&
				!(nodeToProcess = searchSpace[rowToProcess][colToProcess]).isBlocked()) {
			setNeighborData(node, nodeToProcess, neighbors);
		}
		
		//node left
		rowToProcess = nodeRow;
		colToProcess = nodeCol - 1;
		if(colToProcess >= 0 &&
				!(nodeToProcess = searchSpace[rowToProcess][colToProcess]).isBlocked()) {
			setNeighborData(node, nodeToProcess, neighbors);
		}
		
		//node right
		rowToProcess = nodeRow;
		colToProcess = nodeCol + 1;
		if(colToProcess < NUM_COLS &&
				!(nodeToProcess = searchSpace[rowToProcess][colToProcess]).isBlocked()) {
			setNeighborData(node, nodeToProcess, neighbors);
		}
		
		return neighbors;
	}
	
	private void setNeighborData(Node node, Node nodeToProcess, List<Node> neighbors) {
		nodeToProcess.setH(manhattanHeuristic(nodeToProcess, finalNode));
		nodeToProcess.setG(node.getG() + HOR_VER_COST);
		neighbors.add(nodeToProcess);
	}
	

}
