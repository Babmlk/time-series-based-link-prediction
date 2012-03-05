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

	public ArrayList<Frame> getFrames() {
		return frames;
	}

	public ArrayList<SimilarityMetric> getMetrics() {
		return metrics;
	}

	public ArrayList<Forecaster> getForecasters() {
		return forecasters;
	}

	public abstract void saveTimeSeries() throws IOException;
	public abstract void saveTraditionalScores() throws IOException;
	public abstract void savePredictedScores() throws IOException;
	public abstract ResultsBoard computeAbsoluteResults();	
	public abstract ResultsBoard computeRelativeResults(int folds);
	
}
