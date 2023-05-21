package org.elsfs.admin.client.dto.clientobject.dept;

import lombok.Getter;
import lombok.Setter;
import org.elsfs.core.dto.Command;

/**
 * @author zeng
 * @since 0.0.1
 */
@Getter
@Setter
public class DeptCO implements Command {

    private String deptId;

    /* 上级部门id */
    private String parentId;

    /* 部门名称 */
    private String deptName;

    /* 备注 */
    private String remark;

    /* 0 启用 1 停用 */
    private Boolean status;

    private Integer orderNo = 0;

}
