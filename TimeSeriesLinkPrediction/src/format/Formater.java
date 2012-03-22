package format;

import java.text.DecimalFormat;

import model.ResultsBoard;

public class Formater {

	public static double[] stringToDoubleArray(String text, String delimiter){
		String[] strings = text.split(delimiter); 
		double[] array = new double[strings.length];
		int i = 0;
		for(String string : strings){
			array[i++] = Double.parseDouble(string);
		}
		return array;
	}

	public static String doubleArrayToString(double[] values, String delimiter){
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < values.length; i++){
			buffer.append(values[i]);
			if(i < values.length - 1){
				buffer.append(delimiter);
			}
		}		
		return buffer.toString();
	}

	public static String doubleToString(double value, String mask){
		DecimalFormat fmt = new DecimalFormat(mask);     
		return fmt.format(value).replace(",", ".");  
	}

	public static String tableToLatex(ResultsBoard resultsBoard, boolean statistics){
		StringBuffer buffer = new StringBuffer();
		buffer.append("\\begin{table}\n");
		buffer.append("\\centering\n");
		buffer.append("\\tabcolsep=0.08cm\n");
				
		StringBuffer temp = new StringBuffer();
		for(int i = 0; i < resultsBoard.getColumnsCount() + 1; i++){
			temp.append("c");
		}
		
		buffer.append("\\begin{tabular}{" + temp.toString() + "}\n");
		buffer.append("\\hline\n");
		
		temp = new StringBuffer();
		for(int i = 0; i < resultsBoard.getColumnsCount(); i++){
			temp.append("& " + resultsBoard.getColumnNames()[i]);
			if(i < resultsBoard.getColumnsCount() - 1){
				temp.append(" ");
			}
		}
		
		buffer.append(temp.toString() + "\\\\\n");
		buffer.append("\\hline\n");
		
		for(int i = 0; i < resultsBoard.getLinesCount(); i++){
			temp = new StringBuffer();
			for(int j = 0; j < resultsBoard.getColumnsCount() + 1; j++){
				String text;
				if(j == 0){
					text = resultsBoard.getLineNames()[i];
					temp.append(text);
				}else{
					text = resultsBoard.getFormatedResult(i, j - 1, statistics).replace(" +/- ", "$\\pm$");
					temp.append(" & " + text);
				}
			}
			buffer.append(temp + "\\\\\n");
			buffer.append("\\hline\n");
		}
				
		buffer.append("\\end{tabular}\n");
		buffer.append("\\caption{<caption>}\n");
		buffer.append("\\label{<label>}\n");
		buffer.append("\\end{table}\n");
		
		return buffer.toString();
	}

}

