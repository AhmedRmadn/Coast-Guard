package strategies;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Main.Node;

public class AStarSearch extends Strategy {
	private int x;

	public AStarSearch(int x) {
		this.x = x;
	}

	@Override
	public ArrayList<Node> QPriority(ArrayList<Node> nodes) {
		for (Node n : nodes) {
			if (x == 1)
				n.setPriority(n.getS().getH1() + n.getCost());
			else if (x == 2)
				n.setPriority(n.getS().getH2() + n.getCost());
			else
				System.out.println("balabezo from AStart");

		}
		return nodes;

	}

}
