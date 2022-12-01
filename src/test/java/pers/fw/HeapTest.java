package pers.fw;

import pers.fw.data_structure.heap.Heap;
import pers.fw.data_structure.heap.MaxHeap;
import pers.fw.data_structure.heap.MinHeap;

public class HeapTest {
    public static void main(String[] args) {
        Heap<Integer> heap = new MaxHeap(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        while (!heap.isEmpty()) {
            System.out.println(heap.pop());
        }
        heap = new MinHeap(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        while (!heap.isEmpty()) {
            System.out.println(heap.pop());
        }
    }
}
