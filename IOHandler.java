import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOHandler{

	public static void main(String[] args) {
		try {
		String[] file = IOHandler.getFileName(args);
		ArrayList<ArrayList<Integer>> information = IOHandler.inputFile(file[0]);
		System.out.println(information);
		}
		catch (Exception e){
			System.out.println("Sorry Error Found");
		}	
	}

	public static ArrayList<ArrayList<Integer>> inputFile(String filename)
		throws FileNotFoundException {
			try (Scanner input = new Scanner(new File(filename))){
				return processFile(input);
			}
	}

	public static ArrayList<ArrayList<Integer>> processFile(Scanner input)
		throws FileNotFoundException {
		ArrayList<ArrayList<Integer>> returnable = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> n = new ArrayList<>();
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		ArrayList<Integer> weights = new ArrayList<>();
		ArrayList<Integer>	capacity = new ArrayList<>();
		ArrayList<String> phrases = new ArrayList<>();
		n.add(input.nextInt());	
		returnable.add(n);
		while (input.hasNext()){
			phrases.add(input.next());
		}
		capacity.add(Integer.parseInt(phrases.get(phrases.size() - 1)));
		returnable.add(capacity);
		for (int i = 0; i < phrases.size() - 1; i = i + 3){
			ids.add(Integer.parseInt(phrases.get(i)));
		}
		for (int i = 1; i < phrases.size() - 1; i = i + 3){
			values.add(Integer.parseInt(phrases.get(i)));
		}
		for (int i = 2; i < phrases.size() - 1; i = i + 3){
			weights.add(Integer.parseInt(phrases.get(i)));
		}
		returnable.add(ids);
		returnable.add(values);
		returnable.add(weights);
		//System.out.println(phrases);
		return returnable;
	}

	

	public static String[] getFileName(String[] args)
   	{
      	if (args.length < 1){
        	System.err.println("File not specified.");
        	System.exit(1);
      	}
    	return args;
   	}

}