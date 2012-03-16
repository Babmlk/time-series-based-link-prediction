package model;

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
		
}
