package cn.master.phoenix.controller;

import cn.master.phoenix.entity.UserRole;
import cn.master.phoenix.payload.BasePageRequest;
import cn.master.phoenix.service.UserRoleService;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
@RestController
@RequestMapping("/user/role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    /**
     * 添加。
     *
     * @param userRole
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public UserRole save(@RequestBody UserRole userRole) {
        return userRoleService.add(userRole);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable String id) {
        return userRoleService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param userRole
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody UserRole userRole) {
        return userRoleService.updateById(userRole);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<UserRole> list() {
        return userRoleService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public UserRole getInfo(@PathVariable String id) {
        return userRoleService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param request 分页对象
     * @return 分页对象
     */
    @PostMapping("page")
    public Page<UserRole> page(@Validated @RequestBody BasePageRequest request) {
        return userRoleService.queryByPage(request);
    }

}
