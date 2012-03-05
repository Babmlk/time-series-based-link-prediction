package metric.topologic;

import java.util.ArrayList;

import metric.SimilarityMetric;

public class CommonNeighbors extends SimilarityMetric{
	
	private final static String NAME = "CN";

	public double getScore(Integer v1, Integer v2){
		double score = 0;
		ArrayList<Integer> cn = new ArrayList<Integer>(this.graph.getNeighbors(v1));
		cn.retainAll(new ArrayList<Integer>(this.graph.getNeighbors(v2)));
		score = cn.size();
		return score;
	}
	
	public String getName() {
		return NAME;
	}	
}
