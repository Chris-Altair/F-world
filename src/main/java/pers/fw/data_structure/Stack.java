package pers.fw.data_structure;

/**
 * 栈：使用单链表实现
 * 允许栈内元素为null
 *
 * @param <E>
 */
public class Stack<E> {
    private Node<E> head;
    private int size;

    private static class Node<E> {
        private E e;
        private Node<E> next;

        public Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
    }

    public Stack() {
        size = 0;
    }

    /**
     * 获取栈顶元素
     *
     * @return
     */
    public E top() {
        return head == null ? null : head.e;
    }

    /**
     * 元素入栈
     *
     * @param e
     */
    public void push(E e) {
        head = new Node<>(e, head);
        ++size;
    }

    /**
     * 弹出栈顶元素，若栈为空则返回null
     *
     * @return
     */
    public E pop() {
        if (head == null) {
            return null;
        }
        E top = head.e;
        head = head.next;
        --size;
        return top;
    }

    /**
     * 清空栈
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * 获取栈的大小
     *
     * @return
     */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
