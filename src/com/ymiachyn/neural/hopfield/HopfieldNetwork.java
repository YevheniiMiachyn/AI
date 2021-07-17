package com.ymiachyn.neural.hopfield;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.RealVectorFormat;

import com.ymiachyn.neural.TransformDirection;
import com.ymiachyn.neural.Utils;

/**
 * n/2log(n) - number of pattern Hopfield network
 * n - number of neurons
 * 
 * @author Jenya
 *
 */
public class HopfieldNetwork {
	private RealMatrix weightMatrix;
	
	public HopfieldNetwork(int dimension) {
		//with all 0's
		this.weightMatrix = MatrixUtils.createRealMatrix(dimension, dimension);
	}
	
	public void train(RealVector pattern) {
		pattern = Utils.transform(pattern, TransformDirection.BIPOLAR);
		//multiply matrix on vector
		RealMatrix patternWeightMatrix = pattern.outerProduct(pattern);
		Utils.clearDiagonal(patternWeightMatrix);
		weightMatrix = weightMatrix.add(patternWeightMatrix);
	}
	
	public String recall(RealVector pattern) {
		pattern = Utils.transform(pattern, TransformDirection.BIPOLAR);
		RealVector result = weightMatrix.preMultiply(pattern);
		
		//apply sign activation function
		result = result.map(x -> {
			return Utils.activation(x);
		});
		
		result = Utils.transform(result, TransformDirection.BINARY);
		return new RealVectorFormat().format(result);
	}
}

