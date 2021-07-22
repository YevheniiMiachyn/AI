package com.ymiachyn.neural.singlelayer;

public class ActivationFunction {
	
	/**
	 * Step function	
	 * @param activation
	 * @return
	 */
	public static float apply(float x) {
		
		int returnValue = 1;
		
		if(x < 1) {
			returnValue = 0;
		}
		
		return returnValue;
	}

}
