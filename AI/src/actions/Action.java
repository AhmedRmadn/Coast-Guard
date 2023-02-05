package actions;

import Main.State;
import elements.Element;
import elements.Ship;

public abstract class Action {
	// protected State successorState;
	// private static String[] allActions =
	// {"PickUp","Retrieve","Drop","Up","Down","Left","Right"};
	private static String[] allActions = { "pickup", "retrieve", "drop", "up", "down", "left", "right" };
	protected int numberOfDeadPassengers;
	protected int numberOfDamegedBoxes;
	protected String actionName;
	private int numberOfShips;
	private int allNumberOfAlivePassengers;

	public Action(String actionName) {
		this.actionName = actionName;
		this.numberOfShips = 0;
		this.allNumberOfAlivePassengers = 0;
		numberOfDeadPassengers = 0;
		numberOfDamegedBoxes = 0;
	}

	protected Element[][] actionOnGrid(Element grid[][], Action a) {
		Element gridNextState[][] = new Element[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] != null)
					gridNextState[i][j] = grid[i][j].performeAction(a);
				if (gridNextState[i][j] != null && gridNextState[i][j] instanceof Ship) {
					this.numberOfShips++;
					this.allNumberOfAlivePassengers += ((Ship) gridNextState[i][j]).getAlivePassengers();
				}
			}
		}
		return gridNextState;

	}

	private static Action getInstanceOfAction(String actionName) {
		Action a;
		if (actionName.equals("down")) {
			a = new Down(actionName);

		} else if (actionName.equals("drop")) {
			a = new Drop(actionName);

		} else if (actionName.equals("left")) {
			a = new Left(actionName);

		} else if (actionName.equals("pickup")) {
			a = new PickUp(actionName);

		} else if (actionName.equals("retrieve")) {
			a = new Retrieve(actionName);

		} else if (actionName.equals("right")) {
			a = new Right(actionName);

		} else if (actionName.equals("up")) {
			a = new Up(actionName);

		} else {
			a = null;

		}
		return a;

	}

	public static Action[] actionsFactory() {
		Action[] actions = new Action[Action.allActions.length];
		for (int i = 0; i < actions.length; i++) {
			actions[i] = getInstanceOfAction(Action.allActions[i]);
		}
		return actions;
	}
	

	public abstract State actionOnState(State s);

	public abstract boolean isValid(State s);

	public int getNumberOfDeadPassengers() {
		return numberOfDeadPassengers;
	}

	public void setNumberOfDeadPassengers(int numberOfDeadPassengers) {
		this.numberOfDeadPassengers = numberOfDeadPassengers;
	}

	public int getNumberOfDamegedBoxes() {
		return numberOfDamegedBoxes;
	}

	public void setNumberOfDamegedBoxes(int numberOfDamegedBoxes) {
		this.numberOfDamegedBoxes = numberOfDamegedBoxes;
	}

	public String getActionName() {
		return actionName;
	}

	public int getNumberOfShips() {
		return numberOfShips;
	}

	public int getAllNumberOfAlivePassengers() {
		return allNumberOfAlivePassengers;
	}
	
	

}
