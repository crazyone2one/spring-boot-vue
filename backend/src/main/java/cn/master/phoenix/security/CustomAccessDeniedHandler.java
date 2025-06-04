package cn.master.phoenix.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by 11's papa on 2025/4/25
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpServletResponse.SC_FORBIDDEN);
        responseBody.put("message", accessDeniedException.getMessage());
        responseBody.put("error", "Forbidden");
        responseBody.put("path", request.getRequestURI());
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}
