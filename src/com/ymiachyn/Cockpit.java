package com.ymiachyn;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import com.ymiachyn.algorithms.AStarSearch;
import com.ymiachyn.algorithms.BreadthFirstSearch;
import com.ymiachyn.algorithms.DepthFirstSearch;
import com.ymiachyn.algorithms.IterativeDeepeningDepthFirstSearch;
import com.ymiachyn.algorithms.Vertex;
import com.ymiachyn.algorithms.genetic.Constants;
import com.ymiachyn.algorithms.genetic.GeneticAlgorithm;
import com.ymiachyn.algorithms.genetic.Population;
import com.ymiachyn.algorithms.ps.ParticleSwarm;
import com.ymiachyn.algorithms.sa.SimulatedAnnealing;
import com.ymiachyn.algorithms.tabusearch.CostFunction;
import com.ymiachyn.algorithms.tabusearch.State;
import com.ymiachyn.algorithms.tabusearch.TabuSearch;
import com.ymiachyn.neural.hopfield.HopfieldNetwork;

import static com.ymiachyn.Constants.NUM_VALUES;

public class Cockpit {
	
	public static void main(String[] args) {
		//fireUpBreadthFirst();
		//fireUpDepthFirstSearch();
		//fireIDDFS();
	    //fireAStarSearch();
		//fireTabuSearch();
	    //fireSimulatedAnnealing();
		//fireGeneticAlgorithm();
		
		//fireParticleSwarmOptimization();
		
		fireHopfieldNetwork();
		
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
