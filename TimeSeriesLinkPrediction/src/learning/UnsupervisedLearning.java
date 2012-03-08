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

public class UnsupervisedLearning extends LearningMethod{
	
	private final static String ID = "unsupervised";

	private FTSNS s;
	private Frame predictionFrame;
	private ArrayList<PairOfNodes> pairsOfNodes;
	
	public UnsupervisedLearning(ArrayList<Frame> frames, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters){
		this.frames = frames;
		this.metrics = metrics;
		this.forecasters = forecasters;
		this.init(frames);
	}
	
	private void init(ArrayList<Frame> frames){
		ArrayList<Frame> temp = new ArrayList<Frame>(frames);
		this.predictionFrame = temp.remove(temp.size() - 1);
		this.s = new FTSNS(temp);
		this.pairsOfNodes = FrameworkUtils.getTestablePairOfNodes(this.s.getWholeNetwork(), this.predictionFrame.getContent());
	}
	
	public void saveTimeSeries() throws IOException{				
		FrameworkUtils.saveTimeSeries(ID, this.metrics, this.pairsOfNodes, this.s, this.predictionFrame);
	}
	
	public void saveTraditionalScores() throws IOException {
		FrameworkUtils.saveTraditionalScores(ID, false, this.metrics, this.pairsOfNodes, this.s, predictionFrame);
	}
	
	public void savePredictedScores() throws IOException{
		FrameworkUtils.savePredictedScores(ID, false, this.metrics, this.forecasters);
	}
	
	public ResultsBoard computeAbsoluteResults(){
		return null;
	} 
	
	public ResultsBoard computeRelativeResults(int folds){
		return null;
	}
	
	
}
