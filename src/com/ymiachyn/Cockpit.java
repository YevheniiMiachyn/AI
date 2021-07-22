package com.ymiachyn;

import static com.ymiachyn.Constants.ITERATIONS;
import static com.ymiachyn.Constants.LEARNING_RATE;
import static com.ymiachyn.Constants.MOMENTUM;
import static com.ymiachyn.Constants.NUM_VALUES;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import com.ymiachyn.algorithms.AStarSearch;
import com.ymiachyn.algorithms.BreadthFirstSearch;
import com.ymiachyn.algorithms.DepthFirstSearch;
import com.ymiachyn.algorithms.IterativeDeepeningDepthFirstSearch;
import com.ymiachyn.algorithms.MazeSolver;
import com.ymiachyn.algorithms.Vertex;
import com.ymiachyn.algorithms.alphabeta.Game;
import com.ymiachyn.algorithms.genetic.Constants;
import com.ymiachyn.algorithms.genetic.GeneticAlgorithm;
import com.ymiachyn.algorithms.genetic.Population;
import com.ymiachyn.algorithms.ps.ParticleSwarm;
import com.ymiachyn.algorithms.sa.SimulatedAnnealing;
import com.ymiachyn.algorithms.tabusearch.CostFunction;
import com.ymiachyn.algorithms.tabusearch.State;
import com.ymiachyn.algorithms.tabusearch.TabuSearch;
import com.ymiachyn.neural.backprop.BackpropNeuralNetwork;
import com.ymiachyn.neural.hopfield.HopfieldNetwork;
import com.ymiachyn.neural.singlelayer.Perceptron;

public class Cockpit {
	
	public static void main(String[] args) {
		//fireUpBreadthFirst();
		//fireUpDepthFirstSearch();
		//findMazeExit();
		//fireIDDFS();
	    //fireAStarSearch();
		//fireTabuSearch();
	    //fireSimulatedAnnealing();
		//fireGeneticAlgorithm();
		//fireParticleSwarmOptimization();
		//fireMinMax();
		
		//fireHopfieldNetwork();
		
		fireSingleLayerNetwork();
		//fireBackpropagationNetwork();
		//fireBackpropagationNetworkIris();
	}
	
	private static void fireMinMax() {
		new Game();
	};
		
	private static void findMazeExit() {
		int[][] maze = {
			{1,1,0,0,0,1},	
			{2,0,0,1,1,1},
			{1,1,0,1,1,1},
			{1,1,0,0,0,0},
			{0,0,0,1,0,1},
			{1,1,0,1,0,0}
		};
		
		new MazeSolver(maze, 1, 0).findWay();
	};
	
	/**
	 * Train NN to solve classification problem on the 
	 * Iris data set - (identify different types of Iris
	 * 
	 * https://archive.ics.uci.edu/ml/datasets/iris
	 * 
	 */
	private static void fireBackpropagationNetworkIris() {
		float[][] trainingData = null;
		float[][] trainingResults = null;
			
		List<float[][]> data = loadTrainingData();
		trainingData = data.get(0);
		trainingResults = data.get(1);
		
		BackpropNeuralNetwork bnn = new BackpropNeuralNetwork(4, new int[]{6, 3});
		for(int iteration = 0; iteration < ITERATIONS; iteration++)
		{			
			for (int i = 0; i < trainingResults.length; i++) {
				bnn.train(trainingData[i], trainingResults[i], LEARNING_RATE, MOMENTUM);
			}
			
			if((iteration + 1) % 10000 == 0)
			{
				System.out.println("Iteration #: " + iteration);
				for (int i = 0; i < trainingResults.length; i++) {
					
					String outputAsString;
					float[] tData = trainingData[i];
					float[] out = bnn.run(tData);
					
					//convert data from binary representation
					if(Math.round(out[0]) == 1 && Math.round(out[1]) == 0 && Math.round(out[2]) == 0)
						outputAsString = "Iris-setosa";
					else if(Math.round(out[0]) == 0 && Math.round(out[1]) == 1 && Math.round(out[2]) == 0)
						outputAsString = "Iris-versicolor";
					else if(Math.round(out[0]) == 0 && Math.round(out[1]) == 0 && Math.round(out[2]) == 1)
						outputAsString = "Iris-virginica";
					else 
						outputAsString = "Unknown species of Iris!";
									
					//there are some error results but that's OK - error rate is small
					System.out.println(Arrays.toString(tData) + " --> " + outputAsString); 
				}
			}
		}
	}
	
	private static List<float[][]> loadTrainingData() 
	{
		float[][] iData = null, iResults = null;
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(".\\resources\\iris.data")))
		{			
			List<float[]> data = new ArrayList<>();
			List<float[]> result = new ArrayList<>();
			
			br.lines().forEach(line -> {
				if(!line.trim().isEmpty()) {
					//split line into parameters
					String[] params = line.split(",");
					
					//define array to hold parsed params as floats
					int elementsCnt = (params.length - 1);
					float[] dataSet = new float[elementsCnt];
					
					//populate dataSet with floats
					for (int i = 0; i < elementsCnt; i++) {
						dataSet[i] = Float.parseFloat(params[i].trim());
					}
					
					data.add(dataSet);
					
					//populate result dataSet with matching result - binary representation
					switch(params[elementsCnt]){
						case "Iris-setosa":
							result.add(new float[] {1f, 0f, 0f});
						break;
						
						case "Iris-versicolor":
							result.add(new float[] {0f, 1f, 0f});
						break;
						
						case "Iris-virginica":
							result.add(new float[] {0f, 0f, 1f});
						break;
						
						default:
						break;
					}
				}
		    });
			
			//adding parsed data to 2d arrays 
			iData = new float[data.size()][];
			iResults = new float[data.size()][];
			
			for (int i = 0; i < data.size(); i++) {
				iData[i] = data.get(i);
				iResults[i] = result.get(i);
			}
		}		
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//return loaded/transformed data
		return Arrays.asList(iData, iResults);
	}


	private static void fireBackpropagationNetwork() {
		
		/*
		 * data for XOR logical relations (cannot be solved with single layer perceptrons,
		 * but adding hidden layer like at BPN - allows to solve non-linear problems
		 */
		float[][] trainingData = new float[][] {
			new float[] {0, 0},
			new float[] {0, 1},
			new float[] {1, 0},
			new float[] {1, 1}
		};
		
		float[][] trainingResults = new float[][] {
				new float[] {0},
				new float[] {1},
				new float[] {1},
				new float[] {0}	
		};
		
		BackpropNeuralNetwork bnn = new BackpropNeuralNetwork(2, 3, 1);
		for(int iteration = 0; iteration < ITERATIONS; iteration++)
		{
			System.out.println("\n# of iterations: " + (iteration + 1));
			for (int i = 0; i < trainingResults.length; i++) {
				bnn.train(trainingData[i], trainingResults[i], LEARNING_RATE, MOMENTUM);
			}
			
			System.out.println();
			
			for (int i = 0; i < trainingResults.length; i++) {
				float[] t = trainingData[i];
				
				System.out.printf("%.1f, %.1f --> %.3f\n", t[0], t[1], bnn.run(t)[0]);
			}
		}
	}


	/**
	 * Just to demonstrate main idea of neural network
	 * we train it to 'predict' correct result instead
	 * The example is simple, but it predicts - not matching like key/value!
	 *
	 */
	private static void fireSingleLayerNetwork() {
		/*
		 * train to predict for boolean operator OR
		 * 
		 * Not going to work for XOR because XOR is non-linear function
		 * Single layer Neural Networks (without hidden layer) works only for linear functions
		 */
		float[][] input = { {0,0}, {0,1}, {1,0}, {1,1} };
		float[] output = {0,0,0,1};
		
		Perceptron perceptron = new Perceptron(input, output);
		perceptron.train(0.21f);
		
		System.out.println("The error is 0 so our neural network is ready! Predictions: ");
		
		System.out.println(perceptron.calculateOutput(new float[]{0,0}));
		System.out.println(perceptron.calculateOutput(new float[]{0,1}));
		System.out.println(perceptron.calculateOutput(new float[]{1,0}));
		System.out.println(perceptron.calculateOutput(new float[]{1,1}));
		
	}



	/**
	 * Autoassociative memory when able recognize object by
	 * it's incomplete representation.
	 * 
	 **/
	private static void fireHopfieldNetwork() {
		/********
		 * 1 1 1           1 1 1
		 * 1 0 0  - C      0 1 0  - T
		 * 1 1 1           0 1 0 
		 * ******       
		 */
		RealVector c = new ArrayRealVector(new double[] {1,1,1,1,0,0,1,1,1});
		RealVector t = new ArrayRealVector(new double[] {1,1,1,0,1,0,0,1,0});
		
		RealVector noisyC = new ArrayRealVector(new double[] {1,1,0,1,0,0,1,1,0});
		RealVector noisyT = new ArrayRealVector(new double[] {1,1,1,0,1,1,0,1,0});
		
		// 9x9 Matrix to hold 9 digit letter representations
		HopfieldNetwork hn = new HopfieldNetwork(9);
		//train NN to recognize correct c and t
		hn.train(c);
		hn.train(t);
		
		/*
		 * feed incomplete (noisy) representations 
		 * and verify that autoassociative memory is working
		 * if returned vector is for complete version of a letter
		 */
		System.out.println("Found for noisy C: " + hn.recall(noisyC));
		System.out.println("Found for noisy T: " + hn.recall(noisyT));
		
	}

	private static void fireParticleSwarmOptimization() {
		ParticleSwarm ps = new ParticleSwarm();
		ps.solve();
		ps.showSolution();
	}
	
	private static void fireGeneticAlgorithm() {
		GeneticAlgorithm algorithm = new GeneticAlgorithm();
        Population population = new Population(100);
        population.initialize();
        
        int generationCount = 0;
        
        while (population.getFittest().getFitness() != Constants.MAX_FITNESS) {
            ++generationCount;
            System.out.println("Generation: " + generationCount + " Fittest: " + population.getFittest().getFitness());
            System.out.println(population.getFittest()+"\n");
            population = algorithm.evolvePopulation(population);
        }
        
        System.out.println("Solution found:");
        System.out.println(population.getFittest());
	}
	
	private static void fireSimulatedAnnealing() {
		SimulatedAnnealing sa = new SimulatedAnnealing();
		sa.findOptimum();
		
	}

	private static void fireTabuSearch() {
		State[][] states = new State[NUM_VALUES][NUM_VALUES];
		
		int row = 0;
		int col = 0;
		
		for(double x=-10;x<9.9;x+=0.1) {
			for(double y=-10;y<9.9;y+=0.1) {
				states[row][col] = new State(x, y, CostFunction.f(x, y));
				col++;
			}
			
			col = 0;
			row++;
		}
		
		//set neighbor for first col
		for(int i=0;i<200;i++)
			states[i][0].addNeighbor(states[i][1]);
		
		//set neighbor for first col
		for(int i=0;i<200;i++)
			states[i][199].addNeighbor(states[i][198]);
		
		//set neighbor for first row
		for(int i=0;i<200;i++)
			states[0][i].addNeighbor(states[1][i]);
		
		//set neighbor for last row
		for(int i=0;i<200;i++)
			states[199][i].addNeighbor(states[198][i]);
		
		//set neighbor for middle nodes
		for(int i=1;i<199;i++) {
			for(int j=1;j<199;j++) {
				states[i][j].addNeighbor(states[i-1][j]);
				states[i][j].addNeighbor(states[i+1][j]);
				states[i][j].addNeighbor(states[i][j-1]);
				states[i][j].addNeighbor(states[i][j+1]);
			}
		}
		
		TabuSearch tabuSearch = new TabuSearch(states);
		System.out.println(tabuSearch.solve(states[100][100]));
			
	}

	private static void fireAStarSearch() {
		AStarSearch algorithm = new AStarSearch();
		algorithm.search();
		algorithm.showPath();
	}
	
	private static void fireIDDFS() {
		Vertex vertexToFind = getAdjacentVertexes().stream()
				.filter(v -> v.getName().equals("E"))
				.findFirst().get();
				
		IterativeDeepeningDepthFirstSearch iddfs = 
				new IterativeDeepeningDepthFirstSearch(vertexToFind);
		
		
		System.out.println("Searching for " + vertexToFind + "..."); 
		iddfs.iddfs(getAdjacentVertexes().stream().findFirst().get());
	}
	
	private static void fireUpBreadthFirst() {
				
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.traverse(getVertexes().stream().findFirst().get());
	}
	
	private static void fireUpDepthFirstSearch() {
		DepthFirstSearch dfs = new DepthFirstSearch();
		dfs.bfs(getVertexes());
	}
	
	private static List<Vertex> getAdjacentVertexes(){
		Vertex a = new Vertex("A"); 
		Vertex b = new Vertex("B"); 
		Vertex c = new Vertex("C"); 
		Vertex d = new Vertex("D"); 
		Vertex e = new Vertex("E"); 
		Vertex f = new Vertex("F"); 
		Vertex g = new Vertex("G"); 
		Vertex h = new Vertex("H");
		
		a.addNeighbor(f);
		a.addNeighbor(b);
		a.addNeighbor(g);
		
	    b.addNeighbor(c);
		b.addNeighbor(d);
				
		d.addNeighbor(e);
	
		g.addNeighbor(h);
				
		List<Vertex> vertexes = Arrays.asList(a, b, c, d, e, f, g, h);
		
		return vertexes;
	}
	
	private static List<Vertex> getVertexes() {
		Vertex a = new Vertex("A"); 
		Vertex b = new Vertex("B"); 
		Vertex c = new Vertex("C"); 
		Vertex d = new Vertex("D"); 
		Vertex e = new Vertex("E"); 
		Vertex f = new Vertex("F"); 
		Vertex g = new Vertex("G"); 
		Vertex h = new Vertex("H");
		
		a.addNeighbor(f);
		a.addNeighbor(b);
		a.addNeighbor(g);
		
		f.addNeighbor(a);
		
		b.addNeighbor(c);
		b.addNeighbor(d);
		b.addNeighbor(a);
		
		d.addNeighbor(e);
		d.addNeighbor(b);
		
		c.addNeighbor(b);
		e.addNeighbor(d);
		
		g.addNeighbor(h);
		g.addNeighbor(a);
		h.addNeighbor(g);
		
		List<Vertex> vertexes = Arrays.asList(a, b, c, d, e, f, g, h);
		
		return vertexes;
	}
	
}
