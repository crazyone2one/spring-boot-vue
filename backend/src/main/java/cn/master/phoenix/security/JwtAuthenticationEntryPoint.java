package cn.master.phoenix.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by 11's papa on 2025/4/25
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Responding with unauthorized error. Message - {}", authException.getMessage());
        // Get the error status code and message
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        // Set the default status code and message
        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        // Set the default message based on the status code
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            message = "The requested resource was not found";
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            message = "You don't have permission to access this resource";
        } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            message = "The request was invalid or cannot be served";
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            message = "You need to authenticate to access this resource";
        } else {
            message = (message != null) ? message : "Unexpected error occurred";
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);
        response.setCharacterEncoding("UTF-8");

        final Map<String, Object> body = new HashMap<>();
        body.put("status", statusCode);
        body.put("message", message);
        body.put("path", request.getServletPath());
        body.put("data", null);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
