package model;

import java.util.ArrayList;

public class Fold {

	private ArrayList<ROCPattern> instances;
	private int pCount;
	private int nCount;
	
	public Fold(ArrayList<ROCPattern> instances, int pCount, int nCount){
		this.instances = instances;
		this.pCount = pCount;
		this.nCount = nCount;
	}

	public ArrayList<ROCPattern> getInstances() {
		return instances;
	}

	public int getPositiveClassCounter() {
		return pCount;
	}

	public int getNegativeClassCounter() {
		return nCount;
	}
			
}
