package adts;

import java.util.Iterator;

/**
 * Represents a simple collection of objects. The interface
 * does not include any functionality related to indices of
 * an underlying structure.
 *
 * @author Josh Archer
 * @version 1.0
 */
public interface ISet<T> extends Iterable<T>
{
    /**
     * Adds an element to the collection. No specific ordering
     * is required.
     *
     * @throws IllegalArgumentException thrown when a duplicate
     * element is added to the table
     * @param element the new element to put in the collection
     */
    void add(T element);

    /**
     * Adds a group of elements to the set in a single operation.
     * The structure internals should behave exactly as described
     * in the add(T element) method.
     *
     * @throws IllegalArgumentException thrown when a duplicate
     *      element is added to the table
     * @param elements a variable number of elements to add to the
     *      set
     */
    void add(T... elements);

    /**
     * Finds and removes an element from the collection.
     *
     * @throws java.util.NoSuchElementException thrown when the
     * element is not found in the collection
     * @param element the element to remove
     */
    void remove(T element);

    /**
     * Reports whether the collection contains an element.
     *
     * @param element the element to search for.
     * @return true if the element is found, otherwise false
     */
    boolean contains(T element);

    /**
     * Returns the number of elements in the collection.
     *
     * @return the size
     */
    int size();

    /**
     * Returns the available space in the underlying structure
     * that can be used to store new elements.
     *
     * @return the capacity
     */
    int capacity();

    /**
     * Reports whether the collection is empty or not.
     *
     * @return true if the collection is empty, otherwise false
     */
    boolean isEmpty();

    /**
     * Removes all elements from the collection.
     */
    void clear();

    /**
     * Returns an element in the collection that matches the
     * input parameter according the equals() method of the
     * parameter.
     * Note: This method should not return a direct reference
     * to the input element, without finding it first in the
     * collection. This method will be used directly in part #2
     * of our assignment.
     *
     * @param element an element to search for
     * @return a matching element
     */
    T find(T element);

    /**
     * Returns an iterator over the collection.
     *
     * @return an object using the Iterator<T> interface
     */
    @Override
    Iterator<T> iterator();
}