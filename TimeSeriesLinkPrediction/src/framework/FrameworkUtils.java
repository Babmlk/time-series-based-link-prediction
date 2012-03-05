package framework;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Frame;
import model.Graph;
import model.PairOfNodes;
import reading.GraphReader;
import configuration.Configuration;
import edu.uci.ics.jung.io.GraphIOException;

public class FrameworkUtils {
	
	public static ArrayList<Graph> readSnapshots(){
		ArrayList<Graph> grafos = new ArrayList<Graph>();
		try {
			for(int i = Configuration.inicialYear; i <= Configuration.finalYear; i++){
				grafos.add(GraphReader.lerGrafoML(Configuration.assembleGraphPath(i)));
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grafos;
	}	
	
	public static ArrayList<Frame> buildFrames(ArrayList<Graph> snapshots){
		LinkedList<Frame> frames = new LinkedList<Frame>();		
		ArrayList<Graph> temp = new ArrayList<Graph>();
		int lastIndex = snapshots.size() - 1;
		int i = lastIndex;
		
		while(lastIndex - Configuration.windowOfPrediction >= 0){
			while(i >= lastIndex - Configuration.windowOfPrediction){
				temp.add(snapshots.get(i));
				i--;
			}
			frames.addFirst(new Frame(temp));
			temp.clear();
			lastIndex = i;
		}
		return new ArrayList<Frame>(frames);
	}
	
	public static ArrayList<PairOfNodes> getTestablePairOfNodes(Graph trainingNetwork, Graph testNetwork){
		ArrayList<PairOfNodes> pairsOfNodes = new ArrayList<PairOfNodes>();
		ArrayList<Integer> vertices = new ArrayList<Integer>(testNetwork.getVertices());
		for(int i = 0; i < vertices.size(); i++){
			for(int j = 0; j < vertices.size(); j++){
				if(trainingNetwork.hasCommonNeighbor(i, j)){
					pairsOfNodes.add(new PairOfNodes(i, j));
				}
			}
		}
		return pairsOfNodes;
	}
	
}
