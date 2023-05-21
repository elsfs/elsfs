package org.elsfs.admin.infrastructure.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.elsfs.core.dto.DTO;

import java.time.Instant;

/**
 * @author zeng
 * @since 0.0.1
 */
@Data
public class BaseDO implements DTO {

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Instant updateTime;

    /**
     * 逻辑删除标记(0--正常 1--删除)
     */
    @TableLogic
    @TableField("is_deleted")
    private Boolean deleted;

}
