package pers.fw.data_structure;

/**
 * 双向队列：双向链表实现
 *
 * @param <E>
 */
public class Deque<E> {
    private Node<E> head, tail;
    private int size;

    private static class Node<E> {
        private E e;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> prev, Node<E> next) {
            this.e = e;
            this.prev = prev;
            this.next = next;
        }
    }

    public Deque() {
        size = 0;
    }

    public void offerFirst(E e) {
        if (isEmpty()) {
            head = new Node<>(e, null, null);
            tail = head;
        } else {
            Node node = new Node<>(e, null, head);
            head.prev = node;
            head = node;
        }
        ++size;
    }

    public void offerLast(E e) {
        if (isEmpty()) {
            head = new Node<>(e, null, null);
            tail = head;
        } else {
            Node node = new Node<>(e, tail, null);
            tail.next = node;
            tail = node;
        }
        ++size;
    }

    public E pollFirst() {
        if (isEmpty()) return null;
        if (size == 1) {
            E e = head.e;
            clear();
            return e;
        }
        E e = head.e;
        Node next = head.next;
//        head.next = null; // todo 与pollLast相同
        next.prev = null;
        head = next;
        --size;
        return e;
    }

    public E pollLast() {
        if (isEmpty()) return null;
        if (size == 1) {
            E e = tail.e;
            clear();
            return e;
        }
        E e = tail.e;
        Node prev = tail.prev;
//        tail.prev = null; // todo 逻辑上加不加都行，但考虑垃圾回收，旧tail节点仍存在对新tail的引用，类似Deque->新tail<-旧tail，理论上旧tail不存在引用应该能会回收掉，需要确认一下
        prev.next = null;
        tail = prev;
        --size;
        return e;

    }

    public E peekFirst() {
        if (isEmpty()) return null;
        return head.e;
    }

    public E peekLast() {
        if (isEmpty()) return null;
        return tail.e;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
