package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class CrossValidation {

	private ArrayList<ROCPattern> pInstances;
	private ArrayList<ROCPattern> nInstances;
	private int pCount;
	private int nCount;
	
	public CrossValidation(){
		
	}
	
	private void init(ArrayList<ROCPattern> instances){
		this.pCount = 0;
		this.nCount = 0;
		this.pInstances = new ArrayList<ROCPattern>();
		this.nInstances = new ArrayList<ROCPattern>();
		for(ROCPattern instance : instances){
			if(instance.getClassAttribute() == ROCPattern.POSITIVO){
				this.pCount++;
				this.pInstances.add(instance);
			}else{
				this.nCount++;
				this.nInstances.add(instance);
			}			
		}		
	}
	
	private void randomize(){
		Random r = new Random();
		ArrayList<ROCPattern> pRandInstances = new ArrayList<ROCPattern>();
		ArrayList<ROCPattern> nRandInstances = new ArrayList<ROCPattern>();
		while(!this.pInstances.isEmpty()){
			ROCPattern temp = this.pInstances.remove(r.nextInt(this.pInstances.size()));
			pRandInstances.add(temp);
		}
		
		while(!this.nInstances.isEmpty()){
			ROCPattern temp = this.nInstances.remove(r.nextInt(this.nInstances.size()));
			nRandInstances.add(temp);
		}
		this.pInstances = pRandInstances;
		this.nInstances = nRandInstances;
	}
	
	public ArrayList<Fold> getFolds(ArrayList<ROCPattern> instances, int k){
		ArrayList<Fold> folds = new ArrayList<Fold>();
		this.init(instances);
		int positiveClassByFold, negativeClassByFold;
		int totalPositive = 0, totalNegative = 0;
		if(k > 1){
			this.randomize();
			for(int i = 0; i < k; i++){
				ArrayList<ROCPattern> foldInstances = new ArrayList<ROCPattern>();
				if(i < k-1){
					positiveClassByFold = this.pCount/k;
					negativeClassByFold = this.nCount/k;
					totalPositive += positiveClassByFold;
					totalNegative += negativeClassByFold;
				}else{
					positiveClassByFold = this.pCount - totalPositive;
					negativeClassByFold = this.nCount - totalNegative;
				}
				
				for(int j = 0; j < positiveClassByFold; j++){
					foldInstances.add(this.pInstances.remove(0));
				}
				for(int j = 0; j < negativeClassByFold; j++){
					foldInstances.add(this.nInstances.remove(0));
				}
				
				Collections.sort(foldInstances, new Comparator<ROCPattern>() {  
					public int compare(ROCPattern p1, ROCPattern p2) {  
						return (p1.compareTo(p2));  
					}  
				}); 
				
				
				folds.add(new Fold(foldInstances, positiveClassByFold, negativeClassByFold));
			}
		}else{
			ArrayList<ROCPattern> foldInstances = new ArrayList<ROCPattern>();
			foldInstances.addAll(this.pInstances);
			foldInstances.addAll(this.nInstances);
			
			Collections.sort(foldInstances, new Comparator<ROCPattern>() {  
				public int compare(ROCPattern p1, ROCPattern p2) {  
					return (p1.compareTo(p2));  
				}  
			}); 
			
			folds.add(new Fold(foldInstances, this.pCount, this.nCount));
		}
		return folds;		
	}	
	
	
	
}
