package org.elsfs.core.util.lang.tree;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.elsfs.core.util.CollectionUtils;
import org.elsfs.util.function.Filter;

import java.io.PrintWriter;
import java.io.Serial;
import java.io.StringWriter;
import java.util.*;

import java.util.function.Consumer;

/**
 * @author zeng
 * @since 0.0.1
 */
@SuppressWarnings("all")
public class Tree<T> extends LinkedHashMap<String, Object> implements Node<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    private final TreeNodeConfig treeNodeConfig;

    private Tree<T> parent;

    public Tree() {
        this((TreeNodeConfig) null);
    }

    public Tree(TreeNodeConfig treeNodeConfig) {

        this.treeNodeConfig = (TreeNodeConfig) ObjectUtils.defaultIfNull(treeNodeConfig, TreeNodeConfig.DEFAULT_CONFIG);
    }

    public TreeNodeConfig getConfig() {
        return this.treeNodeConfig;
    }

    public Tree<T> getParent() {
        return this.parent;
    }

    public Tree<T> getNode(T id) {
        return TreeUtil.getNode(this, id);
    }

    public List<CharSequence> getParentsName(T id, boolean includeCurrentNode) {
        return TreeUtil.getParentsName(this.getNode(id), includeCurrentNode);
    }

    public List<CharSequence> getParentsName(boolean includeCurrentNode) {
        return TreeUtil.getParentsName(this, includeCurrentNode);
    }

    public Tree<T> setParent(Tree<T> parent) {
        this.parent = parent;
        if (null != parent) {
            this.setParentId(parent.getId());
        }

        return this;
    }

    public T getId() {
        return (T) this.get(this.treeNodeConfig.getIdKey());
    }

    public Tree<T> setId(T id) {
        this.put(this.treeNodeConfig.getIdKey(), id);
        return this;
    }

    public T getParentId() {
        return (T) this.get(this.treeNodeConfig.getParentIdKey());
    }

    public Tree<T> setParentId(T parentId) {
        this.put(this.treeNodeConfig.getParentIdKey(), parentId);
        return this;
    }

    public CharSequence getName() {
        return (CharSequence) this.get(this.treeNodeConfig.getNameKey());
    }

    public Tree<T> setName(CharSequence name) {
        this.put(this.treeNodeConfig.getNameKey(), name);
        return this;
    }

    public Comparable<?> getWeight() {
        return (Comparable) this.get(this.treeNodeConfig.getWeightKey());
    }

    public Tree<T> setWeight(Comparable<?> weight) {
        this.put(this.treeNodeConfig.getWeightKey(), weight);
        return this;
    }

    public List<Tree<T>> getChildren() {
        return (List) this.get(this.treeNodeConfig.getChildrenKey());
    }

    public boolean hasChild() {
        return !CollectionUtils.isEmpty(this.getChildren());
    }

    public void walk(Consumer<Tree<T>> consumer) {
        consumer.accept(this);
        List<Tree<T>> children = this.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            children.forEach((tree) -> {
                tree.walk(consumer);
            });
        }

    }

    public Tree<T> filterNew(Filter<Tree<T>> filter) {
        return this.cloneTree().filter(filter);
    }

    public Tree<T> filter(Filter<Tree<T>> filter) {
        if (filter.accept(this)) {
            return this;
        }
        else {
            List<Tree<T>> children = this.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                List<Tree<T>> filteredChildren = new ArrayList(children.size());
                var iterator = children.iterator();

                while (iterator.hasNext()) {
                    var child = iterator.next();
                    var filteredChild = child.filter(filter);
                    if (null != filteredChild) {
                        filteredChildren.add(filteredChild);
                    }
                }

                if (!CollectionUtils.isEmpty(filteredChildren)) {
                    return this.setChildren(filteredChildren);
                }

                this.setChildren(null);
            }

            return null;
        }
    }

    public Tree<T> setChildren(List<Tree<T>> children) {
        if (null == children) {
            this.remove(this.treeNodeConfig.getChildrenKey());
        }

        this.put(this.treeNodeConfig.getChildrenKey(), children);
        return this;
    }

    @SafeVarargs
    public final Tree<T> addChildren(Tree<T>... children) {
        if (ArrayUtils.isNotEmpty(children)) {
            var childrenList = this.getChildren();
            if (null == childrenList) {
                childrenList = new ArrayList<>();
                this.setChildren(childrenList);
            }
            for (Tree<T> child : children) {
                child.setParent(this);
                childrenList.add(child);
            }
        }

        return this;
    }

    public void putExtra(String key, Object value) {
        Objects.requireNonNull(key, "Key must be not empty !");
        this.put(key, value);
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        printTree(this, new PrintWriter(stringWriter), 0);
        return stringWriter.toString();
    }

    public Tree<T> cloneTree() {
        var result = ObjectUtils.clone(this);
        result.setChildren(this.cloneChildren());
        return result;
    }

    private List<Tree<T>> cloneChildren() {
        var children = this.getChildren();
        if (null == children) {
            return null;
        }
        else {
            List<Tree<T>> newChildren = new ArrayList<>(children.size());
            children.forEach((t) -> {
                newChildren.add(t.cloneTree());
            });
            return newChildren;
        }
    }

    private static void printTree(Tree<?> tree, PrintWriter writer, int intent) {
        char blankSpace = ' ';
        writer.println(String.format("%s name=%s,id=%s", String.valueOf(blankSpace).repeat(Math.max(0, intent)),
                tree.getName(), tree.getId()));
        writer.flush();
        List<? extends Tree<?>> children = tree.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            var iterator = children.iterator();
            while (iterator.hasNext()) {
                var child = iterator.next();
                printTree(child, writer, intent + 2);
            }
        }

    }

}
