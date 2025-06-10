package cn.master.phoenix.service;

import cn.master.phoenix.payload.BasePageRequest;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import cn.master.phoenix.entity.SystemUser;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统用户表 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
public interface SystemUserService extends IService<SystemUser> {

    /***
     * 根据用户名查询对应的用户信息
     * @param username user name
     * @return SystemUser
     */
    SystemUser loadUserByUsername(String username);

    String saveUser(SystemUser user);

    SystemUser getUserByKeyword(String keyword);

    String importByExcel(MultipartFile file);

    Page<SystemUser> getUserByPage(BasePageRequest request);
}
