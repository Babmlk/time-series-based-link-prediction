package model;

public class ResultsBoard {

	private String[] lineNames;
	private String[] columnNames;
	private double[][] results;
	
	public ResultsBoard(String[] lineNames, String[] columnNames, double[][] results){
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

	public double[][] getResults() {
		return results;
	}
		
}
