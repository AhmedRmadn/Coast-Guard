package elements;

import java.util.Objects;

import actions.Action;
import actions.Drop;

public class Station extends Element {

	public Station(int x, int y) {
		super(x, y);
	}

	@Override
	public Station copy() {
		return new Station(this.x, this.y);

	}

	@Override
	public Element performeAction(Action action) {
		Station successorStation = this.copy();
		if (action instanceof Drop) {
			((Drop) action).setSuccess(true);
		}
		return successorStation;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		return x == other.x
				&& y == other.y;
	}

	@Override
	public String toString() {
		return "Station";
	}
	
	

}
