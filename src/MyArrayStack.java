import java.util.NoSuchElementException;

/**
 * 用数组实现栈
 */
public class MyArrayStack<E> {
    private MyArrayList<E> list = new MyArrayList<>();

    public void push(E e) {
        list.addLast(e);
    }

    public E pop() {
        return list.removeLast();
    }

    public E peek() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.get(list.size() - 1);
    }
}
