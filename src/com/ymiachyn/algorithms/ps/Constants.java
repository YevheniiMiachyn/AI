package com.ymiachyn.algorithms.ps;

public class Constants {

	private Constants(){
		
	}
	
	/**
	 * Not a dimension but more like number of coordinates to
	 * define point at n-dimension. In this case it's 3d where 
	 * x, y are coordinates that used to calculate 3rd coordinate - 
	 * that is a function solution f(x, y)
	 */
	public final static int NUM_DIMENSIONS = 2; 
	public final static int NUM_PARTICLES = 10;
	public final static int MAX_ITERATIONS = 10000;
	public final static double MIN = -2.2; 
	public final static double MAX = 2.2;
	public final static double w = 0.729; // inertia weight
	public final static double c1 = 1.49; // cognitive/local weight
	public final static double c2 = 1.49; // social/global weight
	
	/**
	 * Calculate finction's value for coordinates data[x, y]
	 * @param data
	 * @return
	 */
	public static double f(double[] data){
		return Math.exp(-data[0]*data[0] - data[1]*data[1])*Math.sin(data[0]);
	}
}
