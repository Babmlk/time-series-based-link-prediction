package metric.topologic;

import metric.SimilarityMetric;

public class PreferentialAttachment extends SimilarityMetric{
	
	private final static String NAME = "PA";
	private final static String FULL_NAME = "Preferential Attachment (PA)";

	public double getScore(Integer v1, Integer v2){
		double score = 0;
		score = this.graph.getNeighborCount(v1)*this.graph.getNeighborCount(v2);
		return score;
	}

	public String getName() {
		return NAME;
	}		
	
	public String getFullName(){
		return FULL_NAME;
	}
	
}
