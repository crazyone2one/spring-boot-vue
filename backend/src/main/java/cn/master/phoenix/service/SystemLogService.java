package cn.master.phoenix.service;

import com.mybatisflex.core.service.IService;
import cn.master.phoenix.entity.SystemLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 系统操作日志表 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2025-06-13
 */
public interface SystemLogService extends IService<SystemLog> {
    void saveLog(SystemLog log);

    void saveLogs(List<SystemLog> logs);

    Optional<SystemLog> findByTraceId(UUID traceId);
}
