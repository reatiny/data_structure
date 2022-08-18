import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<E> implements Iterable<E> {
    private E[] data;   // 真正存储数据的底层数组
    private int size;   // 记录当前元素个数
    private static final int INIT_CAP = 1;  // 默认初始容量

    public MyArrayList() { this(INIT_CAP); }

    public MyArrayList(int initCapacity) {
        data = (E[])new Object[initCapacity];
        size = 0;
    }


    /**** 增 ****/
    public void addLast(E e) {
        int cap = data.length;
        if (size == cap) {
            grow(2 * cap);
        }

        data[size] = e;
        size++;
    }

    public void add(int index, E e) {
        checkPositionIndex(index);
        int cap = data.length;
        if (size == cap) {
            grow(2 * cap);
        }

        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = e;
        size++;
    }


    /**** 删 ****/
    // 删除数组的最后一个元素并返回
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int cap = data.length;
        if (size < cap / 4) {
            grow(cap / 2);  // 缩容操作 不同于官方的 trimToSize()方法
        }

        E deletedVal = data[size - 1];
        data[size - 1] = null;
        size--;

        return deletedVal;
    }

    public E remove(int index) {
        checkElementIndex(index);

        E deletedVal = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[index] = null;
        size--;

        return deletedVal;
    }


    /**** 改 ****/
    // 将索引 index 的元素修改为 element 并返回之前元素值
    public E set(int index, E element) {
        checkElementIndex(index);

        E oldVal= data[index];
        data[index] = element;
        return oldVal;
    }


    /**** 查 ****/
    public E get(int index) {
        checkElementIndex(index);
        return data[index];
    }


    /**** 工具方法 ****/
    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    private void grow(int newCap) {
        if (size > newCap) {
            return;
        }

        E[] temp = (E[]) new Object[newCap];
        System.arraycopy(data, 0, temp, 0, size);
        data = temp;
    }

    private boolean isElementIndex(int index) { return index >= 0 && index < size; }

    private boolean isPositionIndex(int index) { return index >= 0 && index <= size; }

    /* 检查 index 索引位置是否可以 存在 元素 */
    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /* 检查 index 索引位置是否可以 添加 元素 */
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }


    public static void main(String[] args) {
        MyArrayList<Integer> arr = new MyArrayList<>(3);

        // 添加 5 个元素
        for (int i = 1; i <= 5; i++) {
            arr.addLast(i);
        }

        arr.remove(3);
        arr.add(1, 9);
        Integer val = arr.removeLast();

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
}
