package impl;

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
        	m = givenKeys.size() * givenKeys.size();
        	keys = (K[]) new Object[m];
        	values = (V[]) new Object[m];
        	
        	h = UniversalHashFactory.makeHashFunction(p, m);
        	while (collisionCheck(h, givenKeys)) 
        		h = UniversalHashFactory.makeHashFunction(m, m);        	
        }

        /**
         * Check to see if the given hash function will result in any of the given 
         * keys colliding with one another.
         * @param h The hash function
         * @param givenKeys The keys
         * @return false if there are no collisions, true if there are collisions
         */
        private boolean collisionCheck(HashFunction<Object> h, Set<K> givenKeys) {
        	boolean[] temp = new boolean[m];
        	for (K item: givenKeys) {
        		if(temp[h.hash(item)])
        			return true;
        		temp[h.hash(item)] = true;
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
    		   return values[h.hash	(key)];
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
            
            // TODO

            // In theory you don't need to write this; all you need
            // to support is PerfectHashMap.iterator().
            // However, in my version the iterator for PerfectHashMap
            // relied on iterators of the secondary maps.
            // You could use a different approach.
            
            return null;
        
        }
        
    }

    /**
     * Secondary maps
     */
    private SecondaryMap[] secondaries;

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
    	p = PrimeSource.nextOrEqPrime(m + 1);
    	h = UniversalHashFactory.makeHashFunction(p, m);
    	
    	secondaries = (SecondaryMap[]) new PerfectHashMap.SecondaryMap[m];
    	for (K item: keys) {
    		int index = h.hash(item);
    		Set<K> set = new ListSet<K>();
    		
    		// no collisions yet
    		if (secondaries[index] == null) {
    			set.add(item);
    			secondaries[index] = new PerfectHashMap.SecondaryMap(set);
    		} 
    		// there's  a collision
    		else {
    			set.add(item);
    			for (int i = 0; i < secondaries[index].keys.length; i++)
    				set.add(secondaries[index].keys[i]);
    			secondaries[index] = new PerfectHashMap.SecondaryMap(set);
    		}	
    	}	
    }
    
    /**
     * Add an association to the map. We assume the given
     * key was known ahead of time.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
    	//int index = h.hash(key);
    	//secondaries[index].put(key, val);
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
   public V get(K key) {
	   //int index = h.hash(key);
       //return secondaries[index].get(key);
	   return null;
   }

   /**
    * Test if this map contains an association for this key.
    * @param key The key to test.
    * @return true if there is an association for this key, false otherwise
    */
    public boolean containsKey(K key) {
    	//int index = h.hash(key);
        //return secondaries[index].containsKey(key);
    	return true;
    }

    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
    	//int index = h.hash(key);
    	//secondaries[index].remove(key);    
    }
    
    /**
     * Return an iterator over this map
     */
    public Iterator<K> iterator() {

        // TODO
        
        return null;
    }
 
}
