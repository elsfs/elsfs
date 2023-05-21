package org.elsfs.admin.infrastructure.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zeng
 * @since 0.0.1
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysUserDB extends BaseDO {

    @TableId(type = IdType.ASSIGN_ID)
    private String userId;

    private String phone;

    private String email;

    // 用户昵称
    private String nickname;

    private String password;

    private String username;

    private String avatar;

}
