package elements;

import java.util.Objects;

import actions.Action;
import actions.PickUp;
import actions.Retrieve;

public class Wreck extends Element {
	private final int maxDamage = 19;
	private int damage;

	public Wreck(int x, int y) {
		super(x, y);
		damage = 0;
	}

	@Override
	public Wreck copy() {
		Wreck clonedWreck = new Wreck(this.x, this.y);
		clonedWreck.damage = this.damage;
		return clonedWreck;

	}

	@Override
	public Element performeAction(Action action) {
		Wreck successorWreck = this.copy();
		if (action instanceof Retrieve && ((Retrieve) action).getxAgent()==this.x && ((Retrieve) action).getyAgent()==this.y) {
			((Retrieve) action).setSuccess(true);
			return null;
		} else {
			successorWreck.damage++;
			if (successorWreck.damage == successorWreck.maxDamage) {
				action.setNumberOfDamegedBoxes(action.getNumberOfDamegedBoxes() + 1);
				return null;
			} else {
			
				return successorWreck;
			}

		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(damage, x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wreck other = (Wreck) obj;
		return damage == other.damage && x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "Wreck [damage=" + damage + "]";
	}

	public int getDamage() {
		return damage;
	}
	
	

}
