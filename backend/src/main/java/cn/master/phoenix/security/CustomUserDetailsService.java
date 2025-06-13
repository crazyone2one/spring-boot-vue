package cn.master.phoenix.security;

import cn.master.phoenix.entity.UserRole;
import cn.master.phoenix.entity.SystemUser;
import com.mybatisflex.core.query.QueryChain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.master.phoenix.entity.table.UserRoleTableDef.USER_ROLE;
import static cn.master.phoenix.entity.table.UserRoleRelationTableDef.USER_ROLE_RELATION;
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
        List<UserRole> systemRoles = QueryChain.of(UserRole.class).select(USER_ROLE.ALL_COLUMNS)
                .from(USER_ROLE)
                .innerJoin(USER_ROLE_RELATION).on(USER_ROLE_RELATION.ROLE_ID.eq(USER_ROLE.ID)
                        .and(USER_ROLE_RELATION.USER_ID.eq(systemUser.getId())))
                .listAs(UserRole.class);
        systemUser.setRoles(systemRoles);
        return UserPrincipal.create(systemUser);
    }
}
