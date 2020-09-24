package com.ymiachyn.algorithms.tabusearch;

import java.util.List;
import java.util.Queue;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import static com.ymiachyn.Constants.TABU_TENURE;

public class TabuList {
	
	private Queue<State> tabuList;
	
	public TabuList() {
		this.tabuList = new CircularFifoQueue<>(TABU_TENURE);
	}
	
	public void add(State solution) {
		this.tabuList.add(solution);
	}
	
	public boolean contains(State solution) {
		return this.tabuList.contains(solution);
	}
	
	public List<State> getTabuItems(){
		return IteratorUtils.toList(this.tabuList.iterator());
	}
}
