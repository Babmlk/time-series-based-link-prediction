package metric;

import java.util.ArrayList;

import model.ROCPattern;

public class ROC {
	
	public ROC(){
		
	}

	private double trapezoidArea(double x1, double x2, double y1, double y2){
		double base = Math.abs(x1 - x2);
		double height = (y1 + y2)/2;
		return base*height;
	}
	
	public double auc(ArrayList<ROCPattern> instances, int pCount, int nCount){
		double tp = 0;
		double fp = 0;
		double tpPrev = 0;
		double fpPrev = 0;
		double area = 0;
		double p = pCount;
		double n = nCount;
		double fi;
		double fprev = Integer.MIN_VALUE;
				
		for(ROCPattern instance : instances){
			fi = instance.getScore();
			if(fi != fprev){
				area = area + this.trapezoidArea(fp/n, fpPrev, tp/p, tpPrev);
				fprev = fi;
				tpPrev = tp/p;
				fpPrev = fp/n;
			}		
			
			if(instance.getClassAttribute() == ROCPattern.POSITIVO){
				tp += 1;
			}else{
				fp += 1;
			}
		}
		area = area + this.trapezoidArea(1, fpPrev, 1, tpPrev);
		return area;
	}	
}
