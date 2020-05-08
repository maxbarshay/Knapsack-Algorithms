import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.util.Collections;

public class Brute{

	public static void main(String[] args) {
		
		try {
			long time = System.nanoTime();
			String[] file = IOHandler.getFileName(args);
			ArrayList<ArrayList<Integer>> information = IOHandler.inputFile(file[0]);
			ArrayList<ArrayList<Integer>> indexList = Brute.indexes(information.get(0).get(0));
			int bestResult = 0;
			int weight = 0;
			ArrayList<Integer> items = new ArrayList<>();
			for (int i = 1; i < indexList.size(); i++){
				int weightSum = 0;
				int valueSum = 0;
				ArrayList<Integer> tempRes = new ArrayList<>();
				for (int j = 0; j < indexList.get(i).size(); j++){
					if ((weightSum + information.get(4).get(indexList.get(i).get(j) - 1)) >
						information.get(1).get(0)){
						weightSum = 0;
						valueSum = 0;
						break;
					}
					valueSum += information.get(3).get(indexList.get(i).get(j) - 1);
			 		weightSum += information.get(4).get(indexList.get(i).get(j) - 1);
			 		tempRes.add(indexList.get(i).get(j));
				}
				if (valueSum > bestResult){
					bestResult = valueSum;
			 		weight = weightSum;
			 		items.clear();
			 		items.addAll(tempRes);
				}
			}
			System.out.println("Using Brute force the best feasible solution found: Value " + bestResult
				+ " Weight " + weight);
			Collections.sort(items);
			for (int i = 0; i < items.size(); i++){
				System.out.print(items.get(i) + " ");
			}
			System.out.println();
			long time2 = System.nanoTime();
			System.out.println(Double.valueOf((time2 - time)) / 1000000000.0 + "s");
		}
		catch (FileNotFoundException e){
			System.out.println("Sorry Error Found");
		}
	}


	public static ArrayList<String> getGrayCode(int n){
	ArrayList<String> codes = new ArrayList<String>();
	ArrayList<String> temp = new ArrayList<String>();
	if (n > 1){
		temp = getGrayCode(n-1);
		for (int i = 0; i < temp.size(); i++){
			codes.add("0" + temp.get(i));
			}
		for (int i = codes.size() - 1; i > -1; i--){
			codes.add("1" + temp.get(i));
			}
		return codes;
			}
		else{
			codes.add("0");
			codes.add("1");
			return codes;
		}
   }

   public static ArrayList<ArrayList<Integer>> indexes(int n){
   		ArrayList<String> subsets = getGrayCode(n);
   		ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
   		for (int i = 0; i < subsets.size(); i++){
   			ArrayList<Integer> temp = new ArrayList<Integer>();
   			char[] tempString = subsets.get(i).toCharArray();
   			for (int j = 0; j < n; j++){
   				if (tempString[j] == '1'){
   					temp.add(j+1);
   				}
   			}
   			results.add(temp);
   		}
   		return results;
   }


}