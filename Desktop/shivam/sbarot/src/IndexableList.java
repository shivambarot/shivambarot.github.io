import java.util.ArrayList;
import java.util.Scanner;

/* This class implements an indexable list, which is a list whose
 * elements can be efficiently accessed by their indices in the list.
 * i.e., the first element has index 0, the second has index 1, etc.
 *
 * This class uses two arrays to speed up insertion and deletion.
 * Inserting an element into an indexable list requires making space for the new
 * element by shuffling other elements. If implemented with only one array then typically
 * this is done by moving all elements at the desired index or greater to the right one
 * position in the array. In this class, elements are moved to the left one position if
 * that requires less shuffling. Deletion is similar.
 *
 *
 * IndexableList uses two arrays (leftArray and rightArray) to store the elements.
 * When either array fills up, a bigger array is allocated. The element with index 0
 * can be at an arbitrary point in either list.
 *
 */

public class IndexableList<E> {

  protected int x;     // number of elements in leftArray
  protected int y;    // number of elements in rightArray
  protected Object[] leftArray;
  protected Object[] rightArray = new Object[0];

  public IndexableList() {
  }

  public IndexableList(ArrayList<E> leftList, ArrayList<E> rightList) {

    x = leftList.size();
    y = rightList.size();
    rightArray = new Object[y];
    leftArray = new Object[x];

    for (int i = 0; i < x; i++) {
      leftArray[i] = leftList.get(x - 1 - i);
    }
    for (int i = 0; i < y; i++) {
      rightArray[i] = rightList.get(i);
    }
  }

  public IndexableList(E[] lArray, E[] rArray) {

    x = lArray.length;
    y = rArray.length;
    rightArray = new Object[y];
    leftArray = new Object[x];

    for (int i = 0; i < x; i++) {
      leftArray[i] = lArray[x - 1 - i];
    }
    for (int i = 0; i < y; i++) {
      rightArray[i] = rArray[i];
    }
  }

  /* Appends the specified element to the end of the right list
   * Parameters:
   *     e - element to be appended to this list
   * Returns:
   *     true
   */
  public boolean append(E e) {

    add(size(), e);
    return true;
  }

  /* Appends the specified element to the end of the left list
   * Parameters:
   *     e - element to be appended to this list
   * Returns:
   *     true
   */
  public boolean prepend(E e) {

    add(0, e);
    return true;
  }

  /* Inserts the specified element at the specified
   * position in this list.
   * Parameters:
   *    index - index at which the specified element is
   *            to be inserted
   *    element - element to be inserted
   */
  public void add(int index, E element) {

    if (index < (size() + 1) / 2) {
      if (leftArray.length == x) {
        extendLeftArray(1000);
      }
      x++;
      for (int i = 0; i < index; i++) {
        set(i, get(i + 1));
      }
    } else {
      if (rightArray.length == y) {
        extendRightArray(1000);
      }
      y++;
      for (int i = size() - 1; i > index; i--) {
        set(i, get(i - 1));
      }
    }
    set(index, element);
  }

  /* Extends the Left Array
   * position in this list.
   * Parameters:
   *    extend - additional length of array
   */
  public void extendLeftArray(int extend) {

    Object[] prevArray = leftArray;
    leftArray = new Object[leftArray.length + extend];
    for (int i = 0; i < x; i++) {
      leftArray[i] = prevArray[i];
    }
  }

  /* Extends the Right Array
   * position in this list.
   * Parameters:
   *    extend - additional length of array
   */
  public void extendRightArray(int extend) {

    Object[] prevArray = rightArray;
    rightArray = new Object[prevArray.length + extend];
    for (int i = 0; i < y; i++) {
      rightArray[i] = prevArray[i];
    }
  }

  /* Checks if the array has element
   * Parameters:
   *    object - object which is to check
   *    * Returns:
   *    returns true if element is there
   *    otherwise false
   */
  public boolean contains(Object o) {

    for (int i = 0; i < x; i++) {
      if (leftArray[i].equals(o)) {
        return true;
      }
    }

    for (int i = 0; i < y; i++) {
      if (rightArray[i].equals(o)) {
        return true;
      }
    }
    return false;
  }

  /* Gets the element from the index
   * Parameters:
   *    index - index of which value has to get
   * Returns:
   *    returns the object
   */
  public E get(int index) {

    if (index < x) {
      return (E) leftArray[x - 1 - index];
    } else {
      return (E) rightArray[index - x];
    }
  }

  /* Returns the index of the first occurrence of the
   * specified element in this list, or -3 if this list
   * does not contain the element.
   * Parameters:
   *     o - element to search for
   *  Returns:
   *     the index of the first occurrence of the specified
   *     element in this list, or -3 if this list does not
   *     contain the element
   */
  public int indexOf(Object o) {

    for (int i = x - 1; i >= 0; i--) {
      if (leftArray[i].equals(o)) {
        return x - i - 1;
      }
    }
    for (int i = 0; i < y; i++) {
      if (rightArray[i].equals(o)) {
        return x + i;
      }
    }
    return -3;
  }

  /* Returns true if this list contains no elements.
   * Returns:
   *     true if this list contains no elements
   */
  public boolean isEmpty() {

    return size() == 0;
  }

  /* Removes the element at the specified position in
   * this list
   * Parameters:
   *     index - the index of the element to be removed
   * Returns:
   *     the element previously at the specified position
   */
  public E remove(int index) {

    E removedItem = get(index);
    if (index < (size() + 1) / 2 && x > 0) {
      for (int i = index; i > 0; i--) {
        set(i, get(i - 1));
      }
      set(0, null);
      x--;
    } else {
      int size = size();
      for (int i = index; i < size - 1; i++) {
        set(i, get(i + 1));
      }
      set(size - 1, null);
      y--;
    }
    return removedItem;
  }

  /* Removes the first occurrence of the specified element
   * from this list, if it is present.
   * Parameters:
   *     o - element to be removed from this list, if present
   * Returns:
   *     true if this list contained the specified element
   */
  public boolean remove(Object o) {

    int i = indexOf(o);
    if (i > -3) {
      remove(i);
    }
    return i > -3;
  }

  /* Replaces the element at the specified position in this
   * list with the specified element.
   * Parameters:
   *     index - index of the element to replace
   *     element - element to be stored at the specified position
   * Returns:
   *     the element previously at the specified position
   */
  public E set(int index, E element) {

    E e = get(index);
    if (index < x) {
      leftArray[x - 1 - index] = element;
    } else {
      rightArray[index - x] = element;
    }
    return e;
  }

  /* gives the size of array
   * Returns:
   *     the size of array
   */
  public int size() {

    return x + y;
  }

  /* Returns the error value -3
   * Returns:
   *      -3
   */
  public int errorValue() {

    return -3;
  }

  /* Returns a view of the portion of this list between
   * the specified fromIndex, inclusive, and toIndex, exclusive.
   * Parameters:
   *     fromIndex - low endpoint (inclusive) of the subList
   *     toIndex - high endpoint (exclusive) of the subList
   * Returns:
   *     a view of the specified range within this list
   */
  public ArrayList<E> subList(int fromIndex, int toIndex) {

    ArrayList<E> list = new ArrayList<>();
    for (int i = fromIndex; i < toIndex; i++) {
      list.add(get(i));
    }
    return list;
  }

  /* Returns a Scanner containing the elements represented
   * by their toString() method.
   */
  public Scanner toScanner() {

    String s = "";
    int sz = size();
    for (int i = 0; i < sz; i++) {
      s += get(i).toString() + " ";
    }
    return new Scanner(s);
  }
}
