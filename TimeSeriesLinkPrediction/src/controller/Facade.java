package controller;

import java.util.ArrayList;

import learning.LearningMethod;
import learning.UnsupervisedLearning;
import log.States;
import metric.SimilarityMetric;
import model.Frame;
import configuration.Configuration;
import forecasting.Forecaster;
import framework.FrameworkUtils;

public class Facade {
	
	private ArrayList<SimilarityMetric> metrics;
	private ArrayList<Forecaster> forecasters;
	private ArrayList<LearningMethod> learningMethods;
	private boolean statistics;
	private int folds;
	private States states;
	
	public Facade(){
		this.states = new States();
	}

	public ArrayList<LearningMethod> getLearningMethods() {
		return learningMethods;
	}

	public boolean isStatistics() {
		return statistics;
	}
	
	public States getStates(){
		return states;
	}

	public void setDataBaseInfo(String category, int beginYear, int endYear){
		Configuration.category = category;
		Configuration.beginYear = beginYear;
		Configuration.endYear = endYear;
	}
	
	public void setFrameworkSettings(int windowOfPrediction, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters){
		Configuration.windowOfPrediction = windowOfPrediction;
		this.metrics = metrics;
		this.forecasters = forecasters;
	}
	
	public void setLearningMethods(ArrayList<LearningMethod> learningMethods, boolean statistics, int folds){
		this.learningMethods = learningMethods;
		this.statistics = statistics;
		this.folds = folds;
	}
	
	public void go() throws Exception{
		System.out.println("Building Frames...");
		ArrayList<Frame> frames = FrameworkUtils.buildFrames(FrameworkUtils.readSnapshots());
		System.out.println(frames.size() + " frames.");
		for(int i = 0; i < this.learningMethods.size(); i++){
			LearningMethod learning = this.learningMethods.get(i);
			
			System.out.println("Initializing learning method...");
			learning.init(frames, this.metrics, this.forecasters);
			
			//this.states.setMainState("Generating time series...");
			System.out.println("Generating time series...");
			//learning.saveTimeSeries();
			
			//this.states.setMainState("Calculating traditional scores...");
			System.out.println("Calculating traditional scores...");
			//learning.saveTraditionalScores();
			
			//this.states.setMainState("Calculating predicted scores...");
			System.out.println("Calculating predicted scores...");
			//learning.savePredictedScores();
			
			//this.states.setMainState("Computing absolute results...");
			System.out.println("Computing absolute results...");
			//learning.computeAbsoluteResults();
			
			if(this.statistics){
				//this.states.setMainState("Computing relative results...");
				System.out.println("Computing relative results...");
				//learning.computeRelativeResults(this.folds);
			}
			
			if(learning instanceof UnsupervisedLearning){
				((UnsupervisedLearning) learning).teste();
			}
		}
	}	
}
