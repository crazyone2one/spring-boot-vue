package cn.master.phoenix.service.impl;

import cn.master.phoenix.entity.SystemRole;
import cn.master.phoenix.entity.SystemUser;
import cn.master.phoenix.exception.CustomException;
import cn.master.phoenix.exception.InvalidEmailException;
import cn.master.phoenix.handler.validator.EmailValidator;
import cn.master.phoenix.mapper.SystemUserMapper;
import cn.master.phoenix.payload.BasePageRequest;
import cn.master.phoenix.service.SystemUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
@RequiredArgsConstructor
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {
    private final PasswordEncoder passwordEncoder;

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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String saveUser(SystemUser user) {
        if (queryChain().where(SYSTEM_USER.USERNAME.eq(user.getUsername())).exists()) {
            throw new CustomException("用户已存在");
        }
        if (!user.getEmail().isEmpty()) {
            checkUserEmail(user.getId(), user.getEmail());
            EmailValidator validator = new EmailValidator();
            try {
                validator.validateEmail(user.getEmail());
            } catch (InvalidEmailException exception) {
                throw new CustomException(exception.getMessage());
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        return user.getId();
    }

    @Override
    public SystemUser getUserByKeyword(String keyword) {
        return queryChain().where(SYSTEM_USER.USERNAME.eq(keyword)).or(SYSTEM_USER.ID.eq(keyword)).one();
    }

    @Override
    public String importByExcel(MultipartFile file) {
        return "";
    }

    @Override
    public Page<SystemUser> getUserByPage(BasePageRequest request) {
        return queryChain()
                .where(SYSTEM_USER.ID.eq(request.getKeyword())
                        .or(SYSTEM_USER.USERNAME.like(request.getKeyword()))
                        .or(SYSTEM_USER.EMAIL.like(request.getKeyword()))
                )
                .page(new Page<>(request.getPage(),request.getPageSize()));
    }

    private void checkUserEmail(String id, String email) {
        boolean exists = queryChain().where(SYSTEM_USER.ID.ne(id).and(SYSTEM_USER.EMAIL.eq(email))).exists();
        if (exists) {
            throw new CustomException("用户邮箱已存在");
        }
    }
}
