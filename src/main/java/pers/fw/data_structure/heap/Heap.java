package pers.fw.data_structure.heap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * 二叉堆抽象类
 * 内部使用数组实现
 * 可根据需要继承该类实现最大堆或最小堆
 * 通过Comparable接口的compareTo方法判断元素大小，所以堆内元素必须实现Comparable接口
 *
 * @param <E>
 */
public abstract class Heap<E extends Comparable<E>> {
    /**
     * 默认容量：只有使用无参构造器并且第一次调用add方法才会使用
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 128;

    /**
     * 没用泛型数组的原因在于正常来说java没法初始化泛型数组（得使用特殊方法{@link java.lang.reflect.Array#newInstance(java.lang.Class, int)}）
     * elements[0]永远不会用到，元素从elements[1]开始
     */
    protected Object[] elements;
    protected int size = 0;

    public Heap() {
    }

    public Heap(int initialCapacity) {
        if (initialCapacity < 0) throw new RuntimeException("initialCapacity must be ge zero");
        elements = new Object[initialCapacity + 1];
    }

    private void checkDataArray(E[] dataArray) {
        if (dataArray == null) throw new NullPointerException("dataArray must not be null");
        for (int i = 0; i < dataArray.length; i++) {
            if (dataArray[i] == null) throw new NullPointerException("element must not be null");
        }
    }

    public Heap(E[] dataArray) {
        checkDataArray(dataArray);
        size = dataArray.length;
        elements = new Object[size + 1];
        System.arraycopy(dataArray, 0, elements, 1, size);
        heapify();
    }

    public Heap(Collection<E> collection) {
        size = collection.size();
        elements = new Object[size + 1];
        Iterator<E> iterator = collection.iterator();
        for (int i = 1; iterator.hasNext(); i++) {
            E e = iterator.next();
            if (e == null) throw new NullPointerException("element must not be null");
            elements[i] = e;
        }
        heapify();
    }

    /**
     * 节点k上浮
     *
     * @param k
     */
    protected abstract void fixUp(int k);

    /**
     * 节点k下沉
     *
     * @param k
     */
    protected abstract void fixDown(int k);

    /**
     * 调整堆
     */
    protected void heapify() {
        for (int i = size / 2; i >= 1; i--)
            fixDown(i);
    }

    /**
     * 添加元素，若使用无参构造器则第一次调用该方法才会初始化对象数组，默认容量{@link DEFAULT_INITIAL_CAPACITY}；
     * 若数组已满，则扩充二倍长度
     *
     * @param e
     */
    public void add(E e) {
        if (size == 0 && elements == null) {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }
        if (size + 1 == elements.length) {
            elements = Arrays.copyOf(elements, 2 * elements.length);
        }
        elements[++size] = e;
        fixUp(size);
    }

    /**
     * 获取并删除堆顶元素
     *
     * @return
     */
    public E pop() {
        assert size > 0;
        E e = (E) elements[1];
        removeTop();
        return e;
    }

    /**
     * 移除堆顶元素
     */
    public void removeTop() {
        assert size > 0;
        elements[1] = elements[size];
        elements[size--] = null;
        fixDown(1);
    }

    /**
     * 获取堆顶但不删除
     *
     * @return
     */
    public E top() {
        assert size > 0;
        return (E) elements[1];
    }

    /**
     * 清空堆
     */
    public void clear() {
        for (int i = 1; i <= size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    protected void swap(int i, int j) {
        Object tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
