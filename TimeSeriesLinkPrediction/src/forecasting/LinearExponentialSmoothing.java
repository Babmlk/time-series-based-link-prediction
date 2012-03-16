package forecasting;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.SimpleExponentialSmoothingModel;

public class LinearExponentialSmoothing implements Forecaster {
	
	public final static String NAME = "LES";
	public final static String FULL_NAME = "Linear Exponential Smoothing (LES)";
	
	public LinearExponentialSmoothing(){
		
	}

	public double forecast(double[] series){
		DataSet dataSet = new DataSet();
		DataPoint dataPoint;
		for(int i = 0; i < series.length; i++){
			dataPoint = new Observation(series[i]);
			dataPoint.setIndependentValue( "t", i );		
			dataSet.add(dataPoint);
		}	
		
		SimpleExponentialSmoothingModel es = SimpleExponentialSmoothingModel.getBestFitModel(dataSet);
		
		//Forecast
		
		dataPoint = new Observation(0);
		dataPoint.setIndependentValue( "t", series.length );		
		return es.forecast(dataPoint);		
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
