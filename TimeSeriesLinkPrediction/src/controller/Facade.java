package controller;

import learning.LearningMethod;
import metric.SimilarityMetric;
import model.Frame;
import model.ResultsBoard;
import configuration.Configuration;
import forecasting.Forecaster;
import framework.FrameworkUtils;

public class Facade {
	
	private SimilarityMetric[] metrics;
	private Forecaster[] forecasters;
	private LearningMethod[] learningMethods;
	private boolean statistics;
	private int folds;

	public void setDataBaseInfo(String category, int beginYear, int endYear){
		Configuration.category = category;
		Configuration.beginYear = beginYear;
		Configuration.endYear = endYear;
	}
	
	public void setFrameworkSettings(int windowOfPrediction, SimilarityMetric[] metrics, Forecaster[] forecasters){
		Configuration.windowOfPrediction = windowOfPrediction;
		this.metrics = metrics;
		this.forecasters = forecasters;
	}
	
	public void setLearningMethods(LearningMethod[] learningMethods, boolean statistics, int folds){
		this.learningMethods = learningMethods;
		this.statistics = statistics;
		this.folds = folds;
	}
	
	public void go() throws Exception{
		ResultsBoard[] absoluteResults, relativeResults;
		absoluteResults = new ResultsBoard[this.learningMethods.length];
		relativeResults = null;
		if(this.statistics){
			relativeResults = new ResultsBoard[this.learningMethods.length];
		}
		Frame[] frames = FrameworkUtils.buildFrames(FrameworkUtils.readSnapshots());
		for(int i = 0; i < this.learningMethods.length; i++){
			LearningMethod learning = this.learningMethods[i];
			learning.init(frames, this.metrics, this.forecasters);
			learning.saveTimeSeries();
			learning.saveTraditionalScores();
			learning.savePredictedScores();
			absoluteResults[i] = learning.computeAbsoluteResults();
			if(this.statistics){
				relativeResults[i] = learning.computeRelativeResults(this.folds);
			}
		}
	}
	
	
	
}
