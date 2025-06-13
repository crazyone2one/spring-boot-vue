package cn.master.phoenix.service.impl;

import cn.master.phoenix.entity.SystemUser;
import cn.master.phoenix.entity.UserRole;
import cn.master.phoenix.entity.UserRoleRelation;
import cn.master.phoenix.exception.CustomException;
import cn.master.phoenix.exception.InvalidEmailException;
import cn.master.phoenix.handler.convert.SystemUserConvert;
import cn.master.phoenix.handler.validator.EmailValidator;
import cn.master.phoenix.mapper.SystemUserMapper;
import cn.master.phoenix.mapper.UserRoleRelationMapper;
import cn.master.phoenix.payload.BasePageRequest;
import cn.master.phoenix.payload.request.SaveUserRequest;
import cn.master.phoenix.service.SystemUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static cn.master.phoenix.entity.table.SystemUserTableDef.SYSTEM_USER;
import static cn.master.phoenix.entity.table.UserRoleRelationTableDef.USER_ROLE_RELATION;
import static cn.master.phoenix.entity.table.UserRoleTableDef.USER_ROLE;

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
    private final UserRoleRelationMapper userRoleRelationMapper;
    private final SystemUserConvert systemUserConvert;

    @Override
    public SystemUser loadUserByUsername(String username) {
        SystemUser systemUser = queryChain().where(SYSTEM_USER.USERNAME.eq(username)).oneOpt()
                .orElseThrow(() -> new UsernameNotFoundException("User is not found by username:" + username));
        List<UserRole> userRoles = queryChain().select(USER_ROLE.ALL_COLUMNS)
                .from(USER_ROLE)
                .leftJoin(USER_ROLE_RELATION).on(USER_ROLE_RELATION.USER_ID.eq(USER_ROLE.ID)
                        .and(USER_ROLE_RELATION.USER_ID.eq(systemUser.getId())))
                .listAs(UserRole.class);
        systemUser.setRoles(userRoles);
        return systemUser;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String saveUser(SaveUserRequest userRequest) {
        if (queryChain().where(SYSTEM_USER.USERNAME.eq(userRequest.getUsername())).exists()) {
            throw new CustomException("用户已存在");
        }
        if (!userRequest.getEmail().isEmpty()) {
            checkUserEmail(userRequest.getId(), userRequest.getEmail());
            EmailValidator validator = new EmailValidator();
            try {
                validator.validateEmail(userRequest.getEmail());
            } catch (InvalidEmailException exception) {
                throw new CustomException(exception.getMessage());
            }
        }
        SystemUser systemUser = systemUserConvert.convert(userRequest);
        systemUser.setPassword(passwordEncoder.encode("password"));
        save(systemUser);
        if (!userRequest.getRoleIds().isEmpty()) {
            userRequest.getRoleIds().forEach(roleId -> {
                UserRoleRelation userRoleRelation = UserRoleRelation.builder().userId(systemUser.getId()).roleId(roleId).build();
                userRoleRelationMapper.insert(userRoleRelation);
            });
        }
        return userRequest.getId();
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
        Page<SystemUser> page = queryChain()
                .where(SYSTEM_USER.ID.eq(request.getKeyword())
                        .or(SYSTEM_USER.USERNAME.like(request.getKeyword()))
                        .or(SYSTEM_USER.EMAIL.like(request.getKeyword()))
                )
                .page(new Page<>(request.getPage(), request.getPageSize()));
        if (!page.getRecords().isEmpty()) {
            page.getRecords().forEach(item -> {
                List<UserRole> roles = QueryChain.of(UserRole.class).from(USER_ROLE)
                        .innerJoin(USER_ROLE_RELATION).on(USER_ROLE_RELATION.USER_ID.eq(item.getId())
                                .and(USER_ROLE_RELATION.ROLE_ID.eq(USER_ROLE.ID))).list();
                item.setRoles(roles);
            });
        }
        return page;
    }

    @Override
    public String updateUser(SaveUserRequest userRequest) {
        if (!userRequest.getEmail().isEmpty()) {
            checkUserEmail(userRequest.getId(), userRequest.getEmail());
            EmailValidator validator = new EmailValidator();
            try {
                validator.validateEmail(userRequest.getEmail());
            } catch (InvalidEmailException exception) {
                throw new CustomException(exception.getMessage());
            }
        }
        SystemUser systemUser = systemUserConvert.convert(userRequest);
        mapper.update(systemUser);
        if (!userRequest.getRoleIds().isEmpty()) {
            QueryChain<UserRoleRelation> userRoleRelationQueryChain =
                    QueryChain.of(UserRoleRelation.class).where(USER_ROLE_RELATION.USER_ID.eq(systemUser.getId()));
            userRoleRelationMapper.deleteByQuery(userRoleRelationQueryChain);
            userRequest.getRoleIds().forEach(roleId -> {
                UserRoleRelation userRoleRelation = UserRoleRelation.builder().userId(systemUser.getId()).roleId(roleId).build();
                userRoleRelationMapper.insert(userRoleRelation);
            });
        }
        return systemUser.getId();
    }

    private void checkUserEmail(String id, String email) {
        boolean exists = queryChain().where(SYSTEM_USER.ID.ne(id).and(SYSTEM_USER.EMAIL.eq(email))).exists();
        if (exists) {
            throw new CustomException("用户邮箱已存在");
        }
    }
}
