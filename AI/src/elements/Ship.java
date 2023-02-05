package elements;

import java.util.Objects;

import actions.Action;
import actions.PickUp;
import actions.Retrieve;

public class Ship extends Element {
	private int numberOfPassengers;
	private int alivePassengers;
	private int deadPassengers;

	public Ship(int numberOfPassengers, int x, int y) {
		super(x, y);
		this.numberOfPassengers = numberOfPassengers;
		this.alivePassengers = numberOfPassengers;
		this.deadPassengers = 0;

	}

	@Override
	public Ship copy() {
		Ship clonedShip = new Ship(this.numberOfPassengers, this.x, this.y);
		clonedShip.alivePassengers = this.alivePassengers;
		clonedShip.deadPassengers = this.deadPassengers;
		return clonedShip;
	}

	@Override
	public Element performeAction(Action action) {
		Ship successorShip = this.copy();
		if (action instanceof PickUp && ((PickUp) action).getxAgent() == this.x
				&& ((PickUp) action).getyAgent() == this.y) {
			int agentCapacity = ((PickUp) action).getAgentCapacity();
			int rescued = Math.min(agentCapacity, this.alivePassengers);
			((PickUp) action).setNumberOfPassengersRescued(rescued);
			((PickUp) action).setSuccess(true);
			successorShip.alivePassengers -= rescued;
		}
		if (successorShip.alivePassengers > 0) {
			successorShip.alivePassengers -= 1;
			successorShip.deadPassengers += 1;
			action.setNumberOfDeadPassengers(action.getNumberOfDeadPassengers() + 1);
		}
		if (successorShip.alivePassengers == 0) {
			return new Wreck(successorShip.x, successorShip.y);
		} else {
			return successorShip;
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(alivePassengers, deadPassengers, x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ship other = (Ship) obj;
		return alivePassengers == other.alivePassengers && deadPassengers == other.deadPassengers && x == other.x
				&& y == other.y;
	}



	@Override
	public String toString() {
		return "Ship [alivePassengers=" + alivePassengers + "]";
	}

	public int getAlivePassengers() {
		return alivePassengers;
	}

	public int getDeadPassengers() {
		return deadPassengers;
	}

}
