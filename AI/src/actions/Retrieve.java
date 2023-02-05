package actions;

import Main.State;
import elements.Element;
import elements.Ship;
import elements.Wreck;

public class Retrieve extends Action {
	public Retrieve(String actionName) {
		super(actionName);
		// TODO Auto-generated constructor stub
	}

	private int xAgent;
	private int yAgent;
	private boolean success = false;

	@Override
	public State actionOnState(State s) {
		boolean valid = isValid(s);
		if (!valid) {

			return null;
		}

		this.xAgent = s.getxAgent();
		this.yAgent = s.getyAgent();
		Element gridNextState[][] = actionOnGrid(s.getGrid(), this);
		int agentCapacity = s.getAgentCapacity();
		int numberOfBlackBoxes = s.getNumberOfBlackBoxes() + 1;
		int numberOfDamagedBoxes = s.getNumberOfDamagedBoxes() + this.getNumberOfDamegedBoxes();
		State successorState = new State(gridNextState, agentCapacity, xAgent, yAgent,
				numberOfBlackBoxes,numberOfDamagedBoxes);
		successorState.setActionPerformed(this);
		return successorState;

	}

	@Override
	public boolean isValid(State s) {
		return s.getGrid()[s.getxAgent()][s.getyAgent()] instanceof Wreck;
	}
	
	

	@Override
	public String toString() {
		return "Retrieve [xAgent=" + xAgent + ", yAgent=" + yAgent + ", success=" + success + "]";
	}

	public int getxAgent() {
		return xAgent;
	}

	public int getyAgent() {
		return yAgent;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}
	
	

}
