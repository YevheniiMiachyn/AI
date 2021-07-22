package com.ymiachyn.neural.singlelayer;

public class Perceptron {

	private float[] weights;
	private float[][] input;
	private float[] output;
	private int numOfWeights;
	
	public Perceptron(float[][] input, float[] output) {
		this.input = input;
		this.output = output;
		this.numOfWeights = input[0].length;
		this.weights = new float[numOfWeights];
	
		initialzeWeight();
	}

	private void initialzeWeight() {
		for(int i=0;i<numOfWeights;++i)
			weights[i] = (float)(Math.random() - 0.5);
	}
	
	//train model
	public void train(float learningRate) {
		
		float totalError = 1;
		
		while ( totalError != 0 ) {
			
			totalError = 0;
			
			for(int i=0;i<output.length;++i) {
				
				//prediction made by NN
				float calculatedOutput = calculateOutput(input[i]);
				float error = output[i]-calculatedOutput;
				
				totalError += error;
				
				//update weights based on error and learning rate
				for(int j=0;j<numOfWeights;++j) {
					weights[j] = weights[j] + learningRate * input[i][j] * error;
					System.out.println("Updated weight: " + weights[j]);
				}
			}
			
			System.out.println("Keep on training the network, error is: " + totalError);
		}
		
	}
	
	public float calculateOutput(float[] input) {
		
		float sum = 0f;
		
		for(int i=0;i<input.length;++i)
			sum = sum + weights[i] * input[i];
		
		return ActivationFunction.apply(sum);
	}
}
