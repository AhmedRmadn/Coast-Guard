package actions;

import Main.Agent;
import Main.State;
import elements.Element;
import elements.Ship;
import elements.Station;

public class Drop extends Action {
	public Drop(String actionName) {
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
		Element[][] grid = s.getGrid();
		Element gridNextState[][] = actionOnGrid(s.getGrid(), this);
		int agentCapacity = Agent.getFullAgentCapacity();
		int numberOfBlackBoxes = s.getNumberOfBlackBoxes();
		int numberOfDamagedBoxes = s.getNumberOfDamagedBoxes() + this.getNumberOfDamegedBoxes();
		State successorState = new State(gridNextState, agentCapacity, this.xAgent, this.yAgent,
				numberOfBlackBoxes,numberOfDamagedBoxes);
		successorState.setActionPerformed(this);
		return successorState;
	}

	@Override
	public boolean isValid(State s) {
		return s.getAgentCapacity()<Agent.getFullAgentCapacity() &s.getGrid()[s.getxAgent()][s.getyAgent()] instanceof Station;
	}
	
	

	@Override
	public String toString() {
		return "Drop [xAgent=" + xAgent + ", yAgent=" + yAgent + ", success=" + success + "]";
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

}
