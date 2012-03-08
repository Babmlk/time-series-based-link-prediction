package model;


public class FeatureVector {

	private double[] scores;
	private int classAttribute;
	
	public FeatureVector(double[] scores, int classAttribute){
		this.scores = scores;
		this.classAttribute = classAttribute;
	}

	public double[] getScores() {
		return scores;
	}

	public int getClassAttribute() {
		return classAttribute;
	}
			
}
