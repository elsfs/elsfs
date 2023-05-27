package org.elsfs.admin.app.command.dept;

import lombok.RequiredArgsConstructor;
import org.elsfs.admin.client.dto.clientobject.dept.DeptCO;
import org.elsfs.admin.infrastructure.dataobject.SysDeptDB;
import org.elsfs.admin.infrastructure.repository.SysDepRepository;
import org.elsfs.core.dto.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author zeng
 * @since 0.0.1
 */
@RequiredArgsConstructor
@Component
public class DeptUpdateByIdOrSaveCmdExe {

    private final SysDepRepository sysDepRepository;

    private Response execute(SysDeptDB deptDB) {
        sysDepRepository.save(deptDB);
        return Response.buildSuccess();
    }

    public Response execute(DeptCO deptCO) {
        SysDeptDB sysDeptDB = new SysDeptDB();
        BeanUtils.copyProperties(deptCO, sysDeptDB);
        return execute(sysDeptDB);
    }

}
