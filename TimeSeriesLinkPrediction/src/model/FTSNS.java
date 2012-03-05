package model;

import java.util.ArrayList;

public class FTSNS {
	//Framed Time-Sliced Network Structure

	private ArrayList<Frame> frames;
	private Graph wholeNetwork;
	
	public FTSNS(ArrayList<Frame> frames){
		this.frames = frames;
		this.wholeNetwork = this.uniteFrames(frames);
	}
	
	private Graph uniteFrames(ArrayList<Frame> frames){
		ArrayList<Graph> contents = new ArrayList<Graph>();
		for(Frame frame : frames){
			contents.add(frame.getContent());
		}
		return Graph.union(contents);
	}
	
	public ArrayList<Frame> getFrames(){
		return frames;
	}
	
	public Graph getWholeNetwork(){
		return wholeNetwork;
	}
	
}
