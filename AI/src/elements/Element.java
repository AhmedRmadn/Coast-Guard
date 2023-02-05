package elements;

import actions.Action;

public abstract class Element {
	protected int x;
	protected int y;

	public Element(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract Element performeAction(Action action);

	public abstract Element copy();

	public abstract String toString();

}
