package log;

public class States {

	private String mainState;
	private String currentState;
	private String currentSubStates;
	
	public States(){
		
	}

	public String getMainState() {
		return mainState;
	}

	public void setMainState(String mainState) {
		this.mainState = mainState;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getCurrentSubStates() {
		return currentSubStates;
	}

	public void setCurrentSubStates(String currentSubStates) {
		this.currentSubStates = currentSubStates;
	}
	
	
	
}
