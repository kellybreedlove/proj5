package impl;

import java.util.ArrayList;
import java.util.Iterator;

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
         * The max number of items to be in this secondary map, in this case givenKeys.size()
         */
        int numItems;
        
        /**
         * Constructor. Given a set of keys, make appropriately
         * size arrays and a hash set that makes no collisions.
         * @param givenKeys
         */
        @SuppressWarnings("unchecked")
        SecondaryMap(Set<K> givenKeys) {
        	numItems = givenKeys.size();
        	m = numItems == 0 ? 1 : numItems * numItems;
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
    	   return keys[h.hash(key)] == key;                
       }

       /**
        * Remove the association for this key, if it exists.
        * @param key The key to remove
        */
       public void remove(K key) {
    	   if (containsKey(key)) {
    		   int index = h.hash(key);
    		   keys[index] = null;
    		   values[index] = null;
    	   }
       }

       /**
        * The iterator for this portion of the map.
        */
        public Iterator<K> iterator() {
        	int j = 0;
        	if (numItems > 0)
        		while (j < keys.length && keys[j] == null) j++;
            final int start = j;
            System.out.println("start: " + start + "     lenght: " + keys.length + "     numItems: " + numItems);
            
            return new Iterator<K>() {
            	
            	int i = start;
            	
				public boolean hasNext() {
					return i < keys.length && numItems != 0;
				}
				public K next() {
					int index = i++;
					while (i < keys.length-1 && keys[i] == null) i++;
					System.out.println("i: " + i + "    length: " + keys.length);
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
     * An extra value to mod by to avoid negative hash values
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
    	
    	// declare and initialize an array of sets
    	ArrayList<ListSet<K>> keySets = new ArrayList<ListSet<K>>();
    	for (int i = 0; i < m; i++) keySets.add(new ListSet<K>());

    	// populate it
    	for (K item: keys) keySets.get(h.hash(item)).add(item);

    	// initialize secondaries with collected sets
    	for (int i = 0; i < m; i++)
    		secondaries[i] = new PerfectHashMap.SecondaryMap(keySets.get(i)); 	
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
   }

   /**
    * Test if this map contains an association for this key.
    * @param key The key to test.
    * @return true if there is an association for this key, false otherwise
    */
    public boolean containsKey(K key) {
    	int index = h.hash(key);
        return secondaries[index].containsKey(key);
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
    	int j = 0;
    	while (j < secondaries.length && secondaries[j].numItems == 0) j++;
    	final int start = j;
        
    	return new Iterator<K>() {
        	int i = start;
        	Iterator<K> it = secondaries[i].iterator();
        	K item = it.next();  	
        	
			public boolean hasNext() {
				return item != null;
			}	
			public K next() {
				K temp = item;
				if (it.hasNext()) {
					item = it.next();
				} else if (i == secondaries.length && !it.hasNext()){
					item = null;				
				} else {
					i++;
					while (i < secondaries.length-1 && secondaries[i] == null) i++;
					it = secondaries[++i].iterator();
					item = it.next();
				}
				return temp;
			}	
			public void remove() {
				throw new UnsupportedOperationException();
			}       	
        };   
    }
}
