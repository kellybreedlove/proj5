package test;

import impl.BasicHashMap;


public class BHMapTest extends MapTest {

    protected void reset() {
        testMap = new BasicHashMap<String, String>(5);
    }


}
