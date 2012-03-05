package learning;

import java.util.ArrayList;

import model.FTSNS;
import model.Frame;
import model.PairOfNodes;
import framework.FrameworkUtils;

public class UnsupervisedLearning {

	private FTSNS trainingFTSNS;
	private Frame predictionFrame;
	private ArrayList<PairOfNodes> pairsOfNodes;
	
	public UnsupervisedLearning(ArrayList<Frame> frames){
		this.init(frames);
	}
	
	private void init(ArrayList<Frame> frames){
		ArrayList<Frame> temp = new ArrayList<Frame>(frames);
		this.predictionFrame = temp.remove(temp.size() - 1);
		this.trainingFTSNS = new FTSNS(temp);
		this.pairsOfNodes = FrameworkUtils.getTestablePairOfNodes(this.trainingFTSNS.getWholeNetwork(), this.predictionFrame.getContent());
	}
	
	
	
}
