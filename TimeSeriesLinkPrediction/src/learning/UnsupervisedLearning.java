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

public class UnsupervisedLearning extends LearningMethod{
	
	public final static String FULL_NAME = "Unsupervised Learning";
	private final static String ID = "unsupervised";

	private FTSNS s;
	private Frame predictionFrame;
	private ArrayList<PairOfNodes> pairsOfNodes;
	
	public UnsupervisedLearning(){
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
		this.predictionFrame = temp.remove(temp.size() - 1);
		this.s = new FTSNS(temp);
		this.pairsOfNodes = FrameworkUtils.getTestablePairOfNodes(this.s.getWholeNetwork());
		System.out.println(this.pairsOfNodes.size() + " pairs of nodes to test.");
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
	
	public void computeAbsoluteResults() throws Exception{
		this.absoluteResults =  FrameworkUtils.resultsForUnsupervisedLearning(ID, this.metrics, this.forecasters, 1);
	} 
	
	public void computeRelativeResults(int folds) throws Exception{
		this.relativeResults = FrameworkUtils.resultsForUnsupervisedLearning(ID, this.metrics, this.forecasters, folds);
	}
	
	public String getFullName(){
		return FULL_NAME;
	}
	
	private void defaultResultsBoard(){
		String[] lineNames = {"PA", "CN", "AA", "JC"};
		String[] columnNames = {"RW", "MA(2)", "Av", "LR", "LES", "SES", "Traditional"};
		Result[][] results = {{new Result(0.5463344545,0.23652),null,null,null,null,null,null},
				{null,null,null,null,null,null,null},
				{null,null,null,new Result(0,0),null,null,null},
				{null,null,null,null,null,null,null}};
		this.absoluteResults = new ResultsBoard(lineNames, columnNames, results);
		this.relativeResults = new ResultsBoard(lineNames, columnNames, results);
	}
	
	
}
