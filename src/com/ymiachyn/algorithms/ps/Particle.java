package com.ymiachyn.algorithms.ps;

public class Particle {

	private double[] position; // xi -> (x, y) coordinates
	private double[] velocity; // vi
	private double[] bestPosition; // pi -> (x, y) coordinates

	public Particle(double[] position, double[] velocity) {
		
		this.position = new double[Constants.NUM_DIMENSIONS];
		this.velocity = new double[Constants.NUM_DIMENSIONS];
		this.bestPosition = new double[Constants.NUM_DIMENSIONS];
		
		System.arraycopy(velocity, 0, this.velocity, 0, velocity.length);
		System.arraycopy(position, 0, this.position, 0, position.length);
		//best position is current random position
		System.arraycopy(this.position, 0, this.bestPosition, 0, this.position.length);
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}

	public double[] getBestPosition() {
		return bestPosition;
	}

	public void setBestPosition(double[] bestPosition) {
		System.arraycopy(bestPosition, 0, this.bestPosition, 0, bestPosition.length);
	}
	
	/**
	 * Looking for global minimum by
	 * comparing calculated value with best value 
	 * 
	 * @param globalBestSolution
	 */
	public void checkAndSetBestSolution(double[] globalBestSolution) {
		
		if( Constants.f(this.bestPosition)  < Constants.f(globalBestSolution)){
			globalBestSolution = this.bestPosition;
		}
	}
	
	public String toString() {
		return "Best position so far (x,y,z): " + this.bestPosition[0] + ","
				+ this.bestPosition[1] + "," + this.bestPosition[2];
	}
}
