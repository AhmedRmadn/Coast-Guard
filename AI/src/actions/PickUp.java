package actions;

import Main.State;
import elements.Element;
import elements.Ship;

public class PickUp extends Action {
	public PickUp(String actionName) {
		super(actionName);
		// TODO Auto-generated constructor stub
	}

	private int xAgent;
	private int yAgent;
	private int agentCapacity;
	private boolean success = false;
	private int numberOfPassengersRescued;

	@Override
	public State actionOnState(State s) {
		boolean valid = isValid(s);
		if (!valid) {
			return null;
		}
		this.xAgent = s.getxAgent();
		this.yAgent = s.getyAgent();
		this.agentCapacity = s.getAgentCapacity();
		Element gridNextState[][] = actionOnGrid(s.getGrid(), this);
		int agentCapacity = s.getAgentCapacity() - this.numberOfPassengersRescued;
		int numberOfBlackBoxes = s.getNumberOfBlackBoxes();
		int numberOfDamagedBoxes = s.getNumberOfDamagedBoxes() + this.getNumberOfDamegedBoxes();
		State successorState = new State(gridNextState, agentCapacity, xAgent, yAgent, numberOfBlackBoxes,
				numberOfDamagedBoxes);
		successorState.setActionPerformed(this);
		return successorState;
	}

	@Override
	public String toString() {
		return "PickUp [xAgent=" + xAgent + ", yAgent=" + yAgent + ", agentCapacity=" + agentCapacity + ", success="
				+ success + ", numberOfPassengersRescued=" + numberOfPassengersRescued + "]";
	}

	@Override
	public boolean isValid(State s) {
		return s.getAgentCapacity() > 0 && s.getGrid()[s.getxAgent()][s.getyAgent()] instanceof Ship;
	}

	public int getxAgent() {
		return xAgent;
	}

	public int getyAgent() {
		return yAgent;
	}

	public int getAgentCapacity() {
		return agentCapacity;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setNumberOfPassengersRescued(int numberOfPassengersRescued) {
		this.numberOfPassengersRescued = numberOfPassengersRescued;
	}

}
