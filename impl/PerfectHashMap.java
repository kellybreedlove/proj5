package impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import adt.Map;
import adt.Set;
import impl.ListSet;

/**
 * PerfectHashMap
 * 
 * Implementation of perfect hashing, that is, when the keys are known
 * ahead of time. Note that containsKey and get will work as expected
 * if used with a key that doesn't exist. However, we assume put
 * will never be called using a key that isn't supplied to the
 * constructor; behavior is unspecified otherwise.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * March 17, 2015
 * @param <K> The key-type of the map
 * @param <V>The value-type of the map
 */

public class PerfectHashMap<K, V> implements Map<K, V> {

 
    /**
     * Secondary maps for the buckets
     */
    private class SecondaryMap implements Map<K, V> {

        /**
         * The keys in this secondary map. This is necessary to
         * check when get and containsKey are called on spurious keys
         * and also for the iterator.
         */
        K[] keys;  

        /**
         * The values in the secondary map.
         */
        V[] values;

        /**
         * The number of slots in the arrays, computed as the square
         * of the number of keys that can go here.
         */
        int m;

        /**
         * The hash function, drawn from class Hpm
         */
        HashFunction<Object> h;
        
        /**
         * Constructor. Given a set of keys, make appropriately
         * size arrays and a hash set that makes no collisions.
         * @param givenKeys
         */
        @SuppressWarnings("unchecked")
        SecondaryMap(Set<K> givenKeys) {
        	m = givenKeys.size() == 0 ? 1 : givenKeys.size() * givenKeys.size();
        	keys = (K[]) new Object[m];
        	values = (V[]) new Object[m];
        	
        	h = UniversalHashFactory.makeHashFunction(p, m, f);
        	while (collisionCheck(h, givenKeys)) 
        		h = UniversalHashFactory.makeHashFunction(p, m, f);        	
        }

        /**
         * Check to see if the given hash function will result in any of the given 
         * keys colliding with one another.
         * @param h The hash function
         * @param givenKeys The keys
         * @return false if there are no collisions, true if there are collisions
         */
        private boolean collisionCheck(HashFunction<Object> h, Set<K> givenKeys) {
        	boolean[] hashedTo = new boolean[m];
        	for (K item: givenKeys) {
        		int index = h.hash(item);
        		if(hashedTo[index])
        			return true;
        		hashedTo[index] = true;
        	}
        	return false;
        }
        
        /**
         * Add an association to the map. We assume the given
         * key was known ahead of time.
         * @param key The key to this association
         * @param val The value to which this key is associated
         */  
        public void put(K key, V val) {
        	int index = h.hash(key);
        	keys[index] = key;
        	values[index] = val;
        }

        /**
         * Get the value for a key.
         * @param key The key whose value we're retrieving.
         * @return The value associated with this key, null if none exists
         */
       public V get(K key) {
    	   if (containsKey(key))
    		   return values[h.hash(key)];
    	   return null;
       }

       /**
        * Test if this map contains an association for this key.
        * @param key The key to test.
        * @return true if there is an association for this key, false otherwise
        */
       public boolean containsKey(K key) {
    	   return keys[h.hash(key)] != null;                
       }

       /**
        * Remove the association for this key, if it exists.
        * @param key The key to remove
        */
       public void remove(K key) {
    	   int index = h.hash(key);
    	   keys[index] = null;
    	   values[index] = null;
       }

       /**
        * The iterator for this portion of the map.
        */
        public Iterator<K> iterator() {
        	int j = 0;
            while (j < keys.length && keys[j] == null) j++;
            final int start = j;
            
            return new Iterator<K>() {
            	int i = start;
				public boolean hasNext() {
					return i < keys.length;
				}
				public K next() {
					int index = i;
					while (i < keys.length && keys[i] == null) i++;
					return keys[index];
				}
				public void remove() {
					throw new UnsupportedOperationException();
				}       	
            };  
        }     
    }

    /**
     * Secondary maps
     */
    private SecondaryMap[] secondaries;
    
    /**
     * An extra mod to keep from negative hash values
     */
    private int f;

    /**
     * A prime number greater than the greatest hash value
     */
    private int p;

    /**
     * A parameter to the hash function; here, the number of keys
     * known ahead of time.
     */
    private int m;

    /**
     * The hash function
     */
    private HashFunction<Object> h;
    
    /**
     * Constructor. Takes the keys (all known ahead of time) to
     * set things up to guarantee no collisions.
     * @param keys
     */
    @SuppressWarnings("unchecked")
    public PerfectHashMap(K[] keys) {
    	m = keys.length;
    	f = 100;
    	p = PrimeSource.nextOrEqPrime(f + 1);
    	h = UniversalHashFactory.makeHashFunction(p, m, f);
    	secondaries = (SecondaryMap[]) new PerfectHashMap.SecondaryMap[m];

    	// Go through all of the keys, note how many things hash to each available index
    	int[] collisionCounts = new int[m];
    	for (K item: keys) collisionCounts[h.hash(item)]++;

    	// for each position in secondaries
    	for (int i = 0; i < m; i++) {
    		
    		// create a set to be used when creating secondary map
    		ListSet<K> keySet = new ListSet<K>();
    		
    		while (collisionCounts[i] > 0) // # things left that map to i
    			for (K item: keys) // look for those things
    				if (h.hash(item) == i && !keySet.contains(item)) { // if this thing maps to i
    					keySet.add(item); // add it to the set for i
    					collisionCounts[i]--; // decrement # things left that map to i
    				}
    		
    		assert (collisionCounts[i] == 0);
    		secondaries[i] = new PerfectHashMap.SecondaryMap(keySet);
    	}
    	
    }
    
    /**
     * Add an association to the map. We assume the given
     * key was known ahead of time.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
    	int index = h.hash(key);
    	secondaries[index].put(key, val);
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
   public V get(K key) {
	   int index = h.hash(key);
       return secondaries[index].get(key);
	   //return null;
   }

   /**
    * Test if this map contains an association for this key.
    * @param key The key to test.
    * @return true if there is an association for this key, false otherwise
    */
    public boolean containsKey(K key) {
    	int index = h.hash(key);
        return secondaries[index].containsKey(key);
    	//return true;
    }

    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
    	int index = h.hash(key);
    	secondaries[index].remove(key);    
    }
    
    /**
     * Return an iterator over this map
     */
    public Iterator<K> iterator() {

        // TODO
        
        return null;
    }
 
}
