package org.elsfs.core.util.lang.tree;

import org.elsfs.core.util.CollectionUtils;
import org.elsfs.core.util.lang.tree.parser.DefaultNodeParser;
import org.elsfs.core.util.lang.tree.parser.NodeParser;

import java.util.*;

/**
 * @author zeng
 * @since 0.0.1
 */
public class TreeUtil {

    public TreeUtil() {
    }

    public static Tree<Integer> buildSingle(List<TreeNode<Integer>> list) {
        return buildSingle(list, 0);
    }

    public static List<Tree<Integer>> build(List<TreeNode<Integer>> list) {
        return build(list, 0);
    }

    public static <E> Tree<E> buildSingle(List<TreeNode<E>> list, E parentId) {
        return buildSingle(list, parentId, TreeNodeConfig.DEFAULT_CONFIG, new DefaultNodeParser<>());
    }

    public static <E> List<Tree<E>> build(List<TreeNode<E>> list, E parentId) {
        return build(list, parentId, TreeNodeConfig.DEFAULT_CONFIG, new DefaultNodeParser<>());
    }

    public static <T, E> Tree<E> buildSingle(List<T> list, E parentId, NodeParser<T, E> nodeParser) {
        return buildSingle(list, parentId, TreeNodeConfig.DEFAULT_CONFIG, nodeParser);
    }

    public static <T, E> List<Tree<E>> build(List<T> list, E parentId, NodeParser<T, E> nodeParser) {
        return build(list, parentId, TreeNodeConfig.DEFAULT_CONFIG, nodeParser);
    }

    public static <T, E> List<Tree<E>> build(List<T> list, E rootId, TreeNodeConfig treeNodeConfig,
            NodeParser<T, E> nodeParser) {
        return buildSingle(list, rootId, treeNodeConfig, nodeParser).getChildren();
    }

    public static <T, E> Tree<E> buildSingle(List<T> list, E rootId, TreeNodeConfig treeNodeConfig,
            NodeParser<T, E> nodeParser) {
        return TreeBuilder.of(rootId, treeNodeConfig).append(list, rootId, nodeParser).build();
    }

    public static <E> List<Tree<E>> build(Map<E, Tree<E>> map, E rootId) {
        return buildSingle(map, rootId).getChildren();
    }

    public static <E> Tree<E> buildSingle(Map<E, Tree<E>> map, E rootId) {
        Tree<E> tree = null;
        if (!CollectionUtils.isEmpty(map)) {
            var values = map.values();
            if (!CollectionUtils.isEmpty(values)) {
                tree = values.iterator().next();
            }
        }
        if (null != tree) {
            var config = tree.getConfig();
            return TreeBuilder.of(rootId, config).append(map).build();
        }
        else {
            return createEmptyNode(rootId);
        }
    }

    public static <T> Tree<T> getNode(Tree<T> node, T id) {
        if (Objects.equals(id, node.getId())) {
            return node;
        }
        else {
            List<Tree<T>> children = node.getChildren();
            if (null == children) {
                return null;
            }
            else {
                var iterator = children.iterator();
                Tree<T> childNode;
                do {
                    if (!iterator.hasNext()) {
                        return null;
                    }

                    var child = iterator.next();
                    childNode = child.getNode(id);
                }
                while (null == childNode);
                return childNode;
            }
        }
    }

    public static <T> List<CharSequence> getParentsName(Tree<T> node, boolean includeCurrentNode) {
        List<CharSequence> result = new ArrayList<>();
        if (null == node) {
            return result;
        }
        else {
            if (includeCurrentNode) {
                result.add(node.getName());
            }

            for (Tree<T> parent = node.getParent(); null != parent; parent = parent.getParent()) {
                result.add(parent.getName());
            }

            return result;
        }
    }

    public static <E> Tree<E> createEmptyNode(E id) {
        return new Tree<E>().setId(id);
    }

}
