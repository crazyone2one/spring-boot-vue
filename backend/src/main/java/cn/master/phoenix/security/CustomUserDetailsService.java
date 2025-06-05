package cn.master.phoenix.security;

import cn.master.phoenix.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Created by 11's papa on 2025/4/25
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SystemUserService systemUserService;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserPrincipal.create(systemUserService.loadUserByUsername(username));
    }
}
