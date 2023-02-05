package Main;

import java.util.Arrays;
import java.util.Random;

public class main {

	/// return [ships,stations]
	private static String[] randomShipsStations(int numOfPassengersBounds[], int m, int n) {
		Random rand = new Random();
		String map[][] = new String[m][n];
		StringBuilder ships = new StringBuilder();
		StringBuilder stations = new StringBuilder();
		int countShips = 0;
		int countStations = 0;

		int iFirstShip = rand.nextInt(0, m);
		int jFirstShip = rand.nextInt(0, n);
		int numPassFirst = rand.nextInt(numOfPassengersBounds[0], numOfPassengersBounds[1] + 1);
		String firstShip = "S" + iFirstShip + "," + "S" + jFirstShip + "," + "S" + numPassFirst;
		map[iFirstShip][jFirstShip] = firstShip;
		countShips++;

		int iFirstStation = rand.nextInt(0, m);
		int jFirstStation = rand.nextInt(0, n);

		while (iFirstStation == iFirstShip && jFirstStation == jFirstShip) {
			iFirstStation = rand.nextInt(0, m);
			jFirstStation = rand.nextInt(0, n);
		}

		String firstStation = "I" + iFirstStation + "," + "I" + jFirstStation;
		map[iFirstStation][jFirstStation] = firstStation;
		countStations++;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int prob = rand.nextInt(0, 3);
				if (prob == 0 && map[i][j] == null) { // ships
					int numPass = rand.nextInt(numOfPassengersBounds[0], numOfPassengersBounds[1] + 1);
					String ship = "S" + i + "," + "S" + j + "," + "S" + numPass;
					map[i][j] = ship;
					countShips++;
				} else if (prob == 1 && map[i][j] == null) { // station
					String station = "I" + i + "," + "I" + j;
					map[i][j] = station;
					countStations++;
				}
			}
		}
//		for (String[] x : map) {
//			System.out.println(Arrays.toString(x));
//		}

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (map[i][j] == null)
					continue;
				if (map[i][j].charAt(0) == 'S') {
					String[] tempShip = map[i][j].split(",");
					String ship = tempShip[1].substring(1) + "," + tempShip[0].substring(1) + ","
							+ tempShip[2].substring(1);
					if (countShips == 1) {
						ship += ";";
					} else {
						ship += ",";
					}
					ships.append(ship);
					countShips--;

				} else {
					String[] tempStation = map[i][j].split(",");
					String station = tempStation[1].substring(1) + "," + tempStation[0].substring(1);
					if (countStations == 1) {
						station += ";";
					} else {
						station += ",";
					}
					stations.append(station);
					countStations--;
				}

			}
		}
		String res[] = { ships.toString(), stations.toString() };
		return res;
	}

	public static String GenGrid() {
		int mBounds[] = { 3, 3 };
		int nBounds[] = { 5, 5 };
		int numOfPassengersBounds[] = { 1, 4 };
		int agentCapacityBounds[] = { 1, 3 };
		Random rand = new Random();
		int m = rand.nextInt(mBounds[0], mBounds[1] + 1);
		int n = rand.nextInt(nBounds[0], nBounds[1] + 1);

		int agentCapacity = rand.nextInt(agentCapacityBounds[0], agentCapacityBounds[1] + 1);
		int xAgent = rand.nextInt(0, m);
		int yAgent = rand.nextInt(0, n);
		String line1 = m + "," + n + ";" + agentCapacity + ";" + yAgent + "," + xAgent + ";";
		String[] shipsStations = randomShipsStations(numOfPassengersBounds, m, n);
		String stations = shipsStations[1];
		String ships = shipsStations[0];

		StringBuilder grid = new StringBuilder();
		grid.append(line1);
		grid.append(stations);
		grid.append(ships);

		return grid.toString();

	}

	public static void main(String args[]) {

//		while (true) {
//			String grid = GenGrid();
//			//System.out.println(grid);
//			Agent searchAgent = new Agent(grid, "AS1",false);
//			String res1[] = searchAgent.search().split(";");
//			Agent searchAgent2 = new Agent(grid, "AS2",false);
//			String res2[] = searchAgent2.search().split(";");
//			if (Integer.parseInt(res1[res1.length - 2]) != Integer.parseInt(res2[res2.length - 2]) || Integer.parseInt(res1[res1.length - 3]) != Integer.parseInt(res2[res2.length - 3])) {
//				System.out.println(Arrays.toString(res1));
//				System.out.println(Arrays.toString(res2));
////				System.out.println(res1[res1.length - 2] +" "+ res2[res2.length - 2]);
////				System.out.println(res1[res1.length - 2] != res2[res2.length - 2]);
////				System.out.println(res1[res1.length - 3] != res2[res2.length - 3]);
//				
//				System.out.println(grid);
//				break;
//			}
//			//System.out.println();
//		}
		
		Agent searchAgent = new Agent("2,2;3;0,0;0,1;0,0,2,1,0,4,1,1,4;", "AS2", true);
		System.out.println(searchAgent.search());

	}

}
