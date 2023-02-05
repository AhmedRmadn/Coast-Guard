package strategies;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Main.Node;

public class DepthFirstSearch extends Strategy {
	private int p = Integer.MAX_VALUE;

	@Override
	public ArrayList<Node> QPriority(ArrayList<Node> nodes) {
		for (Node n : nodes) {
			p--;
			n.setPriority(p);
		}

		return nodes;
		// TODO Auto-generated method stub

	}

}
