package org.elsfs.admin.app.command.query;

import lombok.RequiredArgsConstructor;
import org.elsfs.admin.client.dto.clientobject.dept.DeptCO;
import org.elsfs.admin.infrastructure.dataobject.SysDeptDB;
import org.elsfs.admin.infrastructure.repository.SysDepRepository;
import org.elsfs.core.dto.SingleResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 根据id查询
 *
 * @author zeng
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class DeptByIdQryExe {

    private final SysDepRepository sysDepRepository;

    public SingleResponse<DeptCO> execute(String id) {
        Optional<SysDeptDB> optional = sysDepRepository.findById(id);
        if (optional.isEmpty()) {
            return SingleResponse.buildFailure("", "");
        }
        DeptCO deptCO = new DeptCO();
        BeanUtils.copyProperties(optional.get(), deptCO);
        return SingleResponse.of(deptCO);
    }

}
