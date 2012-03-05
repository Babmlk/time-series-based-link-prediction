package forecasting;

public interface Forecaster {

	public double forecast(double[] series);	
	public String getName();
	public String getDescription();
	
}
