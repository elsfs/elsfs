package org.elsfs.admin.domain.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 密码 凭证
 *
 * @author zeng
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EvidencePassword extends Evidence {

    private String password;

}
