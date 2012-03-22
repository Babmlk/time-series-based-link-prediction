package configuration;

import java.io.File;

public class Paths {
	
	private static String graphsPath(){return "graphs/" + Configuration.category + "/";};
	private static String seriesPath(){return "series/" + Configuration.category + "/";};
	private static String scoresPath(){return "scores/" + Configuration.category + "/";};
	
	public static void createSeriesDirectory(){
		(new File(seriesPath())).mkdir();
	}
	
	public static void createScoresDirectory(){
		(new File(scoresPath())).mkdir();		
	}	
	
	public static String assembleGraphsPath(int year){
		return graphsPath() + Configuration.category + "-" + year + ".graphml";
	}
	
	public static String assembleSeriesPath(String id, String metric){
		return seriesPath() + id + "-" + metric + "[w = " + Configuration.windowOfPrediction + "].txt";
	}
	
	public static String assembleScoresPath(String id, String method, boolean wekaFormat){
		String path;
		if(wekaFormat){
			path = scoresPath() + id + "-" + method + "[w = " + Configuration.windowOfPrediction + "].arff";
		}else{
			path = scoresPath() + id + "-" + method + "[w = " + Configuration.windowOfPrediction + "].txt";
		}
		return path;
	}
	
	public static int[] yearsAvaliable(){
		File folder = new File(graphsPath());
	    File[] files = folder.listFiles();
	    int[] years = new int[files.length];
	    int i = 0;

	    for (File file : files) {
	      if (file.isFile()) {
	    	  String filename = file.getName();
	    	  int beginIndex = filename.lastIndexOf("-") + 1;
	    	  int endIndex = filename.lastIndexOf(".");
	    	  years[i++] = Integer.parseInt(filename.substring(beginIndex, endIndex));
	      } 
	    }
	    return years;
	}
			
}
