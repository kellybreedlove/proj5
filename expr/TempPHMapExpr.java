package expr;

import java.io.FileInputStream;
import java.util.Scanner;

import impl.PerfectHashMap;
import adt.Map;

public class TempPHMapExpr {
	
	/**
     * Values for testing
     */
    protected static String[] values;
	
    /**
     * Keys for testing
     */
    protected static String[] keys;

    /**
     * The filename for data input
     */
    protected static String keysInput = "expr/namesKeys", 
	valuesInput = "expr/namesValues";

    /*
     *  For some reason this won't work for more than 800 pairs of the 
     *  sacramentoCrime keys and values, which is unfortunate since that 
     *  data set goes up to around 8,000 and is by far the largest.
     */
    static {
    	//initialize keys and values
    	keys = new String[25];
    	values = new String[25];
    	
    	try {
			Scanner keysIn = new Scanner(new FileInputStream(keysInput));
			Scanner valuesIn = new Scanner(new FileInputStream(valuesInput));
			for (int i = 0; i < keys.length; i++) 
				keys[i] = keysIn.nextLine();
			for (int i = 0; i < values.length; i++) 
				values[i] = valuesIn.nextLine();
			keysIn.close();
			valuesIn.close();
		} catch (Exception e) { 	System.out.println("data scan in error"); }
    }
		
	public static void main(String[] args) {
		//String[] keys = {"bulbasaur", "charmander", "pikachu", "squirtle", "arcanine"};
		//String[] values = {"grass", "fire", "electric", "water", "fire"};
		System.out.print("building phm...");
	    Map<String, String>testMap = (Map<String, String>) new PerfectHashMap<String,String>(keys);		
	    System.out.println("built.");
		
	    for (int i = 0; i < keys.length; i++)
	    	testMap.put(keys[i], values[i]);	
		for (int i = 0; i < keys.length; i++)
			System.out.println(keys[i] + ", " + testMap.get(keys[i]));
	}
}
