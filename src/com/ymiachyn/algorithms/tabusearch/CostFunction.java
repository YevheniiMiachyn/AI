package com.ymiachyn.algorithms.tabusearch;

public class CostFunction {
	
	/**
	 * Function return value
	 * @param x
	 * @param y
	 * @return
	 */
	public static double f(double x, double y) {
		return Math.exp(-x*x-y*y)*Math.sin(x);
	}

}
