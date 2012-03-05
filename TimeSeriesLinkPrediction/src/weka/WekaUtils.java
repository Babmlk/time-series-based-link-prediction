package weka;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WekaUtils {

	public static void writeWekaHeader(BufferedWriter writer, String relation, ArrayList<String> attributes) throws IOException{
		writer.write("@RELATION " + relation + "\n");
		for(String attribute : attributes){
			writer.write("@ATTRIBUTE " + attribute +  "	REAL\n");
		}
		writer.write("@ATTRIBUTE class 	{0,1}\n");
		writer.write("@DATA\n");
	}
	
	
}
