package expr;

import adt.Map;
import impl.BasicHashMap;

/**
 * LPHMapExperiment
 * 
 * @author Kelly Breedlove
 * CSCI 345, Wheaton College
 * 23 February 2015
 */
public class BHMapExperiment extends HashMapExperiment {

	public BHMapExperiment() { type = "BasicHashMap"; reset(); }
	
	/**
	 * Reset the testMap to a LPHMapTreeMap.
	 */
	protected void reset() {
	    //testMap = (Map<String, String>) new BasicHashMap<String,String>();		
	}
}