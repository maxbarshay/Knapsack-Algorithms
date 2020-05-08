import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Collections; // may not need all of these

public class Greedy{

	public static void main(String[] args) { // main method
		try {
			long time = System.nanoTime();
			String[] file = IOHandler.getFileName(args);
			ArrayList<ArrayList<Integer>> information = IOHandler.inputFile(file[0]);
			ArrayList<Item> items = Greedy.ordering(information); // sorting
			ArrayList<Integer> taken = new ArrayList<>(); //keeping track of which items were taken
			int capacity = information.get(1).get(0);
			int valueSum = 0;
			int weightSum = 0;
			for (int i = 0; i < items.size(); i++){ // selecting items
				if ((weightSum + items.get(i).getWeight()) <= capacity){
					valueSum += items.get(i).getValue();
			 		weightSum += items.get(i).getWeight();
			 		taken.add(items.get(i).getIndex());
				}
			} 
			System.out.println("Greedy solution (not necessarily optimal): Value " + valueSum
				+ " Weight " + weightSum);
			Collections.sort(taken);
			for (int j = 0; j < taken.size(); j++){
				System.out.print(taken.get(j) + " ");
			}
			System.out.println();
			long time2 = System.nanoTime();
			System.out.println(Double.valueOf((time2 - time)) / 1000000000.0 + "s");
			
		}
		catch (FileNotFoundException e) {
			System.out.println("Sorry error found.");
		}
	}

	public static ArrayList<Item> ordering(ArrayList<ArrayList<Integer>> information){ // sorting item objects
		Greedy greedyObject = new Greedy();
		ArrayList<ArrayList<Double>> results = new ArrayList<ArrayList<Double>>();
		ArrayList<Integer> newIds = new ArrayList<>();
		ArrayList<Item> items = greedyObject.itemGenerator(information);
		Collections.sort(items);
		return items;
	}

	public ArrayList<Item> itemGenerator(ArrayList<ArrayList<Integer>> information){ // generating item objects
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Integer> ids = information.get(2);
		ArrayList<Integer> values = information.get(3);
		ArrayList<Integer> weights = information.get(4);
		for (int i = 0; i < information.get(0).get(0); i++){
			items.add(new Item(ids.get(i), values.get(i), weights.get(i)));
		}
		return items;
	}

}