package com.ymiachyn.neural.backprop;

/**
 * 
 * Backpropagation feed-forward neural network
 *
 */
public class BackpropNeuralNetwork {
	
	private Layer[] layers;

	/**
	 * 
	 * @param inputSize number of nodes at input layer
	 * @param hiddenSize number of nodes at hidden layer
	 * @param outputSize number of nodes at output layer
	 */
	public BackpropNeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
		//2 layers for edge-weights for 3 layer neural network
		layers = new Layer[2];
		//hold edges between input and hidden layers
		layers[0] = new Layer(inputSize, hiddenSize);
		//hold edges between hidden and output layers
		layers[1] = new Layer(hiddenSize, outputSize);
	}
	
	public BackpropNeuralNetwork(int inputSize, int[] layersSize) {
		layers = new Layer[layersSize.length];
		for (int i = 0; i < layersSize.length; i++) {
			int inSize = i == 0 ? inputSize : layersSize[i - 1];
			layers[i] = new Layer(inSize, layersSize[i]);
		}
	}
	
	public Layer getLayer(int idx) {
		return layers[idx];
	}
	
	/**
	 * return activation of the given layer
	 * @param input
	 * @return
	 */
	public float[] run(float[] input) {
		
		float[] activations = input;
		
		for (int i = 0; i < layers.length; i++) {
			activations = layers[i].run(activations);
		}
				
		return activations;
	}
	
	public void train(float[] input, float[] targetOutput, float learningRate, float monentum){
		
		float[] calculatedOutput = run(input);
		float[] error = new float[calculatedOutput.length];
		
		/**
		 * Calculate error by calculating error between targeted output and input
		 */
		for (int i = 0; i < error.length; ++i) {
			error[i] = targetOutput[i] - calculatedOutput[i];
		}
		
		/*
		 * using backpropagation - in backward manner from output to hidden to input layer
		 * keep calculation error
		 */
		for (int i = layers.length - 1; i >= 0; i--) {
			error = layers[i].train(error, learningRate, monentum);
		}
	}
}
