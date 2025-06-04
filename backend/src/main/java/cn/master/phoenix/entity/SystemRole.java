package cn.master.phoenix.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import com.mybatisflex.core.keygen.KeyGenerators;
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
@Table("system_role")
public class SystemRole implements Serializable {

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
    private String name;

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
