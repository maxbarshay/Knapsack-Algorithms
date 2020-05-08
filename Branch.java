import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;

public class Branch{

	// roughly followed pseudocode from Foundations of Algorithms by Richard Neapolitan

	public static void main(String[] args) {
		try {
			// main calls branchBound method which runs the algorithm
			long time = System.nanoTime();
			Branch branchObject = new Branch();
			String[] file = IOHandler.getFileName(args);
			ArrayList<ArrayList<Integer>> information = IOHandler.inputFile(file[0]);
			branchObject.branchBound(information);
			long time2 = System.nanoTime();
			System.out.println((time2 - time) / 1000000000.0 + "s");

		}
		catch (FileNotFoundException e){
			System.out.println("Sorry error found.");
		}

	}

	public void branchBound(ArrayList<ArrayList<Integer>> information){
		long time = System.nanoTime();
		Node w;
		Node u;
		Node v = new Node();
		int numItems = information.get(0).get(0);
		int capacity = information.get(1).get(0);
		ArrayList<Integer> values = new ArrayList<>();
		ArrayList<Integer> weights = new ArrayList<>();
		ArrayList<Item> items = itemGenerator(information);
		for (Item i : items){ //sorting values and weights in non-increasing order of value to weight ratio
			values.add(i.getValue());
		}
		for (Item i : items){
			weights.add(i.getWeight());
		}
		PriorityQueue<Node> pqueue = new PriorityQueue<>(); // using a priority queue to implement best-first search
		Node topNode = v;
		v.level = 0;
		v.value = 0;
		v.weight = 0;
		v.bound = bound(v, information, values, weights); // calculating a bound
		int maxProfit = 0; 
		pqueue.add(v);
		while (pqueue.size() > 0 && ((System.nanoTime() - time) / 1000000000) < 600){ 
			// to ensure the priority queue is not empty as well as providing an ability to stop the algorithm
			v = pqueue.remove();
			if (v.bound > maxProfit){ // main body of algorithm
				u = new Node(); // u is the node in the next level that branches to the left (includes the next node)
				u.level = v.level + 1;
				for (int i = 0; i < v.nodesVisited.size(); i++){
					u.nodesVisited.add(v.nodesVisited.get(i));
				}
				u.addNodeVisited(items.get(u.level-1).getIndex());
				u.weight = v.weight + weights.get(u.level - 1);
				u.value = v.value + values.get(u.level - 1);
				if (u.weight <= capacity && u.value > maxProfit){
					maxProfit = u.value;
					topNode = new Node(u.weight, u.value, u.level, u.bound, u.nodesVisited);
				}
				u.bound = bound(u, information, values, weights);
				if (u.bound > maxProfit){
					pqueue.add(u);
				}
				w = new Node(); // w is the node in the next level that branches to the right 
				// (it has the same attributes as its parents since it does not include the next node)
				for (int i = 0; i < v.nodesVisited.size(); i++){
					w.nodesVisited.add(v.nodesVisited.get(i));
				}
				w.weight = v.weight;
				w.value = v.value;
				w.level = u.level;
				w.bound = bound(w, information, values, weights);
				if (w.bound > maxProfit){
					pqueue.add(w);
				}
			}
		}
		System.out.println("Using Branch and Bound the best feasible solution found: Value " + topNode.value
			+ " Weight " +  topNode.weight);
		Collections.sort(topNode.nodesVisited);
		for (int j = 0; j < topNode.nodesVisited.size(); j++){
				System.out.print(topNode.nodesVisited.get(j) + " ");
		}
			System.out.println();

	}

	public double bound(Node u, ArrayList<ArrayList<Integer>> information, ArrayList<Integer> values,
		ArrayList<Integer> weights){
		// this is my bounding function, grap items greedily and then take a fraction of the last item that wont fit
		int new_level;
		int totweight;
		double result;
		int numItems = information.get(0).get(0);
		int capacity = information.get(1).get(0);
		if (u.weight >= capacity){
			return 0; // so that we do not select this node
		}
		else if (u.level == numItems){ // if it is a leaf node make the bound the value
			return u.value;
		}
		else{
			// greedily add items and take a fraction of the last item
			result = u.value;
			new_level = u.level + 1;
			totweight = u.weight;
			while (new_level <= numItems && (totweight + weights.get(new_level - 1) <= capacity)){
				totweight = totweight + weights.get(new_level - 1);
				result = result + values.get(new_level - 1);
				new_level++;
			}
			if (new_level <= numItems){ // only take a fraction if there is an item to be taken
				result = result + ((capacity - totweight) * Double.valueOf((values.get(new_level - 1)) / 
					Double.valueOf(weights.get(new_level - 1))));
			}
			return result;
		}
		
	}

	public ArrayList<Item> itemGenerator(ArrayList<ArrayList<Integer>> information){
		// this creates items
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Integer> ids = information.get(2);
		ArrayList<Integer> values = information.get(3);
		ArrayList<Integer> weights = information.get(4);
		for (int i = 0; i < information.get(0).get(0); i++){
			items.add(new Item(ids.get(i), values.get(i), weights.get(i)));
		}
		Collections.sort(items);
		return items;
	}

	// Below is an alternative bounding function that also works, but I believe does not provide as tight of a bound

	// public double bound(Node u, ArrayList<ArrayList<Integer>> information){
	// 	int new_level;
	// 	int totweight;
	// 	double result;
	// 	int numItems = information.get(0).get(0);
	// 	int capacity = information.get(1).get(0);
	// 	ArrayList<Integer> values = information.get(3);
	// 	ArrayList<Integer> weights = information.get(4);
	// 	if (u.weight >= capacity){
	// 		return 0; // so that we do not select this node
	// 	}
	// 	else if (u.level == numItems){
	// 		return u.value;
	// 	}
	// 	else{
	// 		int value = u.value;
	// 		int weight = u.weight;
	// 		// put in a check to see what happens on the last level
	// 		new_level = u.level + 1;
	// 		// if (new_level == numItems - 1){
	// 		// 	result = value;
	// 		// 	return result;
	// 		// }
	// 		result = value + (capacity - weight) * (Double.valueOf(values.get(new_level-1))
	// 		 	/ Double.valueOf(weights.get(new_level-1)));
	// 		return result;
	// 	}
		
	// // }


	



	

}