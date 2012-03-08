package framework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import metric.SimilarityMetric;
import model.FTSNS;
import model.Frame;
import model.Graph;
import model.PairOfNodes;
import model.TimeSeries;
import reading.GraphReader;
import weka.WekaUtils;
import configuration.Configuration;
import configuration.Paths;
import edu.uci.ics.jung.io.GraphIOException;
import forecasting.Forecaster;
import format.Formarter;

public class FrameworkUtils {
	
	private final static String HYBRID_METHOD = "hybrid";

	public static ArrayList<Graph> readSnapshots(){
		ArrayList<Graph> grafos = new ArrayList<Graph>();
		try {
			for(int i = Configuration.inicialYear; i <= Configuration.finalYear; i++){
				grafos.add(GraphReader.lerGrafoML(Paths.assembleGraphsPath(i)));
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

	public static void saveTimeSeries(String id, ArrayList<SimilarityMetric> metrics, ArrayList<PairOfNodes> pairsOfNodes, FTSNS s, Frame predictionFrame) throws IOException{
		ArrayList<BufferedWriter> writersSeries = new ArrayList<BufferedWriter>();
		for(SimilarityMetric metric : metrics){
			writersSeries.add(new BufferedWriter(new FileWriter(Paths.assembleSeriesPath(id,metric.getName()))));
		}

		BufferedWriter writerClasses = new BufferedWriter(new FileWriter(Paths.assembleSeriesPath(id,"classes")));

		for(PairOfNodes pair : pairsOfNodes){
			int v1 = pair.getNode1();
			int v2 = pair.getNode2();
			int classAttribute = predictionFrame.getContent().isNeighbor(v1, v2) ? 1 : 0;

			for(int i = 0; i < metrics.size(); i++){
				for(Frame frame : s.getFrames()){
					SimilarityMetric metric = metrics.get(i);
					metric.init(frame.getContent());
					double score = metric.getScore(v1, v2);
					writersSeries.get(i).write(score + "\t");					
				}				
			}			
			writerClasses.write(classAttribute + "\n");						
		}

		writerClasses.flush();
		writerClasses.close();
		for(BufferedWriter writerSeries : writersSeries){
			writerSeries.flush();
			writerSeries.close();
		}
	}	

	private static void writeWekaHeaderInScoreFile(String id, String method, BufferedWriter writer, ArrayList<SimilarityMetric> metrics) throws IOException{
		String relation = id + "-" + method; 
		ArrayList<String> attributes = new ArrayList<String>();
		for(SimilarityMetric metric : metrics){
			attributes.add(metric.getName());
		}
		WekaUtils.writeWekaHeader(writer, relation, attributes);
	}
	
	private static void writeWekaHeaderInHybridScoreFile(String id, String method, BufferedWriter writer, ArrayList<SimilarityMetric> metrics) throws IOException{
		String relation = id + "-" + method; 
		ArrayList<String> attributes = new ArrayList<String>();
		for(SimilarityMetric metric : metrics){
			attributes.add(metric.getName());
		}
		for(SimilarityMetric metric : metrics){
			attributes.add(metric.getName() + "-traditional");
		}
		WekaUtils.writeWekaHeader(writer, relation, attributes);
	}

	public static void saveTraditionalScores(String id, boolean wekaFormat, ArrayList<SimilarityMetric> metrics, ArrayList<PairOfNodes> pairsOfNodes, FTSNS s, Frame predictionFrame) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.assembleScoresPath(id, "traditional", wekaFormat)));
		if(wekaFormat){
			writeWekaHeaderInScoreFile(id, "traditional", writer, metrics);
		}

		for(PairOfNodes pair : pairsOfNodes){
			int v1 = pair.getNode1();
			int v2 = pair.getNode2();

			for(SimilarityMetric metric : metrics){
				metric.init(s.getWholeNetwork());
				double score = metric.getScore(v1, v2);
				writer.write(score + "\t");
			}
			int classAttribute = predictionFrame.getContent().isNeighbor(v1, v2) ? 1 : 0;
			writer.write(classAttribute + "\n");					
		}

		writer.flush();
		writer.close();
	}	

	private static ArrayList<ArrayList<TimeSeries>> readSeries(String id, ArrayList<SimilarityMetric> metrics) throws IOException{
		//Array com as métricas e dentro de cada uma as séries geradas pelos pares de nós.
		ArrayList<ArrayList<TimeSeries>> timeSeries = new ArrayList<ArrayList<TimeSeries>>();

		ArrayList<BufferedReader> readers = new ArrayList<BufferedReader>();
		for(SimilarityMetric metric : metrics){
			readers.add(new BufferedReader(new FileReader(Paths.assembleSeriesPath(id, metric.getName()))));
			timeSeries.add(new ArrayList<TimeSeries>());
		}
		BufferedReader readerClasses = new BufferedReader(new FileReader(Paths.assembleSeriesPath(id, "classes")));

		String line;
		int i = 0;
		while((line = readers.get(i).readLine()) != null){
			double[] values = Formarter.stringToDoubleArray(line,"\t");
			int classAttribute = Integer.parseInt(readerClasses.readLine());
			timeSeries.get(i).add(new TimeSeries(values,classAttribute));
			i++;
			if(i >= readers.size()){
				i = 0;
			}
		}		
		return timeSeries;		
	}

	public static void savePredictedScores(String id, boolean wekaFormat, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters) throws IOException{
		ArrayList<BufferedWriter> writers = new ArrayList<BufferedWriter>();
		for(Forecaster forecaster : forecasters){
			BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.assembleScoresPath(id, forecaster.getDescription(), wekaFormat)));
			if(wekaFormat){
				writeWekaHeaderInScoreFile(id, forecaster.getDescription(), writer, metrics);
			}
			writers.add(writer);
		}

		ArrayList<ArrayList<TimeSeries>> timeSeries = readSeries(id, metrics);
		int numberOfSeries = timeSeries.get(0).size();

		for(int i = 0; i < forecasters.size(); i++){
			for(int j = 0; j < numberOfSeries; j++){
				int classAttribute = 0;
				for(int k = 0; k < metrics.size(); k++){
					TimeSeries series = timeSeries.get(k).get(j);
					classAttribute = series.getClassAttribute();
					Forecaster forecaster = forecasters.get(j);
					double score = forecaster.forecast(series.getObservedValues());
					writers.get(j).write(score + "\t");
				}
				writers.get(0).write(classAttribute + "\n");
			}
		}

		for(BufferedWriter writer : writers){
			writer.flush();
			writer.close();
		}		
	}	
	
	//Método exclusivo da técnica supervisionada (hybrid feature vector)
	public static void saveHybridScores(String id, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters) throws IOException{
		ArrayList<BufferedWriter> writers = new ArrayList<BufferedWriter>();
		for(Forecaster forecaster : forecasters){
			BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.assembleScoresPath(id + "-" + HYBRID_METHOD, forecaster.getDescription(), true)));
			writeWekaHeaderInHybridScoreFile(id, forecaster.getDescription(), writer, metrics);
			writers.add(writer);
		}
		
		//Para cada forecaster
		//Ler scores preditos
		//Ler scores tradicionais
		//escrever ambos num arquivo correspondente
		
		for(BufferedWriter writer : writers){
			writer.flush();
			writer.close();
		}		
	}	


}
