package model;


public class TimeSeries {

	private double[] observedValues;
	private int classAttribute;
		
	public TimeSeries(double[] observedValues, int classAttribute){
		this.observedValues = observedValues;
		this.classAttribute = classAttribute;		
	}

	public double[] getObservedValues() {
		return observedValues;
	}

	public int getClassAttribute() {
		return classAttribute;
	}	
	
}
