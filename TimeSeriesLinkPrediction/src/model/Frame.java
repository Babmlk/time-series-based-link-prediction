package model;

import java.util.ArrayList;

public class Frame {

	private Graph content;
	
	public Frame(ArrayList<Graph> snapshots){
		this.content = this.uniteSnapshots(snapshots);
	}
	
	private Graph uniteSnapshots(ArrayList<Graph> snapshots){
		return Graph.union(snapshots);
	}
	
	public Graph getContent(){
		return content;
	}
	
}
