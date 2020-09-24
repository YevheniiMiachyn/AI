package com.ymiachyn.algorithms.tabusearch;

import java.util.List;

public class NeighborSolutionHandler {

	public State getBestNeighbor(State miidleState, List<State> neighborStates, List<State> tabuStates) {
		State bestSolution;
		
		neighborStates.removeAll(tabuStates);
		//if all states are tabu -> go to middle state
		if(neighborStates.isEmpty()) return miidleState;
		
		bestSolution = neighborStates.get(0);
		for(int i=1;i<neighborStates.size();i++) {
			if(neighborStates.get(i).getZ() < bestSolution.getZ()) {
				bestSolution = neighborStates.get(i);
			}
		}
		
		System.out.println("Best Solution is: " + bestSolution);
		
		return bestSolution;
	}
}
