package org.elsfs.admin.app.command.dept;

import lombok.RequiredArgsConstructor;
import org.elsfs.admin.infrastructure.repository.SysDepRepository;
import org.elsfs.core.dto.Response;
import org.springframework.stereotype.Component;

/**
 * 根据deptId 删除部门
 *
 * @author zeng
 * @since 0.0.1
 */
@RequiredArgsConstructor
@Component
public class DeptDeleteCmdExe {

    private final SysDepRepository sysDepRepository;

    public Response execute(String deptId) {
        sysDepRepository.cascadeDelete(deptId);
        return Response.buildSuccess();
    }

}
