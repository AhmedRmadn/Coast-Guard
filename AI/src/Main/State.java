package Main;

import java.util.Arrays;
import java.util.Objects;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.JLabel;

import actions.Action;
import elements.Element;
import elements.Ship;
import elements.Station;
import elements.Wreck;

public class State {
	private Element[][] grid;
	private int agentCapacity;
	private Action actionPerformed;
	private int xAgent;
	private int yAgent;
	private int numberOfBlackBoxes;
	private int numberOfDamagedBoxes;
	private int numberOfShips;
	private int allNumberOfAlivePassengers;
	private int h1;
	private int h2;
	int costNode;

	public State(Element[][] grid, int agentCapacity, int xAgent, int yAgent, int numberOfBlackBoxes,
			int numberOfDamagedBoxes) {
		this.grid = grid;
		this.agentCapacity = agentCapacity;
		this.xAgent = xAgent;
		this.yAgent = yAgent;
		this.numberOfBlackBoxes = numberOfBlackBoxes;
		this.numberOfDamagedBoxes = numberOfDamagedBoxes;
	}

	public boolean isGoal() {
		boolean f = this.agentCapacity == Agent.getFullAgentCapacity();
		for (int i = 0; i < grid.length && f; i++) {
			for (int j = 0; j < grid[i].length && f; j++) {
				if (grid[i][j] != null && !(grid[i][j] instanceof Station)) {
					f = false;
				}
			}
		}
		return f;
	}

	public int getCost() {
		int numberOfDeadPassengers = this.actionPerformed.getNumberOfDeadPassengers();
		int numberOfDamegedBoxes = this.actionPerformed.getNumberOfDamegedBoxes();
		int x = this.grid.length * this.grid[0].length;
		return numberOfDeadPassengers * x + numberOfDamegedBoxes;

	}

	public String textToUI() {
		String a = "None";
		if (this.actionPerformed != null)
			a = this.actionPerformed.getActionName();

		String s = "Capacity " + this.agentCapacity + "      " + "DamagedBoxes " + this.numberOfDamagedBoxes
				+ "       actionPerformed " + a;
		return s;
	}

	public Object[][][] stateToUI() {
		Object[][][] res = new Object[grid.length][grid[0].length][3];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Object[] x = { 0, false, null };
				if (grid[i][j] != null) {
					if (grid[i][j] instanceof Ship) {
						x[0] = 1;
						x[2] = ((Ship) grid[i][j]).getAlivePassengers() + " " + ((Ship) grid[i][j]).getDeadPassengers();
					} else if (grid[i][j] instanceof Wreck) {
						x[0] = 2;
						x[2] = ((Wreck) grid[i][j]).getDamage() + "";
					} else if (grid[i][j] instanceof Station) {
						x[0] = 3;
					} else {
						x[2] = "balabezo";
					}
				}
				if (xAgent == i && yAgent == j)
					x[1] = true;
				res[i][j] = x;
			}
		}
		return res;

	}

	private void calcH1() {
		int[] alive = new int[this.numberOfShips];
		int k = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] instanceof Ship) {
					alive[k] = ((Ship) grid[i][j]).getAlivePassengers();
					k++;
				}
			}
		}
		Arrays.sort(alive);
		int count = 0;
		int x = 1;
		for (int i = alive.length - 2; i >= 0; i--) {
			count += Math.min(alive[i], x);
			x++;
		}
		this.h1 = this.grid.length * this.grid[0].length * count;
	}

	private void calcH2() {
		int w = this.grid.length * this.grid[0].length;
		int x = 3;
		this.h2 = w * ((this.allNumberOfAlivePassengers / (Agent.getFullAgentCapacity() + x)) * x);
		// this.h2 = 0;
	}

	public Element[][] getGrid() {
		return grid;
	}

	public int getAgentCapacity() {
		return agentCapacity;
	}

	public int getxAgent() {
		return xAgent;
	}

	public int getyAgent() {
		return yAgent;
	}

	public int getNumberOfBlackBoxes() {
		return numberOfBlackBoxes;
	}

	public int getAllNumberOfAlivePassengers() {
		return allNumberOfAlivePassengers;
	}

	public int getNumberOfShips() {
		return numberOfShips;
	}

	public void setNumberOfShips(int numberOfShips) {
		this.numberOfShips = numberOfShips;
	}

	public void setAllNumberOfAlivePassengers(int allNumberOfAlivePassengers) {
		this.allNumberOfAlivePassengers = allNumberOfAlivePassengers;
	}

	public void setActionPerformed(Action actionPerformed) {
		this.actionPerformed = actionPerformed;
		this.numberOfShips = this.actionPerformed.getNumberOfShips();
		this.allNumberOfAlivePassengers = this.actionPerformed.getAllNumberOfAlivePassengers();
		this.calcH1();
		this.calcH2();
	}

	public Action getActionPerformed() {
		return actionPerformed;
	}

	public int getH1() {
		return h1;
	}

	public int getH2() {
		return h2;
	}

	public int getNumberOfDamagedBoxes() {
		return numberOfDamagedBoxes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(grid);
		result = prime * result + Objects.hash(agentCapacity, xAgent, yAgent, numberOfBlackBoxes, numberOfDamagedBoxes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return agentCapacity == other.agentCapacity && Arrays.deepEquals(grid, other.grid) && xAgent == other.xAgent
				&& yAgent == other.yAgent && numberOfBlackBoxes == other.numberOfBlackBoxes
				&& numberOfDamagedBoxes == other.numberOfDamagedBoxes;
	}

	public void display() {
		String a = "Init";
		if (this.actionPerformed != null)
			a = this.actionPerformed.getActionName();
		System.out.println("AgentX " + this.xAgent + " AgentY " + this.yAgent + " AgentCapacity " + this.agentCapacity
				+ " action " + a);
		for (int i = 0; i < grid.length; i++) {
			String s = Arrays.toString(grid[i]);
			System.out.println(s);

		}
		System.out.println("******************************************************************************");
	}

}
