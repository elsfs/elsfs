package org.elsfs.admin.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.elsfs.core.dto.DTO;

/**
 * 用户基本信息
 *
 * @author zeng
 * @since 0.0.1
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserInfo implements DTO {

    private String userId;

    private String phone;

    private String email;

    // 用户昵称
    private String nickname;

    private String password;

    private String username;

    private String avatar;

}
