package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

import actions.Action;
import actions.Retrieve;
import elements.Element;
import elements.Ship;
import elements.Station;
import strategies.BreadthFirstSearch;
import strategies.DepthFirstSearch;
import strategies.IterativeDeepeningSearch;
import strategies.Strategy;

public class Agent {
	private State initState;
	private Node initNode;
	private static int fullAgentCapacity;
	private Strategy strategy;
	private PriorityQueue<Node> nodes;
	private HashSet<State> visited;
	private State[] goalPath;
	private boolean visualize;

	public Agent(String grid, String strategyName, boolean visualize) {
		this.visualize = visualize;
		visited = new HashSet<>();
		generateInitState(grid);
		nodes = new PriorityQueue<>();
		nodes.add(initNode);
		visited.add(initState);
		this.strategy = Strategy.getInstanceOfStrategy(strategyName);
	}

	private void generateInitState(String gridString) {
		int numberOfShips = 0;
		int allNumberOfAlivePassengers = 0;
		String[] lines = gridString.split(";");
		String line0[] = lines[0].split(",");
		int m = Integer.parseInt(line0[1]);
		int n = Integer.parseInt(line0[0]);

		int agentCapacity = Integer.parseInt(lines[1]);
		Agent.fullAgentCapacity = agentCapacity;
		String line2[] = lines[2].split(",");
		int xAgent = Integer.parseInt(line2[0]);
		int yAgent = Integer.parseInt(line2[1]);
		Element[][] initGrid = new Element[m][n];
		String[] stations = lines[3].split(",");
		String[] ships = lines[4].split(",");
		for (int i = 0; i < stations.length; i += 2) {
			int stationX = Integer.parseInt(stations[i]);
			int stationY = Integer.parseInt(stations[i + 1]);
			initGrid[stationX][stationY] = new Station(stationX, stationY);
		}

		for (int i = 0; i < ships.length; i += 3) {
			int xShip = Integer.parseInt(ships[i]);
			int yShip = Integer.parseInt(ships[i + 1]);
			int numberOfPassengers = Integer.parseInt(ships[i + 2]);
			numberOfShips++;
			allNumberOfAlivePassengers += numberOfPassengers;
			initGrid[xShip][yShip] = new Ship(numberOfPassengers, xShip, yShip);

		}
		this.initState = new State(initGrid, agentCapacity, xAgent, yAgent, 0, 0);
		this.initState.setAllNumberOfAlivePassengers(allNumberOfAlivePassengers);
		this.initState.setNumberOfShips(numberOfShips);
		this.initNode = new Node(initState, null, 1);

	}

	private void display(Element[][] grid) {
		for (Element[] x : grid) {
			System.out.println(Arrays.toString(x));
		}
		System.out.println("*************************");
	}

	public String search() {
		int n = 0;
		while (true) {
			if (nodes.isEmpty()) {
				if (strategy instanceof IterativeDeepeningSearch) {
					((IterativeDeepeningSearch) this.strategy).incrementDepth();
					visited = new HashSet<>();
					nodes.add(initNode);
					visited.add(initState);
					continue;
				}
				System.out.println("NO WAY");
				return "";
			}
			Node front = nodes.poll();
			if (front.isGoal()) {
				String ans = finalResult(front, n);
				if (this.visualize) {
					for(State s : goalPath) {
						s.display();
					}
					UIPath ui = new UIPath(goalPath);
				}

				return ans;
			}
			ArrayList<Node> expandedNodes = front.expand();
			expandedNodes = this.strategy.QPriority(expandedNodes);
			n++;
			QING(expandedNodes);

		}

	}

	public String finalResult(Node goal, int n) {
		Stack<String> actions = new Stack<>();
		Stack<State> states = new Stack<>();
		int death = 0;
		int retrieved = 0;
		Node curr = goal;
		while (curr != null) {
			State currState = curr.getS();
			states.push(currState);
			Action currAction = currState.getActionPerformed();
			if (currAction != null) {
				actions.add(currAction.getActionName());
				death += currAction.getNumberOfDeadPassengers();
				if (currAction instanceof Retrieve && ((Retrieve) currAction).isSuccess()) {
					retrieved++;
				}
			}
			curr = curr.getParent();

		}
		goalPath = new State[states.size()];
		for (int i = 0; i < goalPath.length; i++) {
			goalPath[i] = states.pop();
		}

		StringBuilder s = new StringBuilder();
		while (!actions.isEmpty()) {
			s.append(actions.pop());
			if (!actions.isEmpty()) {
				s.append(",");
			}
		}

		return s.toString() + ";" + death + ";" + retrieved + ";" + n;
	}

	public void QING(ArrayList<Node> expandedNodes) {
		for (Node n : expandedNodes) {
			if (!visited.contains(n.getS())) {
				this.nodes.add(n);
				this.visited.add(n.getS());
			}

		}

	}

	public static int getFullAgentCapacity() {
		return fullAgentCapacity;
	}

	public HashSet<State> getVisited() {
		return visited;
	}

}
