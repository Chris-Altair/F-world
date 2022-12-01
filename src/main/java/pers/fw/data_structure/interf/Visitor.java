package pers.fw.data_structure.interf;

/**
 * 遍历时元素的访问器
 *
 * @param <T>
 */
@FunctionalInterface
public interface Visitor<T> {
    /**
     *
     * @param t
     * @return
     */
    boolean visit(T t);
}
