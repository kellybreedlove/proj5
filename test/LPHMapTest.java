package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;

import impl.LinProbHashMap;

public class LPHMapTest extends MapTest {
    protected void reset() {
        testMap = new LinProbHashMap<String, String>();
    }


    protected String[] arrayOfAllLetterSeqs(int seqLen) {
        String[] result = { "" };
        while (result[0].length() < seqLen) {
            String[] newResult = new String[result.length * 26];
            for (int i = 0; i < result.length; i++) 
                for (int j = 0; j < 26; j++) {
                    newResult[(i * 26) + j] = result[i] + ((char) ('a' + j));
                }
            result = newResult;
        }
        return result;
    }
    
    @Test
    public void stressTest() {
        HashSet<String> original = new HashSet<String>();
        original.addAll(Arrays.asList(arrayOfAllLetterSeqs(3)));
        Iterator<String> source = original.iterator();

        
        HashSet<String> added = new HashSet<String>();
       HashSet<String> removed = new HashSet<String>();
      
       
       testMap = new LinProbHashMap<String,String>(17576);
       
        // add a whole bunch
       
        for (int i = 0; i < 150; i++) {
            String x = source.next();
            added.add(x);
            testMap.put(x, x);
            assertTrue(testMap.containsKey(x));
            assertEquals(x, testMap.get(x));
        }
      
        // check they're there
        for (Iterator<String> it = added.iterator(); it.hasNext(); ) {
            String key = it.next();
            assertTrue(testMap.containsKey(key));
            assertEquals(key, testMap.get(key));
        }

        // remove a bunch
        
        Iterator<String> someAdded = added.iterator();
        for (int i = 0; i < 50; i++) {
            String key = someAdded.next();
            //System.out.println("removing " + key);
            testMap.remove(key);
            removed.add(key);
            someAdded.remove();
            assertFalse(testMap.containsKey(key));
        }
        // finish off that iterator
        while (someAdded.hasNext()) someAdded.next();
      
        // check the ones we removed are gone and the
        // ones we didn't are still there
        for (Iterator<String> it = added.iterator(); it.hasNext(); ) {
            String key = it.next();
            //System.out.println(key);
            assertTrue(testMap.containsKey(key));
            assertEquals(key, testMap.get(key));
        }
        for (Iterator<String> it = removed.iterator(); it.hasNext(); ) {
            String key = it.next();
            assertFalse(testMap.containsKey(key));
            assertEquals(null, testMap.get(key));
        }
       

        // add the rest
        
        while (source.hasNext()) {
            String x = source.next();
            added.add(x);
            testMap.put(x, x);
        }
  
        // check again
        for (Iterator<String> it = added.iterator(); it.hasNext(); ) {
            String key = it.next();
            assertTrue(testMap.containsKey(key));
            assertEquals(key, testMap.get(key));
        }
        for (Iterator<String> it = removed.iterator(); it.hasNext(); ) {
            String key = it.next();
            assertFalse(testMap.containsKey(key));
            assertEquals(null, testMap.get(key));
        }
        
        // remove them all
        for (Iterator<String> it = added.iterator(); it.hasNext(); ) {
            String key = it.next();
            testMap.remove(key);
            removed.add(key);
            it.remove();
        }
      
        // check again
        int i = 0;
        for (Iterator<String> it = added.iterator(); it.hasNext(); ) {
            String key = it.next();
            assertTrue(testMap.containsKey(key));
            assertEquals(key, testMap.get(key));
            i++;
        }
       assertEquals(0, i);
        for (Iterator<String> it = removed.iterator(); it.hasNext(); ) {
            String key = it.next();
            assertFalse(testMap.containsKey(key));
            assertEquals(null, testMap.get(key));
        }
    }        
}
