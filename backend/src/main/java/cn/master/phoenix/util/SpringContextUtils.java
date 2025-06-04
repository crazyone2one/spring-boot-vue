package cn.master.phoenix.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Created by 11's papa on 2025/5/29
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext acx) throws BeansException {
        applicationContext = acx;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
