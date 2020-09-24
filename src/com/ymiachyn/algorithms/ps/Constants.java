package com.ymiachyn.algorithms.ps;

public class Constants {

	private Constants(){
		
	}
	
	public final static int NUM_DIMENSIONS = 2; 
	public final static int NUM_PARTICALS = 10;
	public final static int MAX_ITERATIONS = 10000;
	public final static double MIN = -2.2; 
	public final static double MAX = 2.2;
	public final static double w = 0.729; // inertia weight
	public final static double c1 = 1.49; // cognitive/local weight
	public final static double c2 = 1.49; // social/global weight
	
	/**
	 * Calculate finction's value
	 * @param data
	 * @return
	 */
	public static double f(double[] data){
		return Math.exp(-data[0]*data[0] - data[1]*data[1])*Math.sin(data[0]);
	}
}
