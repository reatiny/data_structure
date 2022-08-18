import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 底层用双链表实现LinkedList
 * 操作前首先获得操作位置前后 两个节点
 */
public class MyLinkedList<E> implements Iterable<E> {

    // 虚拟头尾节点
    final private Node<E> head, tail;
    private int size;


    // 双链表节点
    private static class Node<E> {
        E val;
        Node<E> next;
        Node<E> pre;

        Node(E val) {
            this.val = val;
        }
    }


    // 构造函数初始化头尾节点
    MyLinkedList() {
        this.head = new Node<>(null);
        this.tail = new Node<>(null);
        head.next = tail;
        tail.pre = head;
        this.size = 0;
    }


    /**** 增 ****/
    public void addLast(E e) {
        Node<E> x = new Node<>(e);
        Node<E> temp = tail.pre;

        temp.next = x;
        x.pre = temp;
        x.next = tail;
        tail.pre = x;

        size++;
    }

    public void addFirst(E e) {
        Node<E> x = new Node<>(e);
        Node<E> temp = head.next;

        temp.pre = x;
        x.next = temp;
        head.next = x;
        x.pre = head;

        size++;
    }

    public void add(int index, E e) {
        checkPositionIndex(index);
        if (index == size) {
            addLast(e);
            return;
        }

        // temp --- x --- p
        Node<E> p = getNode(index);
        Node<E> temp = p.pre;

        Node<E> x = new Node<>(e);
        p.pre = x;
        x.next = p;
        temp.next = x;
        x.pre = temp;

        size++;


    }

    /**** 删 ****/
    public E removeFirst() {
        if (size < 1) {
            throw new NoSuchElementException();
        }

        // 虚拟节点的存在不用考虑空指针问题
        Node<E> x = head.next;
        Node<E> temp = x.next;
        // head --- x --- temp
        head.next = temp;
        temp.pre = head;

        x.pre = null;
        x.next = null;

        size--;
        return x.val;
    }

    public E removeLast() {
        if (size < 1) {
            throw new NoSuchElementException();
        }
        Node<E> x = tail.pre;
        Node<E> temp = tail.pre.pre;
        // temp <-> x <-> tail

        tail.pre = temp;
        temp.next = tail;

        x.pre = null;
        x.next = null;
        // temp <-> tail

        size--;
        return x.val;
    }

    public E remove(int index) {
        checkElementIndex(index);
        // 找到 index 对应的 Node
        Node<E> x = getNode(index);
        Node<E> prev = x.pre;
        Node<E> next = x.next;
        // prev <-> x <-> next
        prev.next = next;
        next.pre = prev;

        x.pre = x.next = null;
        // prev <-> next

        size--;

        return x.val;
    }


    /**** 查 ****/
    public E get(int index) {
        checkElementIndex(index);
        // 找到 index 对应的 Node
        Node<E> p = getNode(index);

        return p.val;
    }

    public E getFirst() {
        if (size < 1) {
            throw new NoSuchElementException();
        }

        return head.next.val;
    }

    public E getLast() {
        if (size < 1) {
            throw new NoSuchElementException();
        }

        return tail.pre.val;
    }


    /**** 改 ****/
    public E set(int index, E val) {
        checkElementIndex(index);
        // 找到 index 对应的 Node
        Node<E> p = getNode(index);

        E oldVal = p.val;
        p.val = val;

        return oldVal;
    }



    /**** 工具函数 ****/

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private Node<E> getNode(int index) {
        checkElementIndex(index);
        Node<E> p = head.next;

        for (int i = 0; i < index; i++) {
            p = p.next;
        }

        return p;
    }


    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

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


    private void display() {
        System.out.println("size = " + size);
        for (Node<E> p = head.next; p != tail; p = p.next) {
            System.out.print(p.val + " -> ");
        }
        System.out.println("null");
        System.out.println();
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public E next() {
                E val = p.val;
                p = p.next;
                return val;
            }
        };
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(5);
        list.add(1, 2);
        list.addLast(0);
        list.display();
    }
}
