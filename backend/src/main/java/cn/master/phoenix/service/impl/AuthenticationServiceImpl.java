package cn.master.phoenix.service.impl;

import cn.master.phoenix.payload.request.AuthenticationRequest;
import cn.master.phoenix.security.CustomUserDetailsService;
import cn.master.phoenix.security.JwtService;
import cn.master.phoenix.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public AuthenticationResponse refreshToken(AuthenticationResponse token) {
        try {
            String username = jwtService.extractUsername(token.refreshToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token.refreshToken, userDetails)) {
                String accessToken = jwtService.generateAccessToken(userDetails);
                return new AuthenticationResponse(accessToken, accessToken);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public record AuthenticationResponse(String accessToken, String refreshToken) {
    }
}
