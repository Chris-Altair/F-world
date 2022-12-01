package pers.fw.data_structure;

/**
 * 单向队列：使用双栈实现(正常应该考虑双向链表实现)
 * 入队O(1)、出队平均O(n)
 * 与双向链表的实现相比节省了前置引用
 * 所以更适合并且先一直入队，然后统一出队的情况（一边入一边出可能会存在频繁的元素移动，统一出则只需第一次全量移动，后续都是O(1) ）
 *
 * @param <E>
 */
public class Queue<E> {
    /**
     * 入栈、出栈
     */
    private Stack<E> inStack, outStack;
    private int size;

    public Queue() {
        inStack = new Stack<>();
        outStack = new Stack<>();
        size = 0;
    }

    public void offer(E e) {
        inStack.push(e);
        ++size;
    }

    public E poll() {
        if (!outStack.isEmpty()) {
            --size;
            return outStack.pop();
        }
        if (inStack.isEmpty()) {
            //入栈、出栈均为空
            return null;
        }
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
        --size;
        return outStack.pop();

    }

    public E peek() {
        if (!outStack.isEmpty()) {
            return outStack.top();
        }
        if (inStack.isEmpty()) {
            //入栈、出栈均为空
            return null;
        }
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
        return outStack.top();
    }

    public void clear() {
        inStack.clear();
        outStack.clear();
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
