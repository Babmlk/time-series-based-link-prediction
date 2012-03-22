package learning;

import java.io.IOException;
import java.util.ArrayList;

import metric.SimilarityMetric;
import model.Frame;
import model.ResultsBoard;
import forecasting.Forecaster;

public abstract class LearningMethod {
	
	protected ArrayList<Frame> frames;
	protected ArrayList<SimilarityMetric> metrics;
	protected ArrayList<Forecaster> forecasters;
	protected ResultsBoard absoluteResults;
	protected ResultsBoard relativeResults;
	
	public ResultsBoard getAbsoluteResults() {
		return absoluteResults;
	}

	public ResultsBoard getRelativeResults() {
		return relativeResults;
	}

	public abstract void init(ArrayList<Frame> frames, ArrayList<SimilarityMetric> metrics, ArrayList<Forecaster> forecasters);
	public abstract void saveTimeSeries() throws IOException;
	public abstract void saveTraditionalScores() throws IOException;
	public abstract void savePredictedScores() throws IOException;
	public abstract void computeAbsoluteResults() throws Exception;	
	public abstract void computeRelativeResults(int folds) throws Exception;
	public abstract String getFullName();
	
}
