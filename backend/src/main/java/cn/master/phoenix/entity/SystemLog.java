package cn.master.phoenix.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统操作日志表 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2025-06-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统操作日志表")
@Table("system_log")
public class SystemLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Schema(description = "")
    private String id;

    /**
     * 请求追踪ID
     */
    @Schema(description = "请求追踪ID")
    private String traceId = UUID.randomUUID().toString();

    /**
     * 类和方法名
     */
    @Schema(description = "类和方法名")
    private String classMethod;

    /**
     * 方法参数
     */
    @Schema(description = "方法参数")
    private String methodParams;

    /**
     * 返回值
     */
    @Schema(description = "返回值")
    private String returnValue;

    /**
     * 请求URL
     */
    @Schema(description = "请求URL")
    private String requestUrl;

    /**
     * HTTP方法
     */
    @Schema(description = "HTTP方法")
    private String httpMethod;

    /**
     * 客户端IP
     */
    @Schema(description = "客户端IP")
    private String ip;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private String operator;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    @Column(onUpdateValue = "now()", onInsertValue = "now()")
    private LocalDateTime operationTime;

    /**
     * 执行时间(毫秒)
     */
    @Schema(description = "执行时间(毫秒)")
    private Long executionTime;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String exceptionMessage;

}
