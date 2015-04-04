package test;

import impl.PerfectHashMap;


public class PHMapTest extends MapTest {
    static String[] allKeys = { 
        "Minnesota", 
        "Texas", 
        "Oregon", 
        "New Jersey", 
        "Pennsylvania", 
        "Massachusetts", 
        "Arizona", 
        "Michigan", 
        "Ohio", 
        "New York", 
        "Florida", 
        "Colorado", 
        "Alabama", 
        "Kentucky", 
        "Kansas", 
        "Alaska" };
    
    protected void reset() {
        testMap = new PerfectHashMap<String, String>(allKeys);
    }

 
}
