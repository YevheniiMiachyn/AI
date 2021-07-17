package com.ymiachyn.algorithms.alphabeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {

	private List<Cell> emptyCells;
    private Scanner scanner;
    private Player[][] board;
    private List<Cell> rootValues;

    public Board() {
    	initializeBoard();
    }

    private void initializeBoard() {
    	this.rootValues = new ArrayList<>();
		this.scanner = new Scanner(System.in);
		this.board = new Player[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
	}

	public boolean isRunning() {
		
		if(isWinning(Player.COMPUTER) || isWinning(Player.USER) || getEmptyCells().isEmpty())
		{
			return false;
		}
		
		return true;
    }

	/**
	 * Check if given player covered a line
	 * 
	 * @param player
	 * @return
	 */
    public boolean isWinning(Player player) {
    	
    	//checking diagonals
        if ( board[0][0] == player && board[1][1] == player && board[2][2] == player ) {
            return true;
        }
        
        if( board[0][2] == player && board[1][1] == player && board[2][0] == player ){
        	return true;
        }
        
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
           
        	// checking the rows
        	if ( board[i][0] == player && board[i][1] == player && board[i][2] == player ) {
                return true;
            }
        	
        	// checking the columns
        	if( board[0][i] == player && board[1][i] == player && board[2][i] == player ){
        		return true;
        	}
        }
        
        return false;
    }

    public List<Cell> getEmptyCells() {
        
    	emptyCells = new ArrayList<>();
        
        for (int i = 0; i < Constants.BOARD_SIZE; ++i) {
            for (int j = 0; j < Constants.BOARD_SIZE; ++j) {
                if (board[i][j] == Player.NONE) {
                    emptyCells.add(new Cell(i, j));
                }
            }
        }
        return emptyCells;
    }

    /**
     * Assign player to specific cell
     * @param point
     * @param player
     */
    public void move(Cell point, Player player) {
        board[point.getX()][point.getY()] = player;   
    }

    public Cell getBestMove() {
    	
        int max = Integer.MIN_VALUE;
        int best = Integer.MIN_VALUE;

        for (int i = 0; i < rootValues.size(); ++i) { 
            if (max < rootValues.get(i).getMinimaxValue()) {
                max = rootValues.get(i).getMinimaxValue();
                best = i;
            }
        }

        return rootValues.get(best);
    }

    public void makeUserInput() {
        System.out.println("User's move: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        Cell point = new Cell(x, y);
        move(point, Player.USER); 
    }

    public void displayBoard() {

    	System.out.println();
    	
        for (int i = 0; i < Constants.BOARD_SIZE; ++i) {
            for (int j = 0; j < Constants.BOARD_SIZE; ++j) {
                System.out.print(board[i][j] + " ");
            }
            
            System.out.println();
        }
    }

    public int returnMin(List<Integer> list) {
    	
        int min = Integer.MAX_VALUE;
        int index = Integer.MIN_VALUE;
        
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        
        return list.get(index);
    }
 
    /**
     * 
     * @param depth - how many layers in game tree (starts with root 0 )
     * @param player
     */
    public void callMinimax(int depth, Player player){
        rootValues.clear();
        minimax(depth, player);
    }
    
    /**
     * Min-max algorithm - calling it recursively
     * 
     * @param depth
     * @param player
     * @return
     */
    public int minimax(int depth, Player player) {

    	/**
    	 * checking leaf nodes when no more moves
    	 * and can determine the outcome
    	 */
        if (isWinning(Player.COMPUTER)) return +1;
        if (isWinning(Player.USER)) return -1;

        List<Cell> availableCells = getEmptyCells();
        
        //it's a draw
        if (availableCells.isEmpty()) return 0; 

        List<Integer> scores = new ArrayList<>(); 

        for (int i = 0; i < availableCells.size(); i++) {
            
        	Cell point = availableCells.get(i);  

        	/**
        	 * These return values for internal nodes in game tree
        	 * Computer turn select the highest from below minimax() call
        	 * Looking for max value if computer because it's from computer POV
        	 */
            if (player == Player.COMPUTER) 
            { 
                move(point, Player.COMPUTER); 
                //depth first algorithm
                int currentScore = minimax(depth + 1, Player.USER);
                scores.add(currentScore);

                /**
                 * +1 computer move can win
                 * -1 computer move can lose - avoid this move
                 */
                if (depth == 0) {
                	point.setMinimaxValue(currentScore);
                    rootValues.add(point);
                }    	
              /**
               * O's turn select the lowest from below minimax() call  
               * Looking for min if user because it's for user from computer POV
               */
            } else if (player == Player.USER)
            {
                move(point, Player.USER); 
                //minmax algorithm run only for computer
                scores.add(minimax(depth + 1, Player.COMPUTER));
            }
            
            board[point.getX()][point.getY()] = Player.NONE; //Reset this point
        }
        
        if( player == Player.COMPUTER ){
        	return returnMax(scores);
        }
        
        return returnMin(scores);
    }

	public List<Cell> getAvailablePoints() {
		return emptyCells;
	}

	public void setAvailablePoints(List<Cell> availablePoints) {
		this.emptyCells = availablePoints;
	}
	
	public void setupBoard() {
		for(int i=0;i<Constants.BOARD_SIZE;i++){
			for(int j=0;j<Constants.BOARD_SIZE;j++){
				board[i][j] = Player.NONE;
			}
		}
	}
    
    //getters
	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public Player[][] getBoard() {
		return board;
	}

	public void setBoard(Player[][] board) {
		this.board = board;
	}
	
	public List<Cell> getRootValues(){
		return this.rootValues;
	}
}
