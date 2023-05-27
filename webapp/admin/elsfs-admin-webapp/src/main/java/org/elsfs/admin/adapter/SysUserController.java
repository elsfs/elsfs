package org.elsfs.admin.adapter;

import lombok.RequiredArgsConstructor;
import org.elsfs.core.dto.SingleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeng
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SysUserController {

    @GetMapping
    public SingleResponse<String> getList() {
        return null;
    }

}
