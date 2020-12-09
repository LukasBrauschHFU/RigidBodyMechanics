package Mathematics;

import java.util.Random;

public class MyRandom {
	
	private Random random;
	
	public MyRandom() {
		this.random = new Random();
	}
	
	public double getNextHalfNormalDistribution() {
		return Math.abs(random.nextGaussian());
	}
	
	public double getNextHalfNormalDistribution(double scale) {
		return getNextHalfNormalDistribution() * scale;
	}
	
	public double getNextHalfNormalDistribution(double scale, double offset) {
		return getNextHalfNormalDistribution(scale) + offset;
	}
	
	public double nextGaussian() {
		return random.nextGaussian();
	}
	
	public double nextGaussian(double scale) {
		return nextGaussian() * scale;
	}
	
	public double nextGaussian(double scale, double offset) {
		return nextGaussian(scale) + offset;
	}
	
	
}
