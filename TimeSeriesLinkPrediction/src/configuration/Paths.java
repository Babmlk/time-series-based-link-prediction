package configuration;

public class Paths {
	
	private static String graphsPath(){return "graphs/" + Configuration.category + "/";};
	private static String seriesPath(){return "series/" + Configuration.category + "/";};
	private static String scoresPath(){return "scores/" + Configuration.category + "/";};
	
	
	
	public static String assembleGraphsPath(int year){
		return graphsPath() + year + ".graphml";
	}
	
	public static String assembleSeriesPath(String id, String metric){
		return seriesPath() + "-" + id + "-" + metric + "[w = " + Configuration.windowOfPrediction + "].txt";
	}
	
	public static String assembleScoresPath(String id, String method, boolean wekaFormat){
		String path;
		if(wekaFormat){
			path = scoresPath() + "-" + id + "-" + method + "[w = " + Configuration.windowOfPrediction + "].arff";
		}else{
			path = scoresPath() + "-" + id + "-" + method + "[w = " + Configuration.windowOfPrediction + "].txt";
		}
		return path;
	}
			
}
