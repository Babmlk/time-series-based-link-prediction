package configuration;

import learning.LearningMethod;
import learning.UnsupervisedLearning;
import metric.SimilarityMetric;
import metric.topologic.AdamicAdar;
import metric.topologic.CommonNeighbors;
import metric.topologic.Jaccard;
import metric.topologic.PreferentialAttachment;
import forecasting.Average;
import forecasting.Forecaster;
import forecasting.LinearExponentialSmoothing;
import forecasting.LinearRegression;
import forecasting.MovingAverage;
import forecasting.RandomWalk;
import forecasting.SimpleExponentialSmoothing;



public class Configuration {
	
	public static final String[] categoryListDiscription = {"hep-lat:arXiv","hep-th:arXiv" };
	public static final String[] categoryListId = {"hep-lat","hep-th" };
	public static final SimilarityMetric[] metrics = {new PreferentialAttachment(), new CommonNeighbors(), 
		new AdamicAdar(), new Jaccard()};
	public static final Forecaster[] forecasters = {new RandomWalk(), new MovingAverage(2), new Average(), 
		new LinearRegression(), new LinearExponentialSmoothing(), new SimpleExponentialSmoothing()};
	
	public static String category;
	public static int beginYear;
	public static int endYear;
	public static int windowOfPrediction;
	
}
