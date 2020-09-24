package com.ymiachyn.algorithms.tabusearch;

import static com.ymiachyn.Constants.NUM_ITERATIONS;

import java.util.List;;

public class TabuSearch {
	private State[][] states;
	private TabuList tabuList;
	private NeighborSolutionHandler neighborHandler;
	
	public TabuSearch(State[][] states) {
		this.states = states;
		this.tabuList = new TabuList();
		this.neighborHandler = new NeighborSolutionHandler();
	}
	
	public State solve(State initialSolution) {
		State bestState = initialSolution;
		State currentState = initialSolution;
		int iterationCounter = 0;
		
		while(iterationCounter < NUM_ITERATIONS) {
			List<State> candidateNeighbors = currentState.getNeighbors();
			List<State> solutionsTabu = tabuList.getTabuItems();
			
			State bestNeighborFound = 
					neighborHandler.getBestNeighbor(states[100][100],
							                        candidateNeighbors,
							                        solutionsTabu);
			
			if(bestNeighborFound.getZ() < bestState.getZ()) {
				bestState = bestNeighborFound;
			}
			
			tabuList.add(currentState);
			
			currentState = bestNeighborFound;
			
			
			iterationCounter++;
		}
		
		return bestState;
	}
}
