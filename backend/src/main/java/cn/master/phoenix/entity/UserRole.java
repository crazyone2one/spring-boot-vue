package cn.master.phoenix.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_role")
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * role name
     */
    @NotBlank(message = "{user_role.name.not_blank}")
    @Size(min = 1, max = 255, message = "{user_role.name.length_range}")
    private String name;
    @Schema(description =  "描述")
    private String description;
    @Schema(description =  "是否是内置用户组", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "{user_role.internal.not_blank}")
    private Boolean internal;
    @NotBlank(message = "{user_role.scope_id.not_blank}")
    @Size(min = 1, max = 50, message = "{user_role.scope_id.length_range}")
    private String scopeId;
    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @Column(onUpdateValue = "now()", onInsertValue = "now()")
    private LocalDateTime updateDate;

    /**
     * 是否有效，1-有效，0无效
     */
    private Boolean enabled;

    /**
     * 是否删除。0-未删除，1-已删除
     */
    private Boolean deleted;

}
