package org.elsfs.core.util.lang.tree.parser;

import org.elsfs.core.util.CollectionUtils;
import org.elsfs.core.util.lang.tree.Tree;
import org.elsfs.core.util.lang.tree.TreeNode;

import java.util.Map;

/**
 * @author zeng
 * @since 0.0.1
 */
public class DefaultNodeParser<T> implements NodeParser<TreeNode<T>, T> {

    public DefaultNodeParser() {
    }

    public void parse(TreeNode<T> treeNode, Tree<T> tree) {
        tree.setId(treeNode.getId());
        tree.setParentId(treeNode.getParentId());
        tree.setWeight(treeNode.getWeight());
        tree.setName(treeNode.getName());
        Map<String, Object> extra = treeNode.getExtra();
        if (!CollectionUtils.isEmpty(extra)) {
            extra.forEach(tree::putExtra);
        }
    }

}
