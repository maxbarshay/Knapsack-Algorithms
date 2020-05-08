public class Item implements Comparable<Item> {
	
	int index;
	int value;
	int weight;
	double ratio;

	public Item(int index, int value, int weight){
		this.index = index;
		this.value = value;
		this.weight = weight;
		this.ratio = Double.valueOf(value) / Double.valueOf(weight);
	}

	public int compareTo(Item other){ // so that we sort by finish time
		if (this.ratio > other.ratio){
			return -1;
		}
		else if (this.ratio < other.ratio){
			return 1;
		}
		else{
			return 0;
		}
	}

	public String toString(){
		return "Index: " + index + " Value: " + value + " Weight: " 
		+ weight + " Ratio: " + ratio;
	}

	public int getIndex(){
		return index;
	}

	public int getValue(){
		return value;
	}

	public int getWeight(){
		return weight;
	}

	public double getRatio(){
		return ratio;
	}

}