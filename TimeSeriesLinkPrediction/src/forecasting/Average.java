package forecasting;

public class Average extends MovingAverage{
	
	public final static String NAME = "Av";
	public final static String FULL_NAME = "Average (Av)";

	public Average(){
		super(0);
	}
	
	@Override
	public double forecast(double[] series){
		super.n = series.length;
		return super.forecast(series);
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getDescription() {
		return NAME;
	}
	
	@Override
	public String getFullName() {
		return FULL_NAME;
	}
	
}
