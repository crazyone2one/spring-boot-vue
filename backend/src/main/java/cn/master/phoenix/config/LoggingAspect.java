package cn.master.phoenix.config;

import cn.master.phoenix.entity.SystemLog;
import cn.master.phoenix.handler.annotation.Loggable;
import cn.master.phoenix.service.SystemLogService;
import cn.master.phoenix.util.JacksonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 日志切面类
 *
 * @author Created by 11's papa on 2025/6/13
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {
    private final SystemLogService systemLogService;

    public LoggingAspect(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    // 定义切点：拦截controller包下的所有方法
    @Pointcut("execution(* cn.master.phoenix.controller.*.*(..))")
    public void controllerMethods() {
    }

    // 定义切点：拦截带有@Loggable注解的方法
    @Pointcut("@annotation(cn.master.phoenix.handler.annotation.Loggable)")
    public void loggableMethods() {
    }

    // 前置通知：在方法执行前执行
    @Before("loggableMethods()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Loggable loggable = method.getAnnotation(Loggable.class);
        assert loggable != null;
        String description = loggable.value();

        log.info("开始执行方法: {} - {}", method.getName(), description);
        log.info("参数: {}", joinPoint.getArgs());
    }

    // 返回通知：在方法正常返回后执行
    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        log.info("方法执行完成: {} - 返回值: {}", method.getName(), result);
    }

    @Around(("loggableMethods()"))
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String traceId = UUID.randomUUID().toString();
        try {
            // 执行目标方法
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            // 异步保存正常日志
            saveLogToDB(joinPoint, traceId, result, null, executionTime);
            return result;
        } catch (Throwable e) {
            // 异步保存异常日志
            long executionTime = System.currentTimeMillis() - startTime;
            saveLogToDB(joinPoint, traceId, null, e.getMessage(), executionTime);
            throw e;
        }
    }

    @Async
    public void saveLogToDB(ProceedingJoinPoint joinPoint, String traceId, Object result, String exception, long executionTime) {
        try {
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            Method method = signature.getMethod();

            // 获取请求信息
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
            SystemLog systemLog = SystemLog.builder()
                    .traceId(traceId)
                    .classMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName())
                    .methodParams(JacksonUtils.toJSONString(joinPoint.getArgs()))
                    .returnValue(Objects.nonNull(result) ? JacksonUtils.toJSONString(result) : "")
                    .requestUrl(Objects.nonNull(request) ? request.getRequestURI() : "")
                    .httpMethod(Objects.nonNull(request) ? request.getMethod() : "")
                    .ip(Objects.nonNull(request) ? request.getRemoteAddr() : "")
                    .operator("unknown_user")
                    .executionTime(executionTime)
                    .exceptionMessage(exception)
                    .build();
            systemLogService.save(systemLog);
            CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("保存日志到数据库失败", e);
            CompletableFuture.completedFuture(null);
        }
    }
}
