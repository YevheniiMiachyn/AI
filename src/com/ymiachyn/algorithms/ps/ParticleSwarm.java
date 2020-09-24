package com.ymiachyn.algorithms.ps;

import java.text.DecimalFormat;

public class ParticleSwarm {

	private double[] globalBestSolution;
	private Particle[] particleSwarm;
	//number of completed iterations
	private int epochs;

	public ParticleSwarm() {
		this.globalBestSolution = new double[Constants.NUM_DIMENSIONS];
		this.particleSwarm = new Particle[Constants.NUM_PARTICALS];
		generateRandomSolution();
	}

	public void solve() {
		
		initializeSwarm();

		//iterate max # of iterations
		while (this.epochs < Constants.MAX_ITERATIONS) {

			/**
			 * for each particle in swarm
			 */
			for (Particle particle : this.particleSwarm) {

				//update/change velocity
				for (int i = 0; i < particle.getVelocity().length; ++i) {

					double rp = Math.random();
					double rg = Math.random();

					/*
					 * change particle's velocity basing on formula(something predetermined)
					 * Also! velocity should be updated prior to updating position
					 * since position depends on particle velocity
					 */
					particle.getVelocity()[i] = Constants.w * particle.getVelocity()[i]
							+ Constants.c1 * rp * (particle.getBestPosition()[i] - particle.getPosition()[i])
							+ Constants.c2 * rg * (this.globalBestSolution[i] - particle.getPosition()[i]);
				}

				//update/change position
				for (int i = 0; i < particle.getPosition().length; ++i) {

					/*
					 * update position by formula pi = pi + vi
					 * where pi - current position and vi - velocity calculated
					 * at previous step. If position is above or below boundaries - 
					 * reset to max/min allowed.
					 */
					particle.getPosition()[i] += particle.getVelocity()[i];

					if (particle.getPosition()[i] < Constants.MIN) {
						particle.getPosition()[i] = Constants.MIN;
					}
					
					if (particle.getPosition()[i] > Constants.MAX) {
						particle.getPosition()[i] = Constants.MAX;
					}
				}
				
				//update particle and if so - global best positions
				if (Constants.f(particle.getPosition()) > Constants.f(particle.getBestPosition())) {
					particle.setBestPosition(particle.getPosition());
				}
				
				if (Constants.f(particle.getBestPosition()) > Constants.f(this.globalBestSolution)) {
					System.arraycopy(particle.getBestPosition(), 0, this.globalBestSolution, 0, particle.getBestPosition().length);
				}	
			}
			// >> to next iteration
			++this.epochs;
		}
	}
	
	/*
	 * initialize particles swarm with default values
	 * Verify/update global best solution for each particles
	 */
	private void initializeSwarm() {
		for (int i = 0; i < Constants.NUM_PARTICALS; ++i) {

			//random starting speed/velocity
			double[] x = initializeLocation();
			double[] v = initializeVelocity();

			this.particleSwarm[i] = new Particle(x, v);
			this.particleSwarm[i].checkAndSetBestSolution(this.globalBestSolution);
		}
	}

	/**
	 * Initial random velocity for a particle
	 * 
	 * @return
	 */
	private double[] initializeVelocity() {

		double vx = random(-(Constants.MAX - Constants.MIN), Constants.MAX - Constants.MIN);
		double vy = random(-(Constants.MAX - Constants.MIN), Constants.MAX- Constants.MIN);

		double[] newVelocity = new double[] { vx, vy };

		return newVelocity;
	}

	/**
	 * Initial random location for a particle
	 * 
	 * @return
	 */
	private double[] initializeLocation() {

		double x = random(Constants.MIN, Constants.MAX);
		double y = random(Constants.MIN, Constants.MAX);

		double[] newLocation = new double[] { x, y };

		return newLocation;
	}

	/**
	 * Generate random solution - starting point
	 * and set it as global best.
	 */
	private void generateRandomSolution() {
		for (int i = 0; i < Constants.NUM_DIMENSIONS; ++i) {
			double randCoordinate = random(Constants.MIN, Constants.MAX);
			this.globalBestSolution[i] = randCoordinate;
		}
	}

	private double random(double min, double max) {
		return min + (max - min) * Math.random();
	}

	public void showSolution() {
		DecimalFormat df = new DecimalFormat("#.###############");
		System.out.println("Solution of PSO problem!");
		System.out.println("Best solution -> x: " + df.format(this.globalBestSolution[0])+ " - y:" + df.format(this.globalBestSolution[1]));
		System.out.println("Value f(x,y)=" + df.format(Constants.f(globalBestSolution)));
	}
}
