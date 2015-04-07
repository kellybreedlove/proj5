package expr;

import adt.Map;
import impl.LinProbHashMap;

/**
 * LPHMapExperiment
 * 
 * @author Kelly Breedlove
 * CSCI 345, Wheaton College
 * 23 February 2015
 */
public class LPHMapExperiment extends HashMapExperiment {

	public LPHMapExperiment() { reset(); }
	
	/**
	 * Reset the testMap to a LPHMapTreeMap.
	 */
	protected void reset() {
	    testMap = (Map<String, String>) new LinProbHashMap<String,String>();		
	}
}