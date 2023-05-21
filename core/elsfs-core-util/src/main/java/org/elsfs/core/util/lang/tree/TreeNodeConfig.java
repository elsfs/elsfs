package org.elsfs.core.util.lang.tree;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zeng
 * @since 0.0.1
 */
public class TreeNodeConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static TreeNodeConfig DEFAULT_CONFIG = new TreeNodeConfig();

    private String idKey = "id";

    private String parentIdKey = "parentId";

    /**
     * 排序属性名 默认值的
     */
    private String weightKey = "weight";

    private String nameKey = "name";

    private String childrenKey = "children";

    /**
     * 最大递归深度
     */
    private Integer deep;

    public TreeNodeConfig() {
    }

    public String getIdKey() {
        return this.idKey;
    }

    public TreeNodeConfig setIdKey(String idKey) {
        this.idKey = idKey;
        return this;
    }

    public String getWeightKey() {
        return this.weightKey;
    }

    public TreeNodeConfig setWeightKey(String weightKey) {
        this.weightKey = weightKey;
        return this;
    }

    public String getNameKey() {
        return this.nameKey;
    }

    public TreeNodeConfig setNameKey(String nameKey) {
        this.nameKey = nameKey;
        return this;
    }

    public String getChildrenKey() {
        return this.childrenKey;
    }

    public TreeNodeConfig setChildrenKey(String childrenKey) {
        this.childrenKey = childrenKey;
        return this;
    }

    public String getParentIdKey() {
        return this.parentIdKey;
    }

    public TreeNodeConfig setParentIdKey(String parentIdKey) {
        this.parentIdKey = parentIdKey;
        return this;
    }

    public Integer getDeep() {
        return this.deep;
    }

    public TreeNodeConfig setDeep(Integer deep) {
        this.deep = deep;
        return this;
    }

}
