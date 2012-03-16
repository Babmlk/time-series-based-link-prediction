package learning;

import java.io.IOException;
import java.util.ArrayList;

import metric.SimilarityMetric;
import model.FTSNS;
import model.Frame;
import model.PairOfNodes;
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
	
	public SupervisedLearning(ArrayList<Frame> frames, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters){
		this.frames = frames;
		this.metrics = metrics;
		this.forecasters = forecasters;
		this.init(frames);
	}
	
	private void init(ArrayList<Frame> frames){
		ArrayList<Frame> temp = new ArrayList<Frame>(frames);
		this.validationPredictionFrame = temp.remove(temp.size() - 1);
		this.validationS = new FTSNS(temp);
		this.validationPairsOfNodes = FrameworkUtils.getTestablePairOfNodes(this.validationS.getWholeNetwork(), this.validationPredictionFrame.getContent());
		
		this.trainingPredictionFrame = temp.remove(temp.size() - 1);
		this.trainingS = new FTSNS(temp);
		this.trainingPairsOfNodes = FrameworkUtils.getTestablePairOfNodes(this.trainingS.getWholeNetwork(), this.trainingPredictionFrame.getContent());
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
	}
	
	public void saveHybridScores() throws IOException{
		FrameworkUtils.saveHybridScores(TRAINING_ID, this.metrics, this.forecasters);
		FrameworkUtils.saveHybridScores(VALIDATION_ID, this.metrics, this.forecasters);
	}
	
	public ResultsBoard computeAbsoluteResults() throws Exception{
		return FrameworkUtils.resultsForSupervisedLearning(TRAINING_ID, VALIDATION_ID, this.forecasters, 1);
	} 
	
	public ResultsBoard computeRelativeResults(int folds) throws Exception{
		return FrameworkUtils.resultsForSupervisedLearning(TRAINING_ID, VALIDATION_ID, this.forecasters, folds);
	}
	
	public String getFullName(){
		return FULL_NAME;
	}
}
