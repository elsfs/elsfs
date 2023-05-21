package org.elsfs.admin.infrastructure.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zeng
 * @since 0.0.1
 */
@Getter
@Setter
@TableName("sys_permission")
public class SysPermissionDB extends BaseDO {

    /*-----------菜单和meta公共部分-------------*/
    /* 权限id */
    @TableId(type = IdType.ASSIGN_ID)
    private String permissionId;

    /* 父id */
    private String parentId;

    /* 是否隐藏路由: 0否,1是 */
    // 从不显示在菜单中 hideMenu is_hidden

    @TableField("is_hidden")
    private Boolean hidden = false;

    /* 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限) */
    private Integer menuType;

    private Integer orderNo;

    private String icon;

    /*----------菜单部分--------------*/
    private String component;

    private String redirect;

    private String name;

    private String path;

    // private List<PermissionMenu> children;
    // private PermissionMenu.RouteMeta meta;

    /*-------------mate部分--------------*/
    // title
    private String title;

    // 是否缓存
    // private boolean ignoreKeepAlive = false;
    /**
     * 是否已动态添加路由
     */
    // private boolean hideBreadcrumb = true;
    // 隐藏儿童菜单
    // private boolean hideChildrenInMenu = false;
    // 当前活动菜单
    private String currentActiveMenu;

}
