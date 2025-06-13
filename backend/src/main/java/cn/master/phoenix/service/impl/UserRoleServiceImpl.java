package cn.master.phoenix.service.impl;

import cn.master.phoenix.entity.UserRole;
import cn.master.phoenix.exception.CustomException;
import cn.master.phoenix.mapper.UserRoleMapper;
import cn.master.phoenix.payload.BasePageRequest;
import cn.master.phoenix.service.UserRoleService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public UserRole add(UserRole userRole) {
        userRole.setInternal(false);
        checkExist(userRole);
        mapper.insert(userRole);
        return userRole;
    }

    @Override
    public Page<UserRole> queryByPage(BasePageRequest request) {
        return queryChain().where(UserRole::getName).like(request.getKeyword())
                .orderBy(UserRole::getCreateDate).desc()
                .page(new Page<>(request.getPage(), request.getPageSize()));
    }

    private void checkExist(UserRole userRole) {
        if (StringUtils.isBlank(userRole.getName())) {
            return;
        }
        boolean exists = queryChain().where(UserRole::getName).eq(userRole.getName())
                .and(UserRole::getId).ne(userRole.getId())
                .exists();
        if (exists) {
            throw new CustomException("user role existed");
        }
    }
}
