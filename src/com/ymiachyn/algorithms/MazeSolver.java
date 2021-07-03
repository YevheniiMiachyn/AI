package com.ymiachyn.algorithms;

public class MazeSolver {
	private int startRow, startCol;
	private int[][] maze;
	private boolean[][] vizited;
	
	public MazeSolver(int[][] maze, int startRow, int startCol) {
		this.maze = maze;
		this.startRow = startRow;
		this.startCol = startCol;
		this.vizited = new boolean[maze.length][maze.length];
	}

	public void findWay() {
		if(findWayWithDFS(startRow, startCol))
			System.out.println("There is an exit!");
		else
			System.out.println("There is NO exit!");
		
	}
	
	private boolean findWayWithDFS(int x, int y) {
				
		if(x == maze.length-1 && y == maze.length-1)
			return true;
		
		if(isValidMove(x, y))
		{
			vizited[x][y] = true;
			
			//up
			if(findWayWithDFS(x+1, y)) {
				return true;
			}
			//down
			if(findWayWithDFS(x-1, y)) {
				return true;
			}
			//right
			if(findWayWithDFS(x, y+1)) {
				return true;
			}
			//left
			if(findWayWithDFS(x, y-1)) {
				return true;
			}
			
			//backtrack
			vizited[x][y] = false;
			
			return false;
		}
			
		return false;
	}
	
	private boolean isValidMove(int x, int y) {
		
		if(x < 0 || x > this.maze.length-1)
			return false;
		
		if(y < 0 || y > this.maze.length-1)
			return false;
		
		if(this.vizited[x][y])
			return false;
		
		if(this.maze[x][y] == 1)
			return false;
					
		return true;
	}
	
}
