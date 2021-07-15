import java.util.ArrayList;

public class OrderedIndexableList<E extends Comparable> extends IndexableList {

  private int leftNumElements;
  private int rightNumElements;
  private Object[] leftArray;
  private Object[] rightArray;

  public OrderedIndexableList() {
  }

  public OrderedIndexableList(ArrayList<E> list) {

    for (E e : list) {
      add(e);
    }
  }

  public OrderedIndexableList(E[] array) {

    for (E e : array) {
      add(e);
    }
  }

  /* Inserts the specified element into the correct position
   * in this list.
   * Parameters:
   *     e - element to be appended to this list
   * Returns:
   *     true
   * TBT
   */
  public boolean add(E e) {

    int i;

    for (i = 0; i < size(); i++) {
      if (e.compareTo(get(i)) > 0) {
        break;
      }
    }

    add(i, e);
    return true;
  }
}
