package org.elsfs.admin.adapter;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.elsfs.admin.infrastructure.dataobject.SysPermissionDB;
import org.elsfs.admin.infrastructure.repository.mybatis.SysPermissionRepository;
import org.elsfs.core.dto.MultiResponse;
import org.elsfs.core.dto.SingleResponse;
import org.elsfs.core.util.lang.tree.Tree;
import org.elsfs.core.util.lang.tree.TreeNode;
import org.elsfs.core.util.lang.tree.TreeNodeConfig;
import org.elsfs.core.util.lang.tree.TreeUtil;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zeng
 * @since 0.0.1
 */
@RequiredArgsConstructor
@RestController
public class SysUserInfoController {

    @GetMapping("/user/getUserInfo")
    public SingleResponse getUserInfo() {
        List<HashMap<String, String>> asList = Arrays.asList(new HashMap<String, String>() {
            {
                put("roleName", "Super Admin");
                put("super", "super");
            }
        });

        return SingleResponse.of(new HashMap<String, List>() {
            {
                put("roles", asList);
            }
        });
    }

    @GetMapping("/menu/getPermCode")
    public SingleResponse getPermCode() {
        return SingleResponse.of(Arrays.asList(1000, 3000, 5000));
    }

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    @GetMapping("/getMenuList")
    public SingleResponse ss(HttpServletResponse httpServletResponse) {
        List<SysPermissionDB> list = sysPermissionRepository.selectList(Wrappers.emptyWrapper());
        List<TreeNode<String>> nodeList = new ArrayList<>();

        // 配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("orderNo");
        treeNodeConfig.setIdKey("permissionId");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        // 转换器
        List<Tree<String>> treeNodes = TreeUtil.build(list, "0", treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getPermissionId());
            tree.setParentId(treeNode.getParentId());
            tree.putExtra("path", treeNode.getPath());
            tree.putExtra("name", treeNode.getName());
            tree.putExtra("name", treeNode.getName());
            tree.putExtra("component", treeNode.getComponent());
            tree.putExtra("redirect", treeNode.getRedirect());
            tree.putExtra("path", treeNode.getPath());
            HashMap<String, Object> map = new HashMap<>();
            if (treeNode.getCurrentActiveMenu() != null) {
                map.put("currentActiveMenu", treeNode.getTitle());
            }
            map.put("hideMenu", treeNode.getHidden());
            map.put("title", treeNode.getTitle());
            map.put("icon", treeNode.getIcon());
            map.put("currentActiveMenu", treeNode.getCurrentActiveMenu());
            tree.putExtra("meta", map);
        });

        return SingleResponse.of(treeNodes);
    }

    private static class MapTypeReference extends TypeReference<LinkedMultiValueMap<String, JSONWrappedObject>> {

        private MapTypeReference() {
        }

    }

    /**
     * 获取权限代码
     * @return
     */
    @GetMapping("/getMenuList1")
    public MultiResponse<Tree<String>> getMenuList() {
        PermissionVO icon = new PermissionVO().setComponent("LAYOUT")
            .setRedirect("/dashboard/analysis")
            .setTitle("routes.dashboard.dashboard")
            .setHideChildrenInMenu(true)
            .setType("0")
            .setPath("/dashboard")
            .setPermissionName("Dashboard")
            .setIcon("bx:bx-home")
            .setPermissionId("1")
            .setParentId("0");
        PermissionVO icon1 = new PermissionVO().setComponent("/dashboard/analysis/index")
            .setTitle("routes.dashboard.dashboard")
            .setHideMenu(true)
            .setHideBreadcrumb(true)
            .setCurrentActiveMenu("/dashboard")
            .setType("0")
            .setPath("analysis")
            .setPermissionName("Analysis")
            .setIcon("bx:bx-home")
            .setPermissionId("2")
            .setParentId("1");
        PermissionVO icon2 = new PermissionVO().setComponent("/dashboard/workbench/index")
            .setTitle("routes.dashboard.workbench")
            .setHideMenu(true)
            .setHideBreadcrumb(true)
            .setCurrentActiveMenu("/dashboard")
            .setType("0")
            .setPath("workbench")
            .setPermissionName("Workbench")
            .setIcon("bx:bx-home")
            .setPermissionId("3")
            .setParentId("1");
        List<PermissionVO> asList = new ArrayList<>();
        asList.add(icon);
        asList.add(icon1);
        asList.add(icon2);
        List<TreeNode<String>> collect = asList.stream()
            // .filter(menu -> StringUtils.isNotBlank(menu.getPath()))
            .map(menu -> {
                TreeNode<String> node = new TreeNode<>();
                node.setId(menu.getPermissionId());
                node.setName(menu.getPermissionName());
                node.setParentId(menu.getParentId());
                node.setWeight(menu.getSortOrder());
                // 扩展属性
                Map<String, Object> extra = new HashMap<>();
                extra.put("icon", menu.getIcon());
                extra.put("path", menu.getPath());
                extra.put("component", menu.getComponent());
                extra.put("hideMenu", menu.getHideMenu());
                extra.put("hideBreadcrumb", menu.getHideBreadcrumb());
                extra.put("title", menu.getTitle());
                extra.put("currentActiveMenu", menu.getCurrentActiveMenu());
                extra.put("hideChildrenInMenu", menu.getHideChildrenInMenu());
                extra.put("type", menu.getType());
                extra.put("permission", menu.getPermission());
                extra.put("label", menu.getPermissionName());
                extra.put("sortOrder", menu.getSortOrder());
                extra.put("keepAlive", menu.getKeepAlive());
                node.setExtra(extra);
                return node;
            })
            .collect(Collectors.toList());
        List<Tree<String>> list = TreeUtil.build(collect, "0");
        return MultiResponse.of(list);
    }

    @Data
    @Accessors(chain = true)
    public static class PermissionVO {

        private String component;

        private String redirect;

        private String title;

        private Boolean hideChildrenInMenu;

        private Boolean hideMenu;

        private Boolean hideBreadcrumb;

        private String currentActiveMenu;

        private String permissionId;

        /**
         * 权限名称
         */
        private String permissionName;

        /**
         * 菜单权限标识
         */
        private String permission;

        /**
         * 父权限ID 顶级为0
         */
        private String parentId;

        /**
         * 权限图标
         */
        private String icon;

        /**
         * 前端URL 前端路由标识路径
         */
        private String path;

        /**
         * 排序值
         */
        private Integer sortOrder;

        /**
         * 菜单类型 （0 菜单 1 按钮）
         */
        private String type;

        /**
         * 路由缓冲
         */
        private String keepAlive;

    }

}
