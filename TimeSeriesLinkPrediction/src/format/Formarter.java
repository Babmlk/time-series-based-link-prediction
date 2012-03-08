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
	
}

	