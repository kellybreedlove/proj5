package expr;

import adt.Map;
import impl.PerfectHashMap;

/**
 * LPHMapExperiment
 * 
 * @author Kelly Breedlove
 * CSCI 345, Wheaton College
 * 23 February 2015
 */
public class PHMapExperiment extends HashMapExperiment {

	public PHMapExperiment(int maxPairs) { this.maxPairs = maxPairs; name = "PerfectHashMap"; reset(); }
	
	/**
	 * Reset the testMap to a LPHMapTreeMap.
	 */
	protected void reset() {
		System.out.println(keys.length);
	    testMap = (Map<String, String>) new PerfectHashMap<String,String>(keys);		
	}
}