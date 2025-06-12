package cn.master.phoenix.security;

import cn.master.phoenix.entity.SystemRole;
import cn.master.phoenix.entity.SystemUser;
import com.mybatisflex.core.query.QueryChain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.master.phoenix.entity.table.SystemRoleTableDef.SYSTEM_ROLE;
import static cn.master.phoenix.entity.table.SystemUserRoleTableDef.SYSTEM_USER_ROLE;
import static cn.master.phoenix.entity.table.SystemUserTableDef.SYSTEM_USER;

/**
 * @author Created by 11's papa on 2025/4/25
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = QueryChain.of(SystemUser.class).where(SYSTEM_USER.USERNAME.eq(username)).oneOpt()
                .orElseThrow(() -> new UsernameNotFoundException("User is not found by username:" + username));
        List<SystemRole> systemRoles = QueryChain.of(SystemRole.class).select(SYSTEM_ROLE.ALL_COLUMNS)
                .from(SYSTEM_ROLE)
                .innerJoin(SYSTEM_USER_ROLE).on(SYSTEM_USER_ROLE.ROLE_ID.eq(SYSTEM_ROLE.ID)
                        .and(SYSTEM_USER_ROLE.USER_ID.eq(systemUser.getId())))
                .listAs(SystemRole.class);
        systemUser.setRoles(systemRoles);
        return UserPrincipal.create(systemUser);
    }
}
