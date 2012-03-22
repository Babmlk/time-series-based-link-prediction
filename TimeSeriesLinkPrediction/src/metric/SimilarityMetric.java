package metric;
import model.Graph;

public abstract class SimilarityMetric {
	
	protected Graph graph;
		
	public void init(Graph graph){
		this.graph = graph;
	}
	
	protected boolean isNodesInGraph(int v1, int v2){
		return this.graph.containsVertex(v1) && this.graph.containsVertex(v2);
	}
	
	public abstract double getScore(Integer v1, Integer v2);
	public abstract String getName();
	public abstract String getFullName();
}
