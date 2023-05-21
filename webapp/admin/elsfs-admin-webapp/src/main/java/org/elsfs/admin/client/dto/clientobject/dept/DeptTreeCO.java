package org.elsfs.admin.client.dto.clientobject.dept;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 部门查询数结果
 *
 * @author zeng
 * @since 0.0.1
 */
@Getter
@Setter
public class DeptTreeCO extends DeptCO {

    private List<DeptTreeCO> children;

}
