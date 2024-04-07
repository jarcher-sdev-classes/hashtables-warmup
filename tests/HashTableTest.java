import adts.ISet;
import hashtables.HashTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These test verify a HashTable structure using
 * separate chaining.
 *
 * @author Josh Archer
 * @version 1.0
 */
public class HashTableTest {
    public static final double LOAD_FACTOR = 0.5;
    private ISet<String> table;
    private final String[] testValues = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                             "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                             "u", "v", "w", "x", "y", "z"};

    /**
     * Creates a new empty table.
     */
    @BeforeEach
    public void setup() {
        table = getTable();
    }

    private ISet<String> getTable() {
        return new HashTable<>(LOAD_FACTOR);
    }

    private ISet<TestClass> getTableTestClass() {
        return new HashTable<>(LOAD_FACTOR);
    }

    private void addTestElements() {
        //add a few elements
        for (String element : testValues) {
            table.add(element);
        }
    }

    /**
     * Verifies that elements can be added and detected in the table.
     */
    @Test
    public void addTest() {
        addTestElements();
        verifyTable();
    }

    private void verifyTable() {
        //testValues the size
        assertEquals(testValues.length, table.size(), "size() is incorrect after using add()");

        //verify each element is in the table
        for (String element : testValues) {
            assertTrue(table.contains(element), "element is missing after adding to hash table");
        }
    }

    /**
     * Verifies that multiple elements can be added and detected in the table.
     */
    @Test
    public void addMultipleTest() {
        table.add(testValues);
        verifyTable();
    }

    /**
     * Checks whether duplicates are rejected by the hash table.
     */
    @Test
    public void addDuplicatesTest() {
        addTestElements();

        for (String element : testValues) {
            assertThrows(IllegalArgumentException.class, () -> table.add(element));
        }
    }

    /**
     * Checks whether duplicates are rejected by the hash table
     * when passing in a group of new values.
     */
    @Test
    public void addMultipleDuplicatesTest() {
        addTestElements();
        assertThrows(IllegalArgumentException.class, () -> table.add(testValues));
    }

    /**
     * Tests the removal of existing elements in the table.
     *
     * @param value a value to remove from the set
     */
    @ParameterizedTest
    @ValueSource(strings = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z"})
    public void removeTest(String value)
    {
        addTestElements();

        table.remove(value);

        //verify that the value is removed
        assertEquals(testValues.length - 1, table.size(), "size() is incorrect after calling remove()");
        assertFalse(table.contains(value),
                "element removed is still inside the table: " + value);
    }

    /**
     * Tests that missing elements throw the expected exception.
     */
    @Test
    public void removeMissingElementTest() {
        addTestElements();
        assertThrows(NoSuchElementException.class, () -> table.remove("!"));
    }

    /**
     * Tests whether existing elements can be found in the table.
     */
    @Test
    public void containsExistsTest() {
        addTestElements();

        //look for present elements
        for (String element : testValues) {
            assertTrue(table.contains(element), "table does not contain elements that have been added");
        }
    }

    /**
     * Tests whether missing elements can be found in the table.
     */
    @Test
    public void containsMissingTest() {
        addTestElements();

        //look for missing elements
        assertFalse(table.contains("!"), "table should not report missing elements as present in the table");
    }

    /**
     * Verifies that size() and isEmpty() report correctly with an empty table
     */
    @Test
    public void emptyTableTest() {
        assertTrue(table.isEmpty(), "table should be empty at first");
        assertEquals(0, table.size(), "table should have size zero with no elements");
    }

    /**
     * Verifies that size() and isEmpty() report correct as a table changes.
     */
    @Test
    public void resizingTableTest() {
        //add elements and see if size changes
        for (int i = 0; i < testValues.length; i++) {
            table.add(testValues[i]);
            assertEquals(i + 1, table.size(), "size() incorrect after calling add");
        }

        //remove elements and see if size changes
        for (int i = testValues.length - 1; i >= 0; i--) {
            table.remove(testValues[i]);
            assertEquals(i, table.size(), "size() incorrect after calling add");
        }
    }

    /**
     * Verifies that clear() actually removes all elements from the table
     */
    @Test
    public void clearTest() {
        addTestElements();
        table.clear();

        //the table should be empty now
        assertTrue(table.isEmpty(), "table should be empty after calling clear()");
        assertEquals(0, table.size(), "table should have size zero after calling clear()");

        //no elements should be present
        for (String testValue : testValues) {
            assertFalse(table.contains(testValue), "table is reporting elements after clear() is called");
        }
    }

    /**
     * Verifies that an element can be retrieved from the table using the find() method.
     */
    @Test
    public void getExistingTest() {
        //add a table with an element
        ISet<TestClass> testTable = getTableTestClass();
        TestClass testElement = new TestClass(1, 2);
        testTable.add(testElement);

        assertEquals(testElement, testTable.find(testElement), "Element cannot be found with the find() method");
    }

    /**
     * Verifies that a missing element will return null when given to the find() method.
     */
    @Test
    public void getMissingTest() {
        //add a table without an element
        ISet<TestClass> testTable = getTableTestClass();
        TestClass testElement = new TestClass(1, 2);

        assertNull(testTable.find(testElement), "find() does not return null for a missing element");
    }

    /**
     * Verifies that using the find() method will not just return
     * the input parameter reference.
     */
    @Test
    public void getNoReferenceTest() {
        //add a table
        ISet<TestClass> testTable = getTableTestClass();

        //two elements that are equal according to equals()
        TestClass firstElement = new TestClass(1, 2);
        TestClass secondElement = new TestClass(1, 4);

        //first element is in the table, not second
        testTable.add(firstElement);

        assertNotSame(secondElement, testTable.find(secondElement),
            "Element in table is not returned when calling the find() method");
    }

    /**
     * Verifies that a new iterator can be retrieved on an empty table
     */
    @Test
    public void emptyTableIteratorTest() {
        //do we have an iterator?
        assertNotNull(table.iterator(), "iterator is returned null with no elements in the table");
    }

    /**
     * Verifies that a new iterator can be retrieved on an empty table
     */
    @Test
    public void emptySetIteratorTest() {
        Iterator<String> iter = table.iterator();
        assertFalse(iter.hasNext(), "iterator reports elements for an empty set");
    }

    /**
     * Verifies that all elements in the table can be returned (in any order).
     */
    @Test
    public void iteratorTest() {
        addTestElements();

        //store an array of flags - true: found, false: not-found
        boolean[] found = new boolean[testValues.length];
        for (String element : table) {
            //find the element, mark it as found
            for (int i = 0; i < testValues.length; i++) {
                if (testValues[i].equals(element)) {
                    if (found[i]) {
                        fail("duplicate element found with iterator: " + element);
                    }
                    else {
                        found[i] = true;
                    }
                }
            }
        }

        //check that each was found
        for (int i = 0; i < found.length; i++) {
            if (!found[i]) {
                fail("element not found with iterator: " + testValues[i]);
            }
        }
    }

    /**
     * Verifies that you cannot call the add() method while using an iterator.
     */
    @Test
    public void concurrentAddTest() {
        addTestElements();

        Iterator<String> iter = table.iterator();
        assertThrows(ConcurrentModificationException.class, () -> {
            //modify the structure after accessing the iterator
            table.add("!");

            boolean more = iter.hasNext(); //this should throw an exception
            if (more) {
                String value = iter.next(); //or this should throw an exception
                fail(value + " found with an iterator after concurrent modification with add()");
            } else {
                fail("The iterator is not report elements that can be found in the structure");
            }
        });
    }

    /**
     * Verifies that you cannot call the remove() method while using an iterator.
     */
    @Test
    public void concurrentRemoveTest() {
        addTestElements();

        Iterator<String> iter = table.iterator();
        assertThrows(ConcurrentModificationException.class, () -> {
            //modify the structure after accessing the iterator
            table.remove("a");

            boolean more = iter.hasNext(); //this should throw an exception
            if (more) {
                String value = iter.next(); //or this should throw an exception
                fail(value + " found with an iterator after concurrent modification with remove()");
            } else {
                fail("The iterator is not report elements that can be found in the structure");
            }
        });
    }

    /**
     * Verifies that you cannot call the clear() method while using an iterator.
     */
    @Test
    public void concurrentClearTest() {
        addTestElements();

        Iterator<String> iter = table.iterator();
        assertThrows(ConcurrentModificationException.class, () -> {
            //modify the structure after accessing the iterator
            table.clear();

            boolean more = iter.hasNext(); //this should throw an exception
            if (more) {
                String value = iter.next(); //or this should throw an exception
                fail(value + " found with an iterator after concurrent modification with remove()");
            } else {
                fail("The iterator is not report elements that can be found in the structure");
            }
        });
    }

    private static class TestClass {
        private int identifier;
        private int value;

        public TestClass(int identifier, int value) {
            this.identifier = identifier;
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            TestClass testClass = (TestClass) other;
            return identifier == testClass.identifier;
        }

        @Override
        public int hashCode() {
            return identifier;
        }

        @Override
        public String toString() {
            return "TestClass{" +
                    "identifier=" + identifier +
                    ", value=" + value +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "A group of tests for a set class";
    }
}
