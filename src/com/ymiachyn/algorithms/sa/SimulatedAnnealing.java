package com.ymiachyn.algorithms.sa;

import java.util.Random;

import static com.ymiachyn.Constants.MAX_TEMPERATURE;
import static com.ymiachyn.Constants.MIN_TEMPERATURE;
import static com.ymiachyn.Constants.MAX_COORDINATE;
import static com.ymiachyn.Constants.MIN_COORDINATE;
import static com.ymiachyn.Constants.COOLING_RATE;

/**
 * The temperature/cooling rate controls number of variants 
 * considered for processing, so by reducing cooling rate or
 * increasing temperature can result in better guess.
 * 
 * @author Jenya
 *
 */
public class SimulatedAnnealing {
	
	private Random randomGenerator;
	private double currentCoordinateX;
	private double nextCoordinateX;
	private double bestCoordinateX;
	
	public SimulatedAnnealing() {
		this.randomGenerator = new Random();
	}
	
	/**
	 * search for optimum until system cool off to min temperature
	 */
	public void findOptimum() {
		double temperature = MAX_TEMPERATURE;
				
		while(temperature > MIN_TEMPERATURE) {
			
			//get random next x from min-max range
			nextCoordinateX = getRandomX();
			
			double actualEnergy = getEnergy(currentCoordinateX);
			double newEnergy = getEnergy(nextCoordinateX);
			
			/*
			 * with system cooling off,
			 * probability of processing nextCoordinateX is reducing 
			 */
			if(acceptanceProbability(actualEnergy, newEnergy, temperature) > Math.random()) {
				currentCoordinateX = nextCoordinateX;
			}
			
			if(getEnergy(currentCoordinateX) < getEnergy(bestCoordinateX)) {
				bestCoordinateX = currentCoordinateX;
			}
			
			//cool system with 0.02 cooling rate each iteration
			temperature *= 1 - COOLING_RATE;
		}
		
		System.out.println("Global extream guess: x = ["+bestCoordinateX + "] f(x)= ["+
				getEnergy(bestCoordinateX)+"]");
	}
	
	private double getRandomX() {
		return randomGenerator.nextDouble()*(MAX_COORDINATE-MIN_COORDINATE)+MIN_COORDINATE;
	}

	public double getEnergy(double x) {
		return f(x);
	}
	
	public double f(double x) {
		return (x-0.3)*(x-0.3)*(x-0.3)-5*x+x*x-2;
	}
	
	public double acceptanceProbability(double energy, double newEnergy, double temperature) {
		double acceptanceProbability;
		
		/*since we are looking for global minimum
		 * if new value is smaller than current,
		 * return 1 which is guarantee to be accepted as 
		 * next current value to process
		 */
		if(newEnergy < energy){
			acceptanceProbability = 1;
		}
		else {
			acceptanceProbability = Math.exp(energy-newEnergy)/temperature;
		}
		
		return acceptanceProbability;
	}
}
