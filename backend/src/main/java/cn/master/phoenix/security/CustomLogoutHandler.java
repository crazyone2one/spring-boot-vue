package cn.master.phoenix.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Created by 11's papa on 2025/6/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
                log.warn("No authenticated user found for logout");
                SecurityContextHolder.clearContext();
            }
            if (authentication.getPrincipal() != null) {
                log.info("Authentication principal: {}, type: {}", authentication.getPrincipal(), authentication.getPrincipal().getClass().getName());
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                String username = userPrincipal.getUsername();
                Boolean deleted = stringRedisTemplate.delete(username);
                log.info("Token deletion result: {}", deleted);
                SecurityContextHolder.clearContext();
            } else {
                SecurityContextHolder.clearContext();
            }
        } catch (Exception ex) {
            log.error("Error during logout: {}", ex.getMessage());
            SecurityContextHolder.clearContext();
            throw ex;
        }
    }
}
