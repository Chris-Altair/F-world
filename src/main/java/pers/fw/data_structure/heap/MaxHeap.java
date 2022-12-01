package pers.fw.data_structure.heap;

import java.util.Collection;

/**
 * 最大堆
 *
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> extends Heap<E> {

    public MaxHeap() {
    }

    public MaxHeap(int initialCapacity) {
        super(initialCapacity);
    }

    public MaxHeap(E[] dataArray) {
        super(dataArray);
    }

    public MaxHeap(Collection<E> collection) {
        super(collection);
    }

    @Override
    protected void fixUp(int k) {
        while (k > 1) {
            int i = k >> 1;
            if (((E) elements[k]).compareTo((E) elements[i]) > 0) swap(k, i);
            k = i;
        }
    }

    @Override
    protected void fixDown(int k) {
        int i;
        while ((i = k << 1) <= size && i > 0) {
            i = i == size ? i : (((E) elements[i]).compareTo((E) elements[i + 1]) >= 0 ? i : i + 1);
            if (((E) elements[i]).compareTo((E) elements[k]) > 0) swap(i, k);
            k = i;
        }
    }
}
