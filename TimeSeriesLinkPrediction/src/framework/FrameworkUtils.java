package framework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import metric.ROC;
import metric.SimilarityMetric;
import model.CrossValidation;
import model.Edge;
import model.FTSNS;
import model.FeatureVector;
import model.Fold;
import model.Frame;
import model.Graph;
import model.PairOfNodes;
import model.ROCPattern;
import model.Result;
import model.ResultsBoard;
import model.TimeSeries;

import org.apache.commons.math.stat.descriptive.moment.Mean;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

import reading.GraphReader;
import weka.WekaUtils;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.instance.SpreadSubsample;
import configuration.Configuration;
import configuration.Paths;
import edu.uci.ics.jung.io.GraphIOException;
import forecasting.Forecaster;
import format.Formater;

public class FrameworkUtils {

	private final static String TRADITIONAL_METHOD = "traditional";
	private final static String HYBRID_METHOD = "hybrid";

	public static ArrayList<Graph> readSnapshots(){
		ArrayList<Graph> graphs = new ArrayList<Graph>();
		try {
			for(int i = Configuration.beginYear; i <= Configuration.endYear; i++){
				graphs.add(GraphReader.lerGrafoML(Paths.assembleGraphsPath(i)));
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graphs;
	}	

	//Adicionar vertices faltantes aos frames
	/*public static ArrayList<Frame> formatFrames(ArrayList<Frame> frames){
		Frame lastFrame = frames.remove(frames.size() - 1);//Prediction frame
		//Todos os vértices dos frames de S
		Collection<Integer> allVertices = new HashSet<Integer>();
		for(Frame frame : frames){
			allVertices.addAll(new HashSet<Integer>(frame.getContent().getVertices()));			
		}
		//Adicionar vértices faltandes aos frames
		frames.add(lastFrame);
		for(Frame frame : frames){
			frame.getContent().complete(allVertices);			
		}
		return frames;
	}*/

	public static ArrayList<Frame> buildFrames(ArrayList<Graph> snapshots){
		LinkedList<Frame> frames = new LinkedList<Frame>();		
		ArrayList<Graph> temp = new ArrayList<Graph>();
		int lastIndex = snapshots.size() - 1;
		int i = lastIndex;

		while(lastIndex - Configuration.windowOfPrediction + 1 >= 0){
			while(i > lastIndex - Configuration.windowOfPrediction){
				temp.add(snapshots.get(i));
				i--;
			}
			frames.addFirst(new Frame(temp));
			temp.clear();
			lastIndex = i;
		}

		return new ArrayList<Frame>(frames);
	}

	public static ArrayList<PairOfNodes> getTestablePairOfNodes(Graph trainingNetwork){
		ArrayList<PairOfNodes> pairsOfNodes = new ArrayList<PairOfNodes>();
		ArrayList<Integer> vertices = new ArrayList<Integer>(trainingNetwork.getVertices());
		for(int i = 0; i < vertices.size(); i++){
			for(int j = i + 1; j < vertices.size(); j++){
				int v1 = vertices.get(i);
				int v2 = vertices.get(j);
				//Só analisar nós a dois passos de distância na rede de treino
				if(!trainingNetwork.isNeighbor(v1, v2) && trainingNetwork.hasCommonNeighbor(v1, v2)){
					pairsOfNodes.add(new PairOfNodes(vertices.get(i), vertices.get(j)));
				}
			}
		}
		return pairsOfNodes;
	}

	public static void saveTimeSeries(String id, ArrayList<SimilarityMetric> metrics, ArrayList<PairOfNodes> pairsOfNodes, FTSNS s, Frame predictionFrame) throws IOException{
		Paths.createSeriesDirectory();
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
				writersSeries.get(i).write("\n");
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
			attributes.add(metric.getName() + "-" + TRADITIONAL_METHOD);
		}
		WekaUtils.writeWekaHeader(writer, relation, attributes);
	}

	public static void saveTraditionalScores(String id, boolean wekaFormat, ArrayList<SimilarityMetric> metrics, ArrayList<PairOfNodes> pairsOfNodes, FTSNS s, Frame predictionFrame) throws IOException{
		Paths.createScoresDirectory();
		BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.assembleScoresPath(id, TRADITIONAL_METHOD, wekaFormat)));
		if(wekaFormat){
			writeWekaHeaderInScoreFile(id, TRADITIONAL_METHOD, writer, metrics);
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
		while((line = readers.get(0).readLine()) != null){
			double[] values = Formater.stringToDoubleArray(line,"\t");
			int classAttribute = Integer.parseInt(readerClasses.readLine());
			timeSeries.get(0).add(new TimeSeries(values,classAttribute));
			for(int i = 1; i < metrics.size(); i++){
				line = readers.get(i).readLine();
				values = Formater.stringToDoubleArray(line,"\t");
				timeSeries.get(i).add(new TimeSeries(values,classAttribute));
			}	
		}

		for(BufferedReader reader : readers){
			reader.close();
		}

		return timeSeries;		
	}

	public static void savePredictedScores(String id, boolean wekaFormat, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters) throws IOException{
		Paths.createScoresDirectory();
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
			Forecaster forecaster = forecasters.get(i);
			System.out.println("Forecasting model: " + forecaster.getDescription());
			for(int j = 0; j < numberOfSeries; j++){
				int classAttribute = 0;
				for(int k = 0; k < metrics.size(); k++){
					TimeSeries series = timeSeries.get(k).get(j);
					classAttribute = series.getClassAttribute();
					double score = forecaster.forecast(series.getObservedValues());
					writers.get(i).write(score + "\t");
				}
				writers.get(i).write(classAttribute + "\n");
			}
		}

		for(BufferedWriter writer : writers){
			writer.flush();
			writer.close();
		}		
	}	

	private static ArrayList<FeatureVector> readScores(String id, String method, boolean wekaFormat) throws IOException{
		//Array com os forecasters e dentro de cada um os scores gerados pelos pares de nós.
		ArrayList<FeatureVector> featureVectors = new ArrayList<FeatureVector>();

		BufferedReader reader = new BufferedReader(new FileReader(Paths.assembleScoresPath(id, method, wekaFormat)));

		String line;
		if(wekaFormat){
			while(!(line = reader.readLine()).equals("@DATA") ){}
		}

		while((line = reader.readLine()) != null){
			double[] values = Formater.stringToDoubleArray(line,"\t");
			int classAttribute = (int) values[values.length - 1];
			values = Arrays.copyOfRange(values, 0, values.length - 1);
			featureVectors.add(new FeatureVector(values,classAttribute));				
		}	

		reader.close();

		return featureVectors;		
	}

	//Método exclusivo da técnica supervisionada (hybrid feature vector)
	public static void saveHybridScores(String id, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters) throws IOException{
		ArrayList<BufferedWriter> writers = new ArrayList<BufferedWriter>();
		for(Forecaster forecaster : forecasters){
			BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.assembleScoresPath(id + "-" + HYBRID_METHOD, forecaster.getDescription(), true)));
			writeWekaHeaderInHybridScoreFile(id, forecaster.getDescription(), writer, metrics);
			writers.add(writer);
		}

		ArrayList<FeatureVector> traditionalFeatureVectors = readScores(id, TRADITIONAL_METHOD, true);

		for(int i = 0; i < forecasters.size(); i++){
			Forecaster forecaster = forecasters.get(i);
			ArrayList<FeatureVector> predictedFeatureVectors = readScores(id, forecaster.getDescription(), true);
			for(int j = 0; j < predictedFeatureVectors.size(); j++){
				FeatureVector pFeatureVector = predictedFeatureVectors.get(j);
				FeatureVector tFeatureVector = traditionalFeatureVectors.get(j);
				String pScores = Formater.doubleArrayToString(pFeatureVector.getScores(), "\t");
				String tScores = Formater.doubleArrayToString(tFeatureVector.getScores(), "\t");
				int classAttribute = pFeatureVector.getClassAttribute();
				writers.get(i).write(pScores + "\t" + tScores + "\t" + classAttribute + "\n");
			}

		}

		for(BufferedWriter writer : writers){
			writer.flush();
			writer.close();
		}		
	}	

	private static Result wekaRelativeResult(Classifier classifier, Instances trainingData, Instances validationData, int folds) throws Exception{
		Random random = new Random(); 
		trainingData = new Instances(trainingData); 
		trainingData.randomize(random); 

		validationData = new Instances(validationData);  
		validationData.randomize(random);

		trainingData.stratify(folds);
		validationData.stratify(folds);

		double[] rates = new double[folds];

		for (int i = 0; i < folds; i++) {
			Instances trainingDataTemp = trainingData.trainCV(folds, i);			
			classifier.buildClassifier(trainingDataTemp);

			Instances validationDataTemp = validationData.testCV(folds, i);				

			Evaluation eval = new Evaluation(trainingDataTemp);
			eval.evaluateModel(classifier, validationDataTemp);
			rates[i] = eval.areaUnderROC(1);
		}

		Mean mean = new Mean();
		StandardDeviation sd = new StandardDeviation();

		return new Result(mean.evaluate(rates), sd.evaluate(rates));		
	}

	private static Result wekaAbsoluteResult(Classifier classifier, Instances trainingData, Instances validationData) throws Exception{
		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(classifier, validationData);
		return new Result(eval.areaUnderROC(1),0);
	}

	private static Result wekaSVM(String trainingFilename, String validationFilename, int folds) throws Exception{
		DataSource source = new DataSource(trainingFilename);
		Instances trainingData = source.getDataSet();
		if (trainingData.classIndex() == -1){
			trainingData.setClassIndex(trainingData.numAttributes() - 1);
		}

		source = new DataSource(validationFilename);
		Instances validationData = source.getDataSet();
		if (validationData.classIndex() == -1){
			validationData.setClassIndex(validationData.numAttributes() - 1);
		}

		SpreadSubsample ss = new SpreadSubsample();
		ss.setDistributionSpread(1);
		ss.setMaxCount(0);
		ss.setRandomSeed(1);

		//Undersampling nos dados de teste
		ss.setInputFormat(trainingData);
		trainingData = Filter.useFilter(trainingData,ss);

		SMO smo = new SMO();
		smo.setBuildLogisticModels(true);

		Result result = null;

		if(folds > 1){
			result = wekaRelativeResult(smo, trainingData, validationData, folds);
		}else{
			smo.buildClassifier(trainingData);
			result = wekaAbsoluteResult(smo, trainingData, validationData);
		}		

		return result;
	} 

	public static ResultsBoard resultsForSupervisedLearning(String trainingId, String validationId, ArrayList<Forecaster> forecasters, int folds) throws Exception{
		String[] lineNames = {"fv","h-fv"};
		String[] columnNames = new String[forecasters.size() + 1];
		columnNames[forecasters.size()] = "Trad";
		String[] methodsIds = {"","-" + HYBRID_METHOD} ;

		Result[][] results = new Result[methodsIds.length][columnNames.length];		
		String trainingFilename;
		String validationFilename;

		for(int i = 0; i < methodsIds.length; i++){
			for(int j = 0; j < forecasters.size(); j++){
				Forecaster forecaster = forecasters.get(j);
				columnNames[j] = forecaster.getDescription();

				trainingFilename = Paths.assembleScoresPath(trainingId + methodsIds[i], forecaster.getDescription(), true);
				validationFilename = Paths.assembleScoresPath(validationId + methodsIds[i], forecaster.getDescription(), true);
				results[i][j] = wekaSVM(trainingFilename, validationFilename, folds);
			}
		}

		//Resultado gerado pela abordagem tradicional
		trainingFilename = Paths.assembleScoresPath(trainingId, TRADITIONAL_METHOD, true);
		validationFilename = Paths.assembleScoresPath(validationId, TRADITIONAL_METHOD, true);
		results[0][columnNames.length - 1] = wekaSVM(trainingFilename, validationFilename, folds);

		return new ResultsBoard(lineNames, columnNames, results);
	}

	private static Result rankingClassification(ArrayList<ROCPattern> instances, int folds){
		ROC roc = new ROC();
		CrossValidation cv = new CrossValidation();
		ArrayList<Fold> foldsData = cv.getFolds(instances, folds);

		double[] rates = new double[folds];
		//System.out.println("Folds = " + folds);

		for (int i = 0; i < folds; i++) {
			Fold fold = foldsData.get(i);
			rates[i] = roc.auc(fold.getInstances(), fold.getPositiveClassCounter(), fold.getNegativeClassCounter());
		}

		Mean mean = new Mean();
		StandardDeviation sd = new StandardDeviation();

		return new Result(mean.evaluate(rates), sd.evaluate(rates));

	}

	public static ResultsBoard resultsForUnsupervisedLearning(String id, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters, int folds) throws Exception{
		String[] lineNames = new String[metrics.size()];
		String[] columnNames = new String[forecasters.size() + 1];
		columnNames[forecasters.size()] = TRADITIONAL_METHOD;
		Result[][] results = new Result[lineNames.length][columnNames.length];


		for(int i = 0; i < forecasters.size() + 1; i++){
			System.out.println(i);
			ArrayList<FeatureVector> scores = null;
			if(i < forecasters.size()){
				Forecaster forecaster = forecasters.get(i);
				columnNames[i] = forecaster.getDescription();
				scores = readScores(id, forecaster.getDescription(), false);
			}else{
				scores = readScores(id, TRADITIONAL_METHOD, false);
			}
			for(int j = 0; j < metrics.size(); j++){
				long ini = System.currentTimeMillis();
				SimilarityMetric metric = metrics.get(j);
				lineNames[j] = metric.getName();
				ArrayList<ROCPattern> instances = new ArrayList<ROCPattern>();
				for(FeatureVector featureVector : scores){
					instances.add(new ROCPattern(featureVector.getScores()[j], featureVector.getClassAttribute()));
				}
				results[j][i] = rankingClassification(instances, folds);
				System.out.println("Total time per metric: " + (System.currentTimeMillis() - ini)*1000 + " seconds.");
			}			
		}	

		return new ResultsBoard(lineNames, columnNames, results);

	}

	public static void experiment(FTSNS s, ArrayList<PairOfNodes> pairs, Frame predictionFrame){
		ArrayList<Integer> vertices = new ArrayList<Integer>(s.getWholeNetwork().getVertices());

		double[] inserts = {1};
		double[] removals = {-4,-2,-1,-0.5,0};
		double[] conservations = {0,0.5, 1, 2,4};
		double alpha = 0.1;

		for(int in = 0; in < inserts.length; in++){
			for(int rem = 0; rem < removals.length; rem++){
				for(int con = 0; con < conservations.length; con++){
					Graph g = new Graph();
					g.addVertices(vertices);
					int f = 0;
					//System.out.println("i = " + inserts[in] + "; r = " + removals[rem] + " c = " + conservations[con]);
					ArrayList<Frame> frames = new ArrayList<Frame>(s.getFrames());
					Frame prev = frames.remove(0);
					while(!frames.isEmpty()){
						f++;
						Frame current = frames.remove(0);
						for(int i = 0; i < vertices.size(); i++){
							for(int j = i + 1; j < vertices.size(); j++){
								int v1 = vertices.get(i);
								int v2 = vertices.get(j);
								boolean wereNeighbors = false;
								boolean areNeighbors = false;
								if(prev.getContent().containsVertex(v1) &&
										prev.getContent().containsVertex(v2)){
									wereNeighbors = prev.getContent().isNeighbor(v1, v2);
								}
								if(current.getContent().containsVertex(v1) &&
										current.getContent().containsVertex(v2)){
									areNeighbors = current.getContent().isNeighbor(v1, v2);
								}
								double score = 0;
								Random r = new Random();

								if(!wereNeighbors && areNeighbors){
									//Nova aresta
									score = inserts[in];
								}else if(wereNeighbors && !areNeighbors){
									//Perdeu aresta
									score = removals[rem];
								}else if(wereNeighbors && areNeighbors){
									//Aresta fixa
									score = conservations[con];
								}
								score = score*(1 + alpha*Math.log(f+1));
								//score = score*(1 + alpha*f);
								
								if(score <= -0.05 || score >= 0.05){
									for(Integer vertex : s.getWholeNetwork().getNeighbors(v1)){
										g.addWeightedEdge(new Edge(vertex, v2, score));
									}
									for(Integer vertex : s.getWholeNetwork().getNeighbors(v2)){
										g.addWeightedEdge(new Edge(vertex, v1, score));
									}
								}
							}
						}
						prev = current;
					}

					ArrayList<ROCPattern> instances = new ArrayList<ROCPattern>();
					for(PairOfNodes pair : pairs){
						Edge edge = g.findEdge(pair.getNode1(), pair.getNode2());
						double score = 0;
						if(edge != null){
							score = edge.getWeight();
						}
						int classAttribute = 0;
						if(predictionFrame.getContent().containsVertex(pair.getNode1()) &&
								predictionFrame.getContent().containsVertex(pair.getNode2())){
							classAttribute = predictionFrame.getContent().isNeighbor(pair.getNode1(), pair.getNode2()) ? 1 : 0;
						}
						instances.add(new ROCPattern(score, classAttribute));
					}
					Result result = rankingClassification(instances, 1);
					System.out.println(result.getMeanRate());
				}
			}
		}
		System.out.println("Fim");
	}

}
