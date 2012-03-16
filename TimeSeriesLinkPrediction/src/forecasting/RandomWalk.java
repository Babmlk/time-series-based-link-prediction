package forecasting;

public class RandomWalk extends MovingAverage{
	
	public final static String NAME = "RW";
	public final static String FULL_NAME = "Random Walk (RW)";

	public RandomWalk(){
		super(1);
	}
	
	@Override
	public double forecast(double[] series){
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
