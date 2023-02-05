package Main;

import java.util.ArrayList;

import actions.Action;

public class Node implements Comparable<Node> {
	private State s;
	private Node parent;
	private int cost;
	private ArrayList<Node> children;
	private int priority = 0;
	private int depth;

	public Node(State s, Node parent, int depth) {
		this.s = s;
		this.parent = parent;
		this.depth = depth;
		if (this.parent == null) {
			this.cost = 0;
		} else {
			this.cost = this.parent.getCost() + this.s.getCost();
		}
		this.s.costNode = this.cost;
	}

	public ArrayList<Node> expand() {
		if (this.children != null)
			return this.children;
		Action[] actions = Action.actionsFactory();
		ArrayList<Node> nodes = new ArrayList<>();
		for (int i = 0; i < actions.length; i++) {
			State successorState = actions[i].actionOnState(this.s);
			if (successorState != null) {
				nodes.add(new Node(successorState, this, this.depth + 1));
			}
		}

		this.children = nodes;
		return nodes;
	}

	public boolean isGoal() {
		return this.s.isGoal();
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Node o) {
		int x = this.priority - o.priority;
		if (x == 0) {
			return this.depth - o.depth;
		}
		return x;
	}

	@Override
	public String toString() {
		return "Node [s=" + s + ", parent=" + parent + "]";
	}

	public State getS() {
		return s;
	}

	public Node getParent() {
		return parent;
	}

	public int getCost() {
		return cost;
	}

	public int getDepth() {
		return depth;
	}
	

}
