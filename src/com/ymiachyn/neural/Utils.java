package com.ymiachyn.neural;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Utils {
	
	public static void clearDiagonal(RealMatrix matrix) {
		for(int i=0; i<matrix.getColumnDimension(); ++i) {
			matrix.setEntry(i, i, 0);
		}
	}

	public static RealVector transform(RealVector pattern, TransformDirection direction) {
		
		return pattern.map(x -> {
			double returnValue = x;
			
			if(x == 0 && TransformDirection.BIPOLAR == direction) {
				returnValue = -1;
			}
			else if(x == -1 && TransformDirection.BINARY == direction) {
				returnValue = 0;
			}
			
			return returnValue;
		});
	}
	
	public static double activation(double x) {
		double returnValue = 1;
		
		if(x < 0) {
			returnValue = -1;
		}
		
		return returnValue;
	}
	
	/**
	 * Sigmoid activation function
	 * 
	 * @param x
	 * @return
	 */
	public static float sigmoid(float x) {
		return (float) (1/(1 + Math.exp(-x)));
	}
	
	/**
	 * Derivative sigmoid function
	 * 
	 * @param sigmoid
	 * @return
	 */
	public static float dSigmoid(float sigmoid) {
		return sigmoid * (1 - sigmoid);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
