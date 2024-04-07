<img src="imgs/ht_2.jpg" style="width: 500px;">

# Hash Tables Part #2

For this assignment, you will be building a basic hash table using open addressing with linear 
probing. In open addressing tables, all elements are stored within the array itself, and 
collisions are resolved by moving to the next available slot in the array according to a 
probing sequence. 

Linear probing handles collisions by placing the collided item in the 
next empty slot found linearly in the table. It's crucial to manage the load factor properly 
in such tables to maintain efficient operations, since a high load factor can lead to many 
collisions, which in turn degrade the performance of the hash table.

## Objectives

**Course:**
- Develop a set or map data structure based on hash tables and implement basic set operations.

**Module:**
- To utilize hash codes for quickly determining new or existing elements' positions in a data structure.
- To resolve collisions using open addressing with linear probing.
- To write an iterator over a complex structure.

## Open Addressing with Linear Probing

Unlike separate chaining, where a linked list is used at each index to handle collisions, 
open addressing with linear probing places all elements directly in the hash table array. 

When adding an element and a collision occurs, the table is probed linearly (i.e., checking 
the next index) until an empty slot is found. This method ensures all table slots are utilized, 
reducing wasted space.

Note: It may sometimes be necessary to search the table around its boundaries. In such cases
the probing should using the modulus operator to ensure that the next index will never be invalid.
For example, to move to the next higher index, you could do the following:

```java
int nextIndex = (currentIndex + 1) % table.length
```

## Writing the Hash Table

Your task is to implement the open-addressing hash table with linear probing as described 
above. Some starter files have been provided for you:

- **ISet:** An interface that your data structure should implement. This defines the methods
  in the hash table and how they should behave. 
    - Note: Your hash table will be based on a 
    <a href="https://en.wikipedia.org/wiki/Set_(mathematics)">mathematical set</a> structure. 
- **HashTable:** The data structure you will implement. The class should use Java generics
  for elements stored in the structure and should also provide an iterator over the 
  data.
- **HashTableTest:** A group of JUnit 5 tests that can be used to verify your work.
- **EmpiricalAnalysis:** A driver class that will be used to analyze your work (see below).

## Verification of the HashTable

When complete, all unit tests in the HashTableTest file should have a pass rating.

Your code is also expected to pass a linter check, as previously covered. Any submissions
with outstanding linter problems will be returned to a student for revision.

### Why is Load Factor Important?

The load factor is a key determinant of the hash table's performance. It is a number that
determines how full a hash table can become, whether you are using separate chaining or
open addressing for the hash table. 

The load factor of a table is defined as: $y = \frac{x^2}{4}$

```
$$
y = \frac{x^2}{4}
$$
```

A high load factor indicates that the table is getting full, 
which increases the likelihood of collisions.
This, in turn, means that linear probing will need to check more slots on average to find
an empty slot or to locate an element, leading to decreased performance. Keeping the load
factor under a certain threshold helps maintain efficient operation.

## Requirements

Submissions not adhering to the following requirements will receive an immediate zero:

- Your hash table class must be generic.
- Your hash table class must implement the `ICollection<T>` interface, using the generic type 
  of the interface.
- Your iterator should be a private inner class, invisible outside of your `OpenAddressHashTable<T>` 
  class.
- You must use open addressing with linear probing to resolve collisions.
- You must provide a set of unit tests for your hash table.

## Total Points: 100.0

(Include the same criteria ratings and points distribution as in the provided assignment, 
tailored for open addressing with linear probing)

## Resizing the Hash Table and Understanding Load Factor

A critical aspect of maintaining the efficiency of an open addressing hash table is managing 
its load factor. The load factor is a measure that indicates how full the hash table is. 
It is calculated as the ratio of the number of elements stored in the table to the total 
number of slots available in the table. For this assignment, we will maintain a load factor 
of 60%. This means that when 60% of the table's slots are filled, the table should be resized 
to ensure that it continues to operate efficiently.

### Why is Load Factor Important?

The load factor is a key determinant of the hash table's performance. A high load factor 
indicates that the table is getting full, which increases the likelihood of collisions. 
This, in turn, means that linear probing will need to check more slots on average to find 
an empty slot or to locate an element, leading to decreased performance. Keeping the load 
factor under a certain threshold helps maintain efficient operation.

### Resizing the Table:

When the load factor exceeds 60%, it's time to resize the hash table to accommodate more 
elements. Resizing involves creating a new, larger array and then rehashing all existing 
elements into this new array. Rehashing is necessary because the position where an element 
is stored is directly tied to the size of the array. As the array size changes, so does the 
index calculated for each element.

#### Hint for Implementing Resizing:

1. **Detect when to Resize:** Keep track of the number of elements in the table and the 
   size of the internal array. When `(number of elements / size of the internal array) > 0.6`,
   initiate the resize process.
2.  **Creating a New Array:** Determine the new size of the array. It's common to double 
   the size of the array to ensure that the resized table can accommodate future insertions 
   without needing to resize too frequently.
3. **Rehashing Elements:** For each element in the old array, calculate its new position in 
   the new, larger array using the hash function. Since the array size has changed, the result 
   of the hash function (modulo the new array size) will likely be different for many elements.
4. **Insert Elements into the New Array:** Insert each element into its new position in the 
   new array. Remember, collisions will still occur, so linear probing will be necessary 
   during this reinsertion process.
5. **Switch to the New Array:** Once all elements have been rehashed and inserted into the 
   new array, the new array becomes the active table.

By following these guidelines, you can ensure that your open addressing hash table dynamically 
adjusts its size to maintain a balance between space efficiency and operational efficiency, 
thus handling an arbitrary number of elements effectively.