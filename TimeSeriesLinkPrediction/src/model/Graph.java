package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Graph extends UndirectedSparseGraph<Integer, Edge>{

	private static final long serialVersionUID = 1L;
	
	public Graph(){
		
	}
	
	public Graph(ArrayList<Integer> vertices, ArrayList<Edge> edges){
		this.addVertices(vertices);
		this.addEdges(edges);
	}

	public void addVertices(Collection<Integer> vertices){
		for (Integer vertice : vertices) {
			this.addVertex(vertice);
		}
	}

	public void addEdges(Collection<Edge> edges){
		for (Edge edge : edges) {
			this.addEdge(edge, edge.getSource(), edge.getTarget());
		}
	}	
	
	public void removeVertices(Collection<Integer> vertices){
		for (Integer vertex : vertices) {
			this.removeVertex(vertex);
		}
	}
	
	public boolean hasCommonNeighbor(int v1, int v2){
		boolean temp = false;
		Collection<Integer> cn = new ArrayList<Integer>(this.getNeighbors(v1));
		cn.retainAll(new ArrayList<Integer>(this.getNeighbors(v2)));
		if(!cn.isEmpty()){
			temp = true;
		}				
		return temp;
	}
	
	public static Graph union(ArrayList<Graph> graphs){
		Graph union = null;
		if(!graphs.isEmpty()){
			
			union = new Graph();
			//Recuperar todos os vértices dos grafos
			HashSet<Integer> vertices = new HashSet<Integer>();
			for (Graph graph : graphs) {
				vertices.addAll(new HashSet<Integer>(graph.getVertices()));
			}
			//Inserir em uniao todos os vértices
			union.addVertices(new ArrayList<Integer>(vertices));
			//Inserir em uniao as arestas dos grafos
			for (Graph graph : graphs) {
				union.addEdges(graph.getEdges());
			}		
		}

		return union;
	}
	
	public void complete(Collection<Integer> vertices){
		for(Integer vertex : vertices){
			if(!this.containsVertex(vertex)){
				this.addVertex(vertex);
			}
		}
	}
	
	public boolean isNeighbor(Integer v1, Integer v2){
		boolean temp = false;
		if(this.containsVertex(v1) && this.containsVertex(v2)){
			temp = super.isNeighbor(v1, v2);
		}
		return temp;
	}
	
}
