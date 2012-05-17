package learning;

import java.io.IOException;
import java.util.ArrayList;

import metric.SimilarityMetric;
import model.FTSNS;
import model.Frame;
import model.PairOfNodes;
import model.Result;
import model.ResultsBoard;
import forecasting.Forecaster;
import framework.FrameworkUtils;

public class SupervisedLearning extends LearningMethod{

	public final static String FULL_NAME = "Supervised Learning";
	private final static String VALIDATION_ID = "supervised-validation";
	private final static String TRAINING_ID = "supervised-training";
	
	
	private FTSNS trainingS;
	private FTSNS validationS;
	private Frame trainingPredictionFrame;
	private Frame validationPredictionFrame;
	private ArrayList<PairOfNodes> trainingPairsOfNodes;
	private ArrayList<PairOfNodes> validationPairsOfNodes;
	
	public SupervisedLearning(){
		this.defaultResultsBoard();
	}
	
	public void init(ArrayList<Frame> frames, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters){
		this.frames = frames;
		this.metrics = metrics;
		this.forecasters = forecasters;
		this.createFrameworkStructures(frames);
	}
	
	private void createFrameworkStructures(ArrayList<Frame> frames){
		ArrayList<Frame> temp = new ArrayList<Frame>(frames);
		this.validationPredictionFrame = temp.remove(temp.size() - 1);
		this.validationS = new FTSNS(new ArrayList<Frame>(temp));
		//this.validationPairsOfNodes = FrameworkUtils.getTestablePairOfNodes(this.validationS.getWholeNetwork());
		//System.out.println(this.validationPairsOfNodes.size() + " pairs of nodes (validation)");
		
		this.trainingPredictionFrame = temp.remove(temp.size() - 1);
		this.trainingS = new FTSNS(temp);
		//this.trainingPairsOfNodes = FrameworkUtils.getTestablePairOfNodes(this.trainingS.getWholeNetwork());
		//System.out.println(this.trainingPairsOfNodes.size() + " pairs of nodes (training)");
	}
	
	public void saveTimeSeries() throws IOException{				
		FrameworkUtils.saveTimeSeries(TRAINING_ID, this.metrics, this.trainingPairsOfNodes, this.trainingS, this.trainingPredictionFrame);
		FrameworkUtils.saveTimeSeries(VALIDATION_ID, this.metrics, this.validationPairsOfNodes, this.validationS, this.validationPredictionFrame);
	}
	
	public void saveTraditionalScores() throws IOException {
		FrameworkUtils.saveTraditionalScores(TRAINING_ID, true, this.metrics, this.trainingPairsOfNodes, this.trainingS, trainingPredictionFrame);
		FrameworkUtils.saveTraditionalScores(VALIDATION_ID, true, this.metrics, this.validationPairsOfNodes, this.validationS, validationPredictionFrame);
	}
	
	public void savePredictedScores() throws IOException{
		FrameworkUtils.savePredictedScores(TRAINING_ID, true, this.metrics, this.forecasters);
		FrameworkUtils.savePredictedScores(VALIDATION_ID, true, this.metrics, this.forecasters);
		this.saveHybridScores();
	}
	
	private void saveHybridScores() throws IOException{
		FrameworkUtils.saveHybridScores(TRAINING_ID, this.metrics, this.forecasters);
		FrameworkUtils.saveHybridScores(VALIDATION_ID, this.metrics, this.forecasters);
	}
	
	public void computeAbsoluteResults() throws Exception{
		this.absoluteResults = FrameworkUtils.resultsForSupervisedLearning(TRAINING_ID, VALIDATION_ID, this.forecasters, 1);
	} 
	
	public void computeRelativeResults(int folds) throws Exception{
		this.relativeResults = FrameworkUtils.resultsForSupervisedLearning(TRAINING_ID, VALIDATION_ID, this.forecasters, folds);
	}
	
	public String getFullName(){
		return FULL_NAME;
	}
	
	private void defaultResultsBoard(){
		String[] lineNames = {"fv", "h-fv"};
		String[] columnNames = {"RW", "MA(2)", "Av", "LR", "LES", "SES", "Traditional"};
		Result[][] results = {{null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null}};
		this.absoluteResults = new ResultsBoard(lineNames, columnNames, results);
		this.relativeResults = new ResultsBoard(lineNames, columnNames, results);
	}
}
