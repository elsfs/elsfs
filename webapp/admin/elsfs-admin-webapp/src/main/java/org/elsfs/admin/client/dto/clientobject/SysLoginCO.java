package org.elsfs.admin.client.dto.clientobject;

import lombok.Data;
import lombok.experimental.Accessors;
import org.elsfs.core.dto.ClientObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author zeng
 * @since 0.0.1
 */
@Data
@Accessors(chain = true)
public class SysLoginCO implements ClientObject {

    private String username;

    private String userId;

    private String token;

    private String realName;

    private String avatar;

    private List<Roles> roles;

    private String homePath = "/dashboard/workbench";

    @Data
    @Accessors(chain = true)
    public static class Roles implements Serializable {

        private String roleName;

        private String value;

    }

}
