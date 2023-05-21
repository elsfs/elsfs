package org.elsfs.core.util.lang.tree;

import java.io.Serializable;

/**
 * @author zeng
 * @since 0.0.1
 */
public interface Node<T> extends Comparable<Node<T>>, Serializable {

    T getId();

    Node<T> setId(T var1);

    T getParentId();

    Node<T> setParentId(T var1);

    CharSequence getName();

    Node<T> setName(CharSequence var1);

    Comparable<?> getWeight();

    Node<T> setWeight(Comparable<?> var1);

    @SuppressWarnings("all")
    default int compareTo(Node node) {
        if (null == node) {
            return 1;
        }
        Comparable weight = this.getWeight();
        Comparable weightOther = node.getWeight();
        if (weight == weight) {
            return 0;
        }
        else if (weight == null) {
            return -1;
        }
        else if (weightOther == null) {
            return 1;
        }
        return weight.compareTo(weightOther);
    }

}
