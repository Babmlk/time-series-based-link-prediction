package metric.topologic;

import java.util.ArrayList;

import metric.SimilarityMetric;

public class AdamicAdar extends SimilarityMetric{

	private final static String NAME = "AA";
	private final static String FULL_NAME = "Adamic/Adar (AA)";

	public double getScore(Integer v1, Integer v2){
		double score = 0;
		if(super.isNodesInGraph(v1, v2)){
			ArrayList<Integer> cn = new ArrayList<Integer>(this.graph.getNeighbors(v1));
			cn.retainAll(new ArrayList<Integer>(this.graph.getNeighbors(v2)));
			for (Integer vizinho : cn) {
				score += 1/Math.log(this.graph.getNeighborCount(vizinho));
			}
		}
		return score;
	}

	public String getName() {
		return NAME;
	}	

	public String getFullName(){
		return FULL_NAME;
	}
}
