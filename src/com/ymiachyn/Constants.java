package com.ymiachyn;

public class Constants {
	
	/**
	 * Graph search constants
	 */
	
	public static final int NUM_ROWS = 5;
	public static final int NUM_COLS = 8;
	public static final int HOR_VER_COST = 10;
	public static final int DIAG_COST = 14;
	
	/**
	 * Tabu search constants
	 */
	
	//size of tabu list or queue
	public static final int TABU_TENURE = 400;
	//for -10 to 10 interval with 0.1 step
	public static final int NUM_VALUES = 200;
	//number of iterations in the search
	public static final int NUM_ITERATIONS = 100000;
	
	/**
	 * Simulated Annealing constants
	 */
	
	public static final double MIN_COORDINATE = -2;
	public static final double MAX_COORDINATE = 2;
	public static final double MIN_TEMPERATURE = 1;
	public static final double MAX_TEMPERATURE = 100;
	public static final double COOLING_RATE = 0.02;
	
}
