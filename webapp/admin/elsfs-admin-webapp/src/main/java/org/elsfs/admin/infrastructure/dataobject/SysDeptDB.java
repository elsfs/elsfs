package org.elsfs.admin.infrastructure.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门管理
 *
 * @author zeng
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDeptDB extends BaseDO {

    @TableId(type = IdType.ASSIGN_ID)

    private String deptId;

    /* 上级部门id */
    private String parentId;

    /* 部门名称 */
    private String deptName;

    /* 备注 */
    private String remark;

    private Integer orderNo;

    /* 0 启用 1 停用 */
    @TableField("is_status")
    private Boolean status;

}
