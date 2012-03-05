package reading;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import model.Edge;
import model.Graph;

import org.apache.commons.collections15.Transformer;

import db.GraphRepository;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class GraphReader {

	private GraphRepository graphRepository;

	public GraphReader(){
		this.graphRepository = new GraphRepository();
	}

	public Collection<Graph> retrieveGraphsByYearsAndCategory(int[] years, String category){
		Collection<Graph> graphs = new ArrayList<Graph>();
		for(int year : years){
			System.out.println("Lendo grafo de " + year);
			graphs.add(graphRepository.retrieveGraphByYearAndCategory(year, category));
		}
		return graphs;
	}

	public static Graph lerGrafoML(String filename) throws GraphIOException, FileNotFoundException{
		Transformer<NodeMetadata, Integer> vtrans = new Transformer<NodeMetadata,Integer>(){
			public Integer transform(NodeMetadata nmd ){
				return Integer.valueOf(nmd.getId());
			}
		};
		Transformer<EdgeMetadata, Edge> etrans = new Transformer<EdgeMetadata,Edge>(){
			public Edge transform( EdgeMetadata emd ){
				return (new Edge(emd.getSource() + "#" + emd.getTarget()));
			}
		};
		Transformer<HyperEdgeMetadata, Edge> hetrans = new Transformer<HyperEdgeMetadata,Edge>(){
			public Edge transform( HyperEdgeMetadata emd ){
				return null;
			}
		};
		Transformer<GraphMetadata, Graph> gtrans = new Transformer<GraphMetadata,Graph>(){
			public Graph transform(GraphMetadata gmd){
				return new Graph();
			}
		};

		GraphMLReader2<Graph, Integer, Edge> gmlReader = 
			new GraphMLReader2<Graph, Integer, Edge>(new FileReader(filename),
					gtrans, vtrans, etrans, hetrans);
		return gmlReader.readGraph();
	}


}
