package cn.master.phoenix.service;

import cn.master.phoenix.payload.BasePageRequest;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import cn.master.phoenix.entity.UserRole;

/**
 *  服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
public interface UserRoleService extends IService<UserRole> {

    UserRole add(UserRole userRole);

    Page<UserRole> queryByPage(BasePageRequest request);
}
