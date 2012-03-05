package forecasting;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.DoubleExponentialSmoothingModel;

public class SimpleExponentialSmoothing implements Forecaster{
	
	public final static String NAME = "SES";
	
	public SimpleExponentialSmoothing(){
		
	}

	public double forecast(double[] series){
		DataSet dataSet = new DataSet();
		DataPoint dataPoint;
		for(int i = 0; i < series.length; i++){
			dataPoint = new Observation(series[i]);
			dataPoint.setIndependentValue( "t", i );		
			dataSet.add(dataPoint);
		}	
		
		DoubleExponentialSmoothingModel es = DoubleExponentialSmoothingModel.getBestFitModel(dataSet);
		
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
}
