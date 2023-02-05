package strategies;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Main.Node;
import actions.Action;

public abstract class Strategy {
	private static String[] strategies = { "BF", "DF", "ID", "GR1", "GR2", "AS1", "AS2" };

	public abstract ArrayList<Node> QPriority(ArrayList<Node> nodes);

	public static Strategy getInstanceOfStrategy(String strategyName) {
		if (strategyName == "BF")
			return new BreadthFirstSearch();
		else if (strategyName == "DF")
			return new DepthFirstSearch();
		else if (strategyName == "GR1")
			return new GreedySearch(1);
		else if (strategyName == "GR2")
			return new GreedySearch(2);
		else if (strategyName == "AS1")
			return new AStarSearch(1);
		else if (strategyName == "AS2")
			return new AStarSearch(2);
		else if (strategyName == "ID")
			return new IterativeDeepeningSearch(1);
		else
			return null;
	}

}
