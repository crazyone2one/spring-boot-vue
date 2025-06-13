package cn.master.phoenix.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.phoenix.entity.UserRoleRelation;
import cn.master.phoenix.mapper.UserRoleRelationMapper;
import cn.master.phoenix.service.SystemUserRoleService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<UserRoleRelationMapper, UserRoleRelation>  implements SystemUserRoleService{

}
