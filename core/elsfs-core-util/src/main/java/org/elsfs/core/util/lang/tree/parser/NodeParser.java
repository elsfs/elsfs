package org.elsfs.core.util.lang.tree.parser;

import org.elsfs.core.util.lang.tree.Tree;

/**
 * @author zeng
 * @since 0.0.1
 */
@FunctionalInterface
public interface NodeParser<T, E> {

    /**
     * parse
     * @param treeNode treeNode
     * @param tree tree
     */
    void parse(T treeNode, Tree<E> tree);

}
