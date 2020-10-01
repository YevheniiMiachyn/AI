package com.ymiachyn.neural.singlelayer;

public class ActivationFunction {
	
	/**
	 * Step function	
	 * @param activation
	 * @return
	 */
	public static int apply(float x) {
		
		int returnValue = 1;
		
		if(x < 0) {
			returnValue = 0;
		}
		
		return returnValue;
	}

}
