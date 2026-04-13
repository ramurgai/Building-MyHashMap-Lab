import java.util.LinkedList;
import java.util.Iterator;

/**
 * Rahul Murgai-Diaz
 * Course: Advanced Java Programming
 * Date: 4/9/2026-4/12/2026
 *
 
 * Creating a HashMap and implmenting put, get, containsKey, and remove. Uses these methods along with chaining with Linked Lists for full functionality.
 */

/*
*Is our overall hash map data structure
* @param <K> the keys for the map
* @param <V> the values for the map
*/
public class MyHashMap<K, V> {

    // ── Inner entry class ─────────────────────────────────────────────────
    /*
    * Class that stores the key-value pairs
    * @param <K> the keys for the map
    * @param <V> the values for the map
    */
    static class Entry<K, V> {
        K key;
        V value;

        /*
        *Actually creates entries with the keys and values
        * @param key the key for the entry
        * @param value the value for the entry assigned to that key
        */
        
        Entry(K key, V value) {
            this.key   = key;
            this.value = value;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────
    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 11;

    // ── Constructor ───────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    /*
    *Creates an empty hashmap to be used later and a size variable to be updated throughout
    */
    public MyHashMap() {
        table = new LinkedList[DEFAULT_CAPACITY];
        size  = 0;
    }

    // ── Hash helper ───────────────────────────────────────────────────────
    /*
    *The hashing formula that will allow us to get our index
    * @param key they key that we want to turn into an index
    * @return the integer that will be used as an index
    */
    private int hash(K key) {
        // Keys must not be null
        return Math.abs(key.hashCode()) % table.length;
    }

    // ── put ───────────────────────────────────────────────────────────────
    /*The put method uses .equals() instead of == to compare keys because == compares the memory address of two things instead of their actual value. For objects if you use == and they don't have the same memory address then you will get the wrong result. 
    *
    * Remove uses an iterator because if you used an enhanced for loop like in the many other methods and tried to remove things it would raise an exception because it messes with the iterator created for that enhanced for loop.
    *
    *Adds new entries to the hashmap. If the key already exists in the bucket the old value is overwritten.
    * @param key the unique key for the entry
    * @param value the value assigned to that key
    * @return the old value of a key or null if it's a new key
    */
    public V put(K key, V value) {
        // TODO Step 1: compute the index using hash(key)
        int index = hash(key);

        // TODO Step 2: if table[index] is null, create a new LinkedList there
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        // TODO Step 3: walk the list at table[index]
        //   -- compare keys using .equals(), not ==
        //   -- if an entry with the same key already exists, update its value
        //      and return the OLD value (do not increment size)
        for (Entry<K,V> entry : table[index]){
            if((entry.key).equals(key)){
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }

        // TODO Step 4: no existing entry found -- add a new Entry to the
        //   FRONT of the list (O(1) -- no traversal needed), increment size, return null

        table[index].addFirst(new Entry<>(key, value));
        size++;
        return null;
    }

    // ── get ───────────────────────────────────────────────────────────────
    /*
    *Returns the value assigned to a key
    * @param key the key to look up the value for
    * @return the value assigned to the key we searched with, or null if there is no value for that key
    */
    public V get(K key) {
        // TODO Step 1: compute the index using hash(key)
        int index = hash(key);
        // TODO Step 2: if table[index] is null, return null (key not present)
        if(table[index] == null){
            return null;
        }

        // TODO Step 3: walk the list at table[index]
        //   -- if an entry with a matching key is found, return its value
        for (Entry<K,V> entry : table[index]){
            if((key).equals(entry.key)){
                return entry.value;
            }
        }

        // TODO Step 4: key was not in the list -- return null
        return null;
    }

    // ── containsKey ───────────────────────────────────────────────────────
    /*
    *Returns true if the map has the key that is searched for
    * @param key the key that is being searched for
    * @return whether or not the key was in the HashMap
    */
    public boolean containsKey(K key) {
        // TODO: return true if the key exists in the map, false otherwise
        // Hint: get(key) returns null when the key is not present --
        //   but what if a key IS present and its value is null?
        //   Walk the list directly to be safe.

        int index = hash(key);
        
        for (Entry<K, V> entry : table[index]){
            if((entry.key).equals(key)){
                return true;
            }
        }

        return false; 
    }

    // ── remove ────────────────────────────────────────────────────────────
    /*
    *If the key is in the map it gets removed
    * @param key the key we want to be removed from the map
    * @return the old value of a key or null if it's wasn't there
    */
    public V remove(K key) {
        // TODO Step 1: compute the index using hash(key)
        int index = hash(key);

        // TODO Step 2: if table[index] is null, return null (nothing to remove)
        if (table[index] == null) {
            return null;
        }

        // TODO Step 3: walk the list at table[index]
        //   -- compare keys using .equals(), not ==
        //   -- if an entry with a matching key is found:
        //      remove it from the list, decrement size, return its value
        //   -- You cannot remove from a list inside a for-each loop.
        //      Use an iterator: Iterator<Entry<K,V>> it = table[index].iterator()
        //      then it.remove() when you find the match.
        Iterator<Entry<K,V>> it = table[index].iterator();
        while (it.hasNext()) {
            Entry<K, V> entry = it.next();
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                it.remove();
                size--;
                return oldValue;
            }
        }

        // TODO Step 4: key was not found -- return null

        return null; 
    }

    // ── size ──────────────────────────────────────────────────────────────
    /*
    *Returns the size of the HashMap based on the number of key-value pairs
    *@return the size of the HashMap based on the number of key-value pairs
    */
    public int size() {
        return size;
    }

    // ── isEmpty ───────────────────────────────────────────────────────────
    /*
    *Returns true if the HashMap has no key-value pairs
    * @return Returns true if the HashMap has no key-value pairs
    */
    public boolean isEmpty() {
        return size == 0;
    }

    // ── Test driver ───────────────────────────────────────────────────────
    /*
    *The main function that tests the HashMap functions
    * @param args commandline args
    */
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // put -- basic insertions
        map.put("cat", 1);
        map.put("dog", 2);
        map.put("rat", 3);
        map.put("bat", 4);
        map.put("ant", 5);
        System.out.println("Size after 5 insertions: " + map.size());   // 5


        // get -- existing keys
        System.out.println("get(cat): " + map.get("cat"));              // 1
        System.out.println("get(bat): " + map.get("bat"));              // 4

        // get -- missing key
        System.out.println("get(owl): " + map.get("owl"));              // null

        // put -- duplicate key (update)
        map.put("cat", 99);
        System.out.println("get(cat) after update: " + map.get("cat")); // 99
        System.out.println("Size after update: " + map.size());         // 5

        // containsKey
        System.out.println("containsKey(dog): " + map.containsKey("dog")); // true
        System.out.println("containsKey(elk): " + map.containsKey("elk")); // false

        // remove -- existing key
        map.remove("dog");
        System.out.println("get(dog) after remove: " + map.get("dog")); // null
        System.out.println("Size after remove: " + map.size());         // 4

        // remove -- missing key
        map.remove("owl");
        System.out.println("Size after removing missing key: " + map.size()); // 4

        // null value -- a key can legitimately map to null
        map.put("fox", null);
        System.out.println("get(fox): " + map.get("fox"));              // null
        System.out.println("containsKey(fox): " + map.containsKey("fox")); // true
        System.out.println("Size with null value: " + map.size());      // 5

        // forced collision -- "AaAaAa" and "BBBaaa" have the same hashCode in Java
        MyHashMap<String, Integer> collisionMap = new MyHashMap<>();
        collisionMap.put("AaAaAa", 1);
        collisionMap.put("BBBaaa", 2);
        System.out.println("Collision get(AaAaAa): " + collisionMap.get("AaAaAa")); // 1
        System.out.println("Collision get(BBBaaa): " + collisionMap.get("BBBaaa")); // 2
        collisionMap.remove("AaAaAa");
        System.out.println("After remove, get(BBBaaa): " + collisionMap.get("BBBaaa")); // 2
        System.out.println("After remove, size: " + collisionMap.size()); // 1
    }
}
