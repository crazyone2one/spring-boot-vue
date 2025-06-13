package cn.master.phoenix.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.phoenix.entity.SystemLog;
import cn.master.phoenix.mapper.SystemLogMapper;
import cn.master.phoenix.service.SystemLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 系统操作日志表 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2025-06-13
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog>  implements SystemLogService{

    @Override
    public void saveLog(SystemLog log) {

    }

    @Override
    public void saveLogs(List<SystemLog> logs) {

    }

    @Override
    public Optional<SystemLog> findByTraceId(UUID traceId) {
        return Optional.empty();
    }
}
