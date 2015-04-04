package impl;

import java.util.Iterator;

import adt.Map;

/**
 * BasicHashMap
 * 
 * This is my implementation of hashing from CSCI 245,
 * made to fit into the Map interface for this course.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 30, 2014
 * @param <K> The key-type of the map
 * @param <V> The value-type of the map
 */

public class BasicHashMap<K, V> implements Map<K, V> {

    private static class Node<KK, VV> {
        KK key;
        VV val;
        Node<KK, VV> next;
        public Node(KK key, VV val, Node<KK, VV> next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
        public String toString() {
            return "(" + key + " " + val + ")";
        }
    }

    /**
     * The hash buckets
     */
    private Node<K, V>[] buckets;

    /**
     * Constructor.
     * @param size The number of buckets to use.
     */
    @SuppressWarnings("unchecked")
    public BasicHashMap(int size) {
        buckets = new Node[size];
    }
 
    /**
     * Find a hash for this string to be used as an index.
     * Use the absolute value of the Java-provided hashcode, 
     * then mod by the number of buckets. See Sedgewick pgs
     * 478-479 for the weakness of this approach.
     * @param str The key
     * @return The bucket
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }
    

    /**
     * Helper method to find the node with an association
     * for the given key, if any.
     * Find the right bucket, then loop over the list, 
     * in that bucket, checking the key for each node.
     * @param key The key whose node we're retrieving
     * @return The node for that key, null if none exists
     */
   private Node<K, V> getNode(K key) {
        for (Node<K, V> current = buckets[hash(key)]; 
            current != null; current = current.next) {
            if (key.equals(current.key)) return current; 
        } 
         return null;                                                        
    }

   /**
    * Add an association to the map.
    * @param key The key to this association
    * @param val The value to which this key is associated
    */  
   public void put(K key, V val) {
       Node<K, V> association = getNode(key);// Old node having this key 
       if (association != null)  
           association.val = val;
       else {                                                               
           int index = hash(key);                                           
           buckets[index] = new Node<K, V>(key, val, buckets[index]);           
       } 
   }

   /**
    * Get the value for a key.
    * @param key The key whose value we're retrieving.
    * @return The value associated with this key, null if none exists
    */   
   public V get(K key) {
       Node<K, V> association = getNode(key); // Node having this key  
       if (association == null) return null; 
       else return association.val;       
   }

   /**
    * Test whether an association exists for this key.
    * See whether there is a node for this key.
    * @param key The key to remove
    * @return true if there is an association for this key, false otherwise
     */   
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
        int index = hash(key);
        if (buckets[index] == null)
            return;
        else if (buckets[index].key.equals(key)) 
            buckets[index] = buckets[index].next;
        else
            for (Node<K, V> current = buckets[index]; 
            current.next != null; current = current.next)
                if (key.equals(current.next.key)) {     
                    current.next = current.next.next;   
                    return; 
                }
    }

    /**
     * Get an iterator for all the keys in this map.
     * Make an anonymous inner class which keeps track
     * of a position in the array of buckets and the current list 
     * @return An iterator over the set of keys.
     */
    public Iterator<K> iterator() {
        //final Iterator<Node> temp = buckets.iterator();
        int i = 0;
        Node<K, V> initialNode = null;
        while (i < buckets.length && initialNode == null) {
            initialNode = buckets[i];
            i++;
        }
        final Node<K, V> n = initialNode;
        final int j = i;
        
        return new Iterator<K>() {
            /**
             * The node of the next key to return
             */
            Node<K, V> next = n;

            /**
             * The bucket after the one containing the current node.
             */
            int pos = j;
 
            // Invariants: 
            // -- next is always referring to a non-null
            //    node, except when we are finished with the 
            //    iteration.
            // -- pos is always referring to the bucket
            //    coming after the bucket
            //    containing the node that next is pointing to
            
            public boolean hasNext() {
                return next != null; 
            }

            /**
             * In keeping with the invariant, we first move next
             * along to the next node in the list/bucket. If it turns
             * out that it's the last one in that bucket, we 
             * start looking in the next buckets, which means
             * we increment pos, also.
             */
            public K next() {
                K toReturn = next.key;
                next = next.next;
                // If the first condition fails, we quit the
                // look because we've found an appropriate next
                // node (ready for next call of next(). 
                // If the second condition fails, it's because
                // there are no more nodes left (iteration's done).
                while (next == null && pos < buckets.length) {
                    next = buckets[pos];
                    pos++;
                }
                return toReturn;
            }

            public void remove() {
                throw new UnsupportedOperationException();            
            }
            
        };
    }

}
