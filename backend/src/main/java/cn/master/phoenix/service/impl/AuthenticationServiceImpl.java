package cn.master.phoenix.service.impl;

import cn.master.phoenix.payload.request.AuthenticationRequest;
import cn.master.phoenix.payload.response.AuthenticationResponse;
import cn.master.phoenix.payload.response.UserDTO;
import cn.master.phoenix.security.CustomUserDetailsService;
import cn.master.phoenix.security.JwtService;
import cn.master.phoenix.security.UserPrincipal;
import cn.master.phoenix.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Created by 11's papa on 2025/4/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        stringRedisTemplate.opsForHash().putAll(userDetails.getUsername(), map);
        UserDTO userDTO = new UserDTO(userDetails.getId(), userDetails.getUsername(), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).user(userDTO).build();
    }

    @Override
    public AuthenticationResponse refreshToken(AuthenticationResponse token) {
        try {
            String username = jwtService.extractUsername(token.getRefreshToken());
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token.getRefreshToken(), userDetails)) {
                String accessToken = jwtService.generateAccessToken(userDetails);
                return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(token.getRefreshToken()).build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
