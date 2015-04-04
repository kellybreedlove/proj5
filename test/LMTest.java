package test;

import impl.ListMap;


public class LMTest extends MapTest {

	protected void reset() {
		testMap = new ListMap<String, String>();
	}

}
