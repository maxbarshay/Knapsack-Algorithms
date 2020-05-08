import java.util.ArrayList;

public class Node implements Comparable<Node> {

	int weight;
	int value;
	int level;
	double bound;
	ArrayList<Integer> nodesVisited = new ArrayList<>();

	public Node(){}

	public Node(int weight, int value, int level, double bound, ArrayList<Integer> nodesVisited){
		this.weight = weight;
		this.value = value;
		this.level = level;
		this.bound = bound;
		this.nodesVisited = nodesVisited;
	}

	public int compareTo(Node other){
		if (this.bound > other.bound){
			return -1;
		}
		else if (this.bound < other.bound){
			return 1;
		}
		else{
			return 0;
		}
	}

	// public double getUpperBound(){
	// 	return upperBound;
	// }

	public void addNodeVisited(Integer n){
		nodesVisited.add(n);
	}

	public String toString(){
		return "Level: " + level + " Weight: " + weight + " Value: " + value + " Bound " + bound
			+ " Nodes Visited: " + nodesVisited;
	}

}