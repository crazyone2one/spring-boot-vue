package cn.master.phoenix.service.impl;

import cn.master.phoenix.entity.SystemRole;
import cn.master.phoenix.entity.SystemUser;
import cn.master.phoenix.mapper.SystemUserMapper;
import cn.master.phoenix.service.SystemUserService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.master.phoenix.entity.table.SystemRoleTableDef.SYSTEM_ROLE;
import static cn.master.phoenix.entity.table.SystemUserRoleTableDef.SYSTEM_USER_ROLE;
import static cn.master.phoenix.entity.table.SystemUserTableDef.SYSTEM_USER;

/**
 * 系统用户表 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Override
    public SystemUser loadUserByUsername(String username) {
        SystemUser systemUser = queryChain().where(SYSTEM_USER.USERNAME.eq(username)).oneOpt()
                .orElseThrow(() -> new UsernameNotFoundException("User is not found by username:" + username));
        List<SystemRole> systemRoles = queryChain().select(SYSTEM_ROLE.ALL_COLUMNS)
                .from(SYSTEM_ROLE)
                .leftJoin(SYSTEM_USER_ROLE).on(SYSTEM_USER_ROLE.USER_ID.eq(SYSTEM_ROLE.ID)
                        .and(SYSTEM_USER_ROLE.USER_ID.eq(systemUser.getId())))
                .listAs(SystemRole.class);
        systemUser.setRoles(systemRoles);
        return systemUser;
    }
}
