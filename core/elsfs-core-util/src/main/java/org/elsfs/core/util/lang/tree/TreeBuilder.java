package org.elsfs.core.util.lang.tree;

import org.elsfs.core.util.CollectionUtils;
import org.elsfs.core.util.lang.tree.parser.NodeParser;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author zeng
 * @since 0.0.1
 */
public class TreeBuilder<E> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Tree<E> root;

    private final Map<E, Tree<E>> idTreeMap;

    private boolean isBuild;

    public static <T> TreeBuilder<T> of(T rootId) {
        return of(rootId, (TreeNodeConfig) null);
    }

    public static <T> TreeBuilder<T> of(T rootId, TreeNodeConfig config) {
        return new TreeBuilder<>(rootId, config);
    }

    public TreeBuilder(E rootId, TreeNodeConfig config) {
        this.root = new Tree<>(config);
        this.root.setId(rootId);
        this.idTreeMap = new LinkedHashMap<>();
    }

    public TreeBuilder<E> setId(E id) {
        this.root.setId(id);
        return this;
    }

    public TreeBuilder<E> setParentId(E parentId) {
        this.root.setParentId(parentId);
        return this;
    }

    public TreeBuilder<E> setName(CharSequence name) {
        this.root.setName(name);
        return this;
    }

    public TreeBuilder<E> setWeight(Comparable<?> weight) {
        this.root.setWeight(weight);
        return this;
    }

    public TreeBuilder<E> putExtra(String key, Object value) {
        Objects.requireNonNull(key, "Key must be not empty !");
        this.root.put(key, value);
        return this;
    }

    public TreeBuilder<E> append(Map<E, Tree<E>> map) {
        this.checkBuilt();
        this.idTreeMap.putAll(map);
        return this;
    }

    public TreeBuilder<E> append(Iterable<Tree<E>> trees) {
        this.checkBuilt();
        for (Tree<E> tree : trees) {
            this.idTreeMap.put(tree.getId(), tree);
        }
        return this;
    }

    public <T> TreeBuilder<E> append(List<T> list, NodeParser<T, E> nodeParser) {
        return this.append(list, null, nodeParser);
    }

    public <T> TreeBuilder<E> append(List<T> list, E rootId, NodeParser<T, E> nodeParser) {
        this.checkBuilt();
        var config = this.root.getConfig();
        Map<E, Tree<E>> map = new LinkedHashMap<>(list.size(), 1.0F);

        for (T t : list) {
            Tree<E> node = new Tree<>(config);
            nodeParser.parse(t, node);
            if (null != rootId && !rootId.getClass().equals(node.getId().getClass())) {
                throw new IllegalArgumentException("rootId type is node.getId().getClass()!");
            }
            map.put(node.getId(), node);
        }

        return this.append(map);
    }

    public TreeBuilder<E> reset() {
        this.idTreeMap.clear();
        this.root.setChildren(null);
        this.isBuild = false;
        return this;
    }

    public Tree<E> build() {
        this.checkBuilt();
        this.buildFromMap();
        this.cutTree();
        this.isBuild = true;
        this.idTreeMap.clear();
        return this.root;
    }

    public List<Tree<E>> buildList() {
        return this.isBuild ? this.root.getChildren() : this.build().getChildren();
    }

    private void buildFromMap() {
        if (!CollectionUtils.isEmpty(this.idTreeMap)) {
            Map<E, Tree<E>> eTreeMap = new LinkedHashMap<>();
            Comparator<Map.Entry<E, Tree<E>>> entryComparator = Map.Entry.comparingByValue();
            this.idTreeMap.entrySet()
                .stream()
                .sorted(entryComparator)
                .forEachOrdered(e -> eTreeMap.put(e.getKey(), e.getValue()));

            for (Tree<E> node : eTreeMap.values()) {
                if (null != node) {
                    var parentId = node.getParentId();
                    if (Objects.equals(this.root.getId(), parentId)) {
                        this.root.addChildren(node);
                    }
                    else {
                        var parentNode = eTreeMap.get(parentId);
                        if (null != parentNode) {
                            parentNode.addChildren(node);
                        }
                    }
                }
            }
        }
    }

    private void cutTree() {
        var config = this.root.getConfig();
        var deep = config.getDeep();
        if (null != deep && deep >= 0) {
            this.cutTree(this.root, 0, deep);
        }
    }

    private void cutTree(Tree<E> tree, int currentDepp, int maxDeep) {
        if (null != tree) {
            if (currentDepp == maxDeep) {
                tree.setChildren(null);
            }
            else {
                var children = tree.getChildren();
                if (!CollectionUtils.isEmpty(children)) {
                    for (Tree<E> child : children) {
                        this.cutTree(child, currentDepp + 1, maxDeep);
                    }
                }

            }
        }
    }

    private void checkBuilt() {
        if (!this.isBuild) {
            return;
        }
        throw new IllegalArgumentException("Current tree has been built.");
    }

}
