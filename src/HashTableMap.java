// --== CS400 File Header Information ==--
// Name: Divyangam Dutta
// Email: ddutta5@wisc.edu
//

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.lang.Math;


public class HashTableMap<keyType, valueType> implements MapADT {
  class Node<KeyType, ValueType> // Self-defined class to store key-value pair.
  {
    public KeyType key;
    public ValueType value;

    Node(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }
  }

  int capacity;
  private LinkedList<Node> table[]; // Use private array instance field to store key-value pairs.

  // constructors
  @SuppressWarnings("unchecked")
  public HashTableMap(int capacity) {
    this.capacity = capacity;
    table = new LinkedList[this.capacity];
    for (int i = 0; i < table.length; i++) {
      table[i] = new LinkedList<Node>();
    }
  }


  @SuppressWarnings("unchecked")
  public HashTableMap() {
    this.capacity = 10; // Default capacity = 10
    table = new LinkedList[this.capacity];
    for (int i = 0; i < table.length; i++) {
      table[i] = new LinkedList<Node>();
    }
  }

  // method to clear the hash table
  public void clear() {
    for (int i = 0; i < capacity; i++) {
      for (int j = 0; j < table[i].size(); j++) {
        table[i].remove(j); // using linkedlist.remove to delete all elements

      }
    }
  }

  // method to returns the number of key-value pairs stored
  public int size() {
    int count = 0;

    for (int i = 0; i < capacity; i++) {
      for (int j = 0; j < table[i].size(); j++) {
        if ((table[i].get(j)) != null)
          count++; // incrementing counter to find number of pairs
      }

    }
    return count;
  }

  // method that returns a reference to the value associated with the key that is being removed.
  @SuppressWarnings("unchecked")
  public valueType remove(Object key) {
    int index = Math.abs((key.hashCode()) % capacity);
    valueType value;
    for (int i = 0; i < table[index].size(); i++) {

      if (table[index].get(i).key == key) { //searching the key 
        value = (valueType) table[index].get(i).value;
        table[index].remove(i);
        return value;
      }
    }
    return null;
  }

  // private helper method for doubling and rehashing
  @SuppressWarnings("unchecked")
  private void resize() {
    
    int count = this.size();
    float loadFactor = (float) count / (float) capacity;
    keyType key;
    valueType value;
    int s = 0;

    Node temp[] = new Node[this.capacity]; // temporary array

    if (loadFactor >= 0.85) { // when the load capacity greater than or equal to 85%, rehashing it.
      for (int i = 0; i < (capacity); i++) {
        for (int j = 0; j < table[i].size(); j++) {
          if ((table[i].get(j)) != null) {
            key = (keyType) table[i].get(j).key;
            value = (valueType) table[i].get(j).value;
            temp[s++] = new Node(key, value);
            // copying all the pairs in the new array
          }
        }
      }
      
      capacity = capacity*2;
      table = new LinkedList[capacity]; // doubling
      for (int i = 0; i < table.length; i++) {
        table[i] = new LinkedList<Node>();
      }

      for (int i = 0; i < s; i++) {
        put((keyType) temp[i].key, (valueType) temp[i].value);
      }
    }
  }
 
 //searching the key
  @SuppressWarnings("unchecked")
  public boolean containsKey(Object key) {
    keyType search;
    for (int i = 0; i < this.capacity; i++) {
      for (int j = 0; j < table[i].size(); j++) {
        search = (keyType) (table[i].get(j)).key;
        if (search.equals(key)) { //if found return true
          return true;
        }
      }
    }
    return false;
  }

  // method to store a new key-value pair
  @SuppressWarnings("unchecked")
  public boolean put(Object key, Object value) {
    int index;
    if (containsKey(key) || key == null) {
      return false;
    }
    index = Math.abs((key.hashCode()) % capacity);
    table[index].add(new Node(key, value)); // using linkedlist.add to add a new key-pair
    resize(); // calling the resize method to check the load factor
    return true;
  }

  // method to return a value from the array using the given key
  @SuppressWarnings("unchecked")
  public valueType get(Object key) throws NoSuchElementException {
    int index = Math.abs((key.hashCode()) % capacity);
    for (int i = 0; i < table[index].size(); i++) {

      if (table[index].get(i).key.equals(key)) // search for key
      {
        
        return (valueType) table[index].get(i).value;
      }

    }
    throw new NoSuchElementException("Element not found");
    // Throws Exception whenever element is not found.
  }
}
