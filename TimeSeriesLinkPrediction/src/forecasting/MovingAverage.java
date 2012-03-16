package forecasting;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.MovingAverageModel;

public class MovingAverage implements Forecaster{
	
	public final static String NAME = "MA";
	public final static String FULL_NAME = "Moving Average (MA)";
	
	protected int n;
	
	public MovingAverage(int n){
		this.n = n;
	}
	
	public double forecast(double[] series){
		DataSet dataSet = new DataSet();
		DataPoint dataPoint;
		for(int i = 0; i < series.length; i++){
			dataPoint = new Observation(series[i]);
			dataPoint.setIndependentValue( "t", i );		
			dataSet.add(dataPoint);
		}	
		
		MovingAverageModel ma = new MovingAverageModel(this.n);
		ma.init(dataSet);
		
		//Forecast		
		dataPoint = new Observation(0);
		dataPoint.setIndependentValue( "t", series.length );		
		return ma.forecast(dataPoint);	
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getDescription() {
		return NAME + "(" + this.n + ")";
	}		
	
	@Override
	public String getFullName() {
		return FULL_NAME + " [n = " +  n + "]";
	}
	
}
