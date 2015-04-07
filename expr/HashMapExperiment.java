package expr;

import java.io.FileInputStream;
import adt.Map;

/**
 * Experiment
 * 
 * @author Kelly Breedlove
 * CSCI 345, Wheaton College
 * 6 April 2015
 * 
 * Some of this code is borrowed/modeled after TVD's MapTest code.
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
     * The stopwatch for this experiment
     */
    protected Stopwatch stopwatch;
	
    /**
     * Map for testing
     */
    protected Map<String,String> testMap;
		
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
    	keys = new String[1000];
    	values = new String[2000];
    }

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
     * Test put runtimes for BasicBST, AVL, and RedBlack trees.
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
     * Test get runtimes for BasicBST, AVL, and RedBlack trees.
     * @return The runtime in seconds
     */
    public long getRuntimes(int pairs) {
	String scrap;
	reset();
	populate(pairs);
	stopwatch = new Stopwatch();
	for (int i = 0; i < pairs; i++)
	    testMap.get(keys[i]);
	return stopwatch.elapsedTimeMillis();
    }
	
    /**
     * Test get runtimes for BasicBST, AVL, and RedBlack trees.
     * @return The runtime in seconds
     */
    public long getWorstCaseRuntimes(int pairs) {
	String scrap;
	reset();
	populate(pairs);
	stopwatch = new Stopwatch();
	for (int i = 0; i < pairs; i++)
	    testMap.get("abcdefg"); 
	return stopwatch.elapsedTimeMillis();
    }
	
    /**
     * Test containsKey runtimes for BasicBST, AVL, and RedBlack trees.
     * @return The runtime in seconds
     */
    public long containsKeyRuntimes(int pairs) {
	Boolean scrap;
	reset();
	populate(pairs);
	stopwatch = new Stopwatch();
	for (int i = 0; i < pairs; i++)
	    testMap.containsKey(keys[i]);
	return stopwatch.elapsedTimeMillis();
    }
	
    /**
     * Test containsKey runtimes for BasicBST, AVL, and RedBlack trees.
     * @return The runtime in seconds
     */
    public long containsKeyWorstCaseRuntimes(int pairs) {
	Boolean scrap;
	reset();
	populate(pairs);
	stopwatch = new Stopwatch();
	for (int i = 0; i < pairs; i++)
	    testMap.containsKey("abcdefg"); 
	return stopwatch.elapsedTimeMillis();
    }

}
