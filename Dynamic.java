import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;

public class Dynamic{

	public static void main(String[] args) {
		try {
			long time = System.nanoTime();
			String[] file = IOHandler.getFileName(args);
			ArrayList<ArrayList<Integer>> information = IOHandler.inputFile(file[0]);
			int numItems = information.get(0).get(0);
			int capacity = information.get(1).get(0);
			int[][] table = new int[numItems+1][capacity+1]; // creating my table to store solutions
			int maxValue = 0;
			int rowsCompleted = 0;
			for (int i = 0; i < numItems + 1; i++){ //initilizing the table for the base cases
				table[i][0] = 0;
			}
			for (int i = 0; i < capacity + 1; i++){
				table[0][i] = 0;
			}
			for (int i = 1; i < numItems + 1; i++){ // loop for filling in the table
				rowsCompleted++;
				if (((System.nanoTime() - time) / 1000000000.0) > 100000){ // to ensure time constraints can be met
					break;
				}
				for (int j = 1; j < capacity + 1; j++){
					if (information.get(4).get(i-1) > j){ // main if then, applying recurrence relation
						table[i][j] = table[i-1][j];
					}
					else{
					table[i][j] = Math.max(
						table[i-1][j],
						(table[i-1][j - information.get(4).get(i-1)] + information.get(3).get(i-1)));
						}
					}
				}
			if (rowsCompleted == numItems){ // depending on if algorithm got stopped short call different method
				solutionRecovery(table, information);
			}
			else{
				augmentedSolutionRecovery(table, information, rowsCompleted);
			}
			long time2 = System.nanoTime();
			System.out.println(Double.valueOf((time2 - time)) / 1000000000.0 + "s");
			}
		catch (FileNotFoundException e){
			System.out.println("Sorry error found.");
		}
	}

	public static ArrayList<Integer> backtracer(int[][] table, ArrayList<ArrayList<Integer>> information){
		ArrayList<Integer> solution = new ArrayList<>(); // this method recovers which items are in the
		// optimal solution
		int numItems = table.length - 1; 
		int capacity = table[0].length - 1;
		ArrayList<Integer> values = information.get(3);
		ArrayList<Integer> weights = information.get(4);
		for (int i = numItems; i > 0; i--){ // reverse applying the recurrence relation
			if ((weights.get(i-1) <= capacity) &&
				(table[i-1][capacity - weights.get(i-1)] + values.get(i-1)) >= table[i-1][capacity]){
				solution.add(i-1);
				capacity = capacity - weights.get(i-1);
			}
		}
		return solution;	
	}

	public static void solutionRecovery(int[][] table, ArrayList<ArrayList<Integer>> information){
		ArrayList<Integer> solution = Dynamic.backtracer(table, information); // mainly an IO method, works 
		// together with backtracing to find a solution
		ArrayList<Integer> values = information.get(3);
		ArrayList<Integer> weights = information.get(4);
		ArrayList<Integer> items = new ArrayList<>();
		int numItems = table.length - 1;
		int capacity = table[0].length - 1;
		int valueSum = 0;
		int weightSum = 0;
		for (int i = 0; i < numItems; i++){ // recovering the weights and the values
			if (solution.contains(i)){
				valueSum += values.get(i);
				weightSum += weights.get(i);
				items.add(i+1);
			}
		}
		System.out.println("Dynamic Programming solution: Value " + valueSum + " Weight " + weightSum);
		Collections.sort(items);
		for (int j = 0; j < items.size(); j++){
			System.out.print(items.get(j) + " ");
		}
		System.out.println();
	}

	public static ArrayList<Integer> augmentedBacktracer(int[][] table, ArrayList<ArrayList<Integer>> information,
		int rowsCompleted){
		// does the same as backtracer but works if the table is not complete
		ArrayList<Integer> solution = new ArrayList<>(); 
		int numItems = rowsCompleted;
		int capacity = table[0].length - 1;
		ArrayList<Integer> values = information.get(3);
		ArrayList<Integer> weights = information.get(4);
		for (int i = numItems; i > 0; i--){
			if ((weights.get(i-1) <= capacity) &&
				(table[i-1][capacity - weights.get(i-1)] + values.get(i-1)) >= table[i-1][capacity]){
				solution.add(i-1);
				capacity = capacity - weights.get(i-1);
			}
		}
		return solution;	
	}

	public static void augmentedSolutionRecovery(int[][] table, ArrayList<ArrayList<Integer>> information,
		int rowsCompleted){
		// partner to augmented backtracer, works on uncompelete tables
		ArrayList<Integer> solution = Dynamic.augmentedBacktracer(table, information, rowsCompleted);
		ArrayList<Integer> values = information.get(3);
		ArrayList<Integer> weights = information.get(4);
		ArrayList<Integer> items = new ArrayList<>();
		int numItems = table.length - 1;
		int capacity = table[0].length - 1;
		int valueSum = 0;
		int weightSum = 0;
		for (int i = 0; i < numItems; i++){
			if (solution.contains(i)){
				valueSum += values.get(i);
				weightSum += weights.get(i);
				items.add(i+1);
			}
		}
		System.out.println("Dynamic Programming solution: Value " + valueSum + " Weight " + weightSum);
		Collections.sort(items);
		for (int j = 0; j < items.size(); j++){
			System.out.print(items.get(j) + " ");
		}
		System.out.println();
	}



}