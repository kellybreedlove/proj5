package expr;

import java.io.FileInputStream;
import java.util.Scanner;

import adt.Map;

/**
 * Experiment
 * 
 * @author Kelly Breedlove
 * CSCI 345, Wheaton College
 * 6 April 2015
 * 
 * Some of this code is borrowed/modeled after TVD's MapTest code.
 * Very similar to TreeMapExperiment from proj3.
 */

public abstract class HashMapExperiment {

    /**
     * A stopwatch for runtime experiments
     *  
     * Implementation, although pretty obvious, is from 
     * introcs.cs.princeton.edu/java/32class/Stopwatch.java.html
     * which I happened across when I was trying to find out if there
     * was a stopwatch in the java api.
     */
	protected class Stopwatch {
		private final long start;
		public Stopwatch() { start = System.currentTimeMillis(); }
		/**
		 * The time elapsed in milliseconds
		 * @return the time in milliseconds since this object was created
		 */
		public long elapsedTimeMillis() {
			long now = System.currentTimeMillis();
			return now - start;
		}
		/**
		 * The time elapsed in seconds
		 * @return the time in seconds since this object was created
		 */
		public double elapsedTimeSec() {
			long now = System.currentTimeMillis();
			return (now - start) / 1000.0;
		}
	}

	/**
	 * The type of the map being tested.
	 */
	protected String name;
	
	/**
	 * The maximum number of pairs to test.
	 */
	protected static int maxPairs = 7000;
	
	/**
     * The stopwatch for this experiment
     */
    protected Stopwatch stopwatch;
	
    /**
     * Map for testing
     */
    protected Map<String, String> testMap;
		
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
    protected static String keysInput = "expr/sacramentoCrimeKeys", 
	valuesInput = "expr/sacramentoCrimeValues";

    /*
     *  For some reason this won't work for more than 800 pairs of the 
     *  sacramentoCrime keys and values, which is unfortunate since that 
     *  data set goes up to around 8,000 and is by far the largest.
     */
    static {
    	//initialize keys and values
    	keys = new String[maxPairs];
    	values = new String[maxPairs];
    	
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
    
    /**
     * An accessor method for name
     * @return type
     */
    public String getName() { return name; }

    /**
     * Reset the testMap.
     */
    protected abstract void reset();
	
    /**
     * Populate the testMap.
     * @param pairs The number of pairs to add to the testMap
     */
    protected void populate(int pairs) {
	for (int i = 0; i < pairs; i++) 
	    testMap.put(keys[i], values[i]);
    }
	
    /**
     * Test put runtimes for BasicHashMap, LinearProbHashMap, and PerfectHashMap.
     * @return 
     * @return The runtime in seconds
     */
    public long putRuntimes(int pairs) {
	reset();
	stopwatch = new Stopwatch();
	populate(pairs);
	return stopwatch.elapsedTimeMillis();
    }
	
    /**
     * Test get runtimes for BasicHashMap, LinearProbHashMap, and PerfectHashMap.
     * @return The runtime in seconds
     */
    public long getRuntimes(int pairs) {
	reset();
	populate(pairs);
	stopwatch = new Stopwatch();
	for (int i = 0; i < pairs; i++)
	    testMap.get(keys[i]);
	return stopwatch.elapsedTimeMillis();
    }
	
    /**
     * Test get runtimes for BasicHashMap, LinearProbHashMap, and PerfectHashMap.
     * @return The runtime in seconds
     */
    public long getWorstCaseRuntimes(int pairs) {
	reset();
	populate(pairs);
	stopwatch = new Stopwatch();
	for (int i = 0; i < pairs; i++)
	    testMap.get("abcdefg"); 
	return stopwatch.elapsedTimeMillis();
    }
	
    /**
     * Test containsKey runtimes for BasicHashMap, LinearProbHashMap, and PerfectHashMap.
     * @return The runtime in seconds
     */
    public long containsKeyRuntimes(int pairs) {
	reset();
	populate(pairs);
	stopwatch = new Stopwatch();
	for (int i = 0; i < pairs; i++)
	    testMap.containsKey(keys[i]);
	return stopwatch.elapsedTimeMillis();
    }
	
    /**
     * Test containsKey runtimes for BasicHashMap, LinearProbHashMap, and PerfectHashMap.
     * @return The runtime in seconds
     */
    public long containsKeyWorstCaseRuntimes(int pairs) {
	reset();
	populate(pairs);
	stopwatch = new Stopwatch();
	for (int i = 0; i < pairs; i++)
	    testMap.containsKey("abcdefg"); 
	return stopwatch.elapsedTimeMillis();
    }

}
