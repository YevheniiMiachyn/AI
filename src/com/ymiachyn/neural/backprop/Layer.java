package com.ymiachyn.neural.backprop;

import java.util.Arrays;
import java.util.Random;

import com.ymiachyn.neural.Utils;

public class Layer {

	 private float[] output;
	 private float[] input;
	 private float[] weights;
	 
	 //represents change weights of previous iterations
	 private float[] dWeights;
	 
	 private Random random;
	 
	 public Layer(int inputSize, int outputSize) {
		// +1 for bias node
		input = new float[inputSize + 1];
		output = new float[outputSize];
		//# of weights between neurons of layers (including bias neuron)
		weights = new float[(inputSize + 1) * outputSize];
		dWeights = new float[weights.length];
		
		random = new Random();
		
		initWeights();
	 }

	private void initWeights() {
		//generated weights in range [-2,2]
		for (int i = 0; i < weights.length; i++)
			weights[i] = (random.nextFloat() - 0.5f) * 4f;//[-2,2]
	}
	
	public float[] run(float[] inputArray) {
		
		System.arraycopy(inputArray, 0, input, 0, inputArray.length);
		
		//set weights for bias node - always 1
		input[input.length-1] = 1;
		int offset = 0;
		
		for (int i = 0; i < output.length; i++) {
			for (int j = 0; j < input.length; j++) {
				output[i] += weights[offset + j] * input[j];
			}
			
			output[i] = Utils.sigmoid(output[i]);
			offset += input.length;
		}
		
		return Arrays.copyOf(output, output.length);
	}
	
	/**
	 * Backpropagation formula
	 * 
	 * dWt = a * gradientFunction + m * dWt-1
	 * 
	 * dW - change in edge weight at time t
	 * a - learning rate
	 * m - momentum
	 * dWt-1 - previous change of dW (previous iteration)
	 * 
	 * @param error
	 * @param learningRate
	 * @param momentum
	 * @return
	 */
	public float[] train(float[] error, float learningRate, float momentum) {
		int offset = 0;
		float[] nextError = new float[input.length];
		
		for (int i = 0; i < output.length; i++) {
			
			//calculate delta for the output
			float delta = error[i] * Utils.dSigmoid(output[i]);
			
			for (int j = 0; j < input.length; j++) {
				
				int weightIndex = offset + j;
				nextError[j] = nextError[j] + weights[weightIndex] * delta;
				//delta edge weight
				float dw = input[j] * delta * learningRate;
				
				weights[weightIndex] += dWeights[weightIndex] * momentum + dw;
				dWeights[weightIndex] = dw;
			}
			
			offset += input.length;
		}
		
		return nextError;
	}
	 
	 
}
