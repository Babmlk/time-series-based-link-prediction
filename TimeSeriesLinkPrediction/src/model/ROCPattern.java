package model;

public class ROCPattern implements Comparable<ROCPattern>{

public static final int POSITIVO = 1;
	
	private double score;
	private int classAttribute;
	
	public ROCPattern(double score, int classAttribute){
		this.score = score;
		this.classAttribute = classAttribute;
	}
	
	public double getScore() {
		return score;
	}
	
	public int getClassAttribute(){
		return classAttribute;
	}
	
	@Override
	public int compareTo(ROCPattern pattern) {
		int temp = 0;
		if(this.score < pattern.getScore()){
			temp = 1;
		}else if(this.score > pattern.getScore()){
			temp = -1;
		}
		return temp;
	}
		
}
