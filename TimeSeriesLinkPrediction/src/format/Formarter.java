package format;

public class Formarter {

	public static double[] stringToDoubleArray(String text, String delimiter){
		String[] strings = text.split(delimiter); 
		double[] array = new double[strings.length];
		int i = 0;
		for(String string : strings){
			array[i++] = Double.parseDouble(string);
		}
		return array;
	}
	
}
