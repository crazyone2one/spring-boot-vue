package cn.master.phoenix.handler;

import cn.master.phoenix.handler.annotation.NoResultHolder;
import cn.master.phoenix.util.JacksonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author Created by 11's papa on 2025/5/21
 */
@RestControllerAdvice
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        // 排除已经是ApiResponse类型的返回值和异常处理方法
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType) || StringHttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (Objects.isNull(body) && StringHttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return JacksonUtils.toJSONString(ResultHolder.success(body));
        }
        if (returnType.hasMethodAnnotation(NoResultHolder.class)) {
            return body;
        }
        if (!(body instanceof ResultHolder)) {
            if (body instanceof String) {
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return JacksonUtils.toJSONString(ResultHolder.success(body));
            }
            return ResultHolder.success(body);
        }
        return body;
    }
}