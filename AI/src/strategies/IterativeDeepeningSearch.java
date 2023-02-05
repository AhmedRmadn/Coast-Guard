package strategies;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Main.Node;

public class IterativeDeepeningSearch extends Strategy {

	private int currDepth;
	private int p = Integer.MAX_VALUE;

	public IterativeDeepeningSearch(int currDepth) {
		this.currDepth = currDepth;
	}

	@Override
	public ArrayList<Node> QPriority(ArrayList<Node> nodes) {
		ArrayList<Node> res = new ArrayList<>();
		for (Node n : nodes) {
			if (n.getDepth() <= this.currDepth) {
				p--;
				n.setPriority(p);
				res.add(n);
			}
		}
		// TODO Auto-generated method stub
		return res;

	}

	public void incrementDepth() {
		this.currDepth++;
	}

}
