package metric;
import model.Graph;

public abstract class SimilarityMetric {
	
	protected Graph graph;
		
	public void init(Graph graph){
		this.graph = graph;
	}
	
	public abstract double getScore(Integer v1, Integer v2);
	public abstract String getName();
	public abstract String getFullName();
}
