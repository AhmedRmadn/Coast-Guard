package actions;

import Main.State;
import elements.Element;

public class Right extends Action {

	public Right(String actionName) {
		super(actionName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public State actionOnState(State s) {
		boolean valid = isValid(s);
		if (!valid) {
			return null;
		}
		int xAgent = s.getxAgent();
		int yAgent = s.getyAgent() + 1;
		Element[][] grid = s.getGrid();
		Element gridNextState[][] = actionOnGrid(s.getGrid(), this);
		int agentCapacity = s.getAgentCapacity();
		int numberOfBlackBoxes = s.getNumberOfBlackBoxes();
		int numberOfDamagedBoxes = s.getNumberOfDamagedBoxes() + this.getNumberOfDamegedBoxes();
		State successorState = new State(gridNextState, agentCapacity, xAgent, yAgent, numberOfBlackBoxes,
				numberOfDamagedBoxes);
		successorState.setActionPerformed(this);
		return successorState;
	}

	@Override
	public boolean isValid(State s) {
		return s.getyAgent() + 1 < s.getGrid()[s.getxAgent()].length;
	}

	@Override
	public String toString() {
		return "Right []";
	}

}
