package cn.master.phoenix.config;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by 11's papa on 2025/4/25
 */
@Slf4j
@Configuration
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {
    @Override
    public void customize(FlexGlobalConfig flexGlobalConfig) {
        // 设置全局主键生成策略
        FlexGlobalConfig.KeyConfig keyConfig = new FlexGlobalConfig.KeyConfig();
        keyConfig.setKeyType(KeyType.Generator);
        keyConfig.setValue(KeyGenerators.flexId);
        flexGlobalConfig.setKeyConfig(keyConfig);
        // 全局配置逻辑删除字段
        FlexGlobalConfig.getDefaultConfig().setLogicDeleteColumn("deleted");

        //开启审计功能
        AuditManager.setAuditEnable(true);
        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage -> log.info("{},{}ms", auditMessage.getFullSql(), auditMessage.getElapsedTime())
        );
    }
}
