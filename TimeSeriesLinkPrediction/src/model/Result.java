package model;

public class Result {

	private double meanRate;
	private double sdRate;
	
	public Result(double meanRate, double sdRate){
		this.meanRate = meanRate;
		this.sdRate = sdRate;
	}

	public double getMeanRate() {
		return meanRate;
	}

	public double getSdRate() {
		return sdRate;
	}
		
}
