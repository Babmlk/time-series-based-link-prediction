package model;

import format.Formater;

public class ResultsBoard {

	private String[] lineNames;
	private String[] columnNames;
	private Result[][] results;
	
	public ResultsBoard(String[] lineNames, String[] columnNames, Result[][] results){
		this.lineNames = lineNames;
		this.columnNames = columnNames;
		this.results = results;
	}

	public String[] getLineNames() {
		return lineNames;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public Result[][] getResults() {
		return results;
	}
	
	public int getLinesCount(){
		return this.lineNames.length;
	}
	
	public int getColumnsCount(){
		return this.columnNames.length;
	}
	
	public String getFormatedResult(int i, int j, boolean statistics){
		Result result = this.results[i][j];
		String text;
		if(result == null){
			text = "--";
		}else{
			double mean = this.results[i][j].getMeanRate();
			double sd = this.results[i][j].getSdRate();
			text = statistics ? Formater.doubleToString(mean, "0.0000") + " +/- " + 
			Formater.doubleToString(sd, "0.0000") : Formater.doubleToString(mean, "0.0000");
		}
		return text;
	}
		
}
