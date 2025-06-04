package cn.master.phoenix.controller;

import cn.master.phoenix.entity.SystemUser;
import cn.master.phoenix.service.SystemUserService;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户表 控制层。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
@RestController
@RequestMapping("/system-user")
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService systemUserService;

    /**
     * 添加系统用户表。
     *
     * @param systemUser 系统用户表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody SystemUser systemUser) {
        return systemUserService.save(systemUser);
    }

    /**
     * 根据主键删除系统用户表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable String id) {
        return systemUserService.removeById(id);
    }

    /**
     * 根据主键更新系统用户表。
     *
     * @param systemUser 系统用户表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody SystemUser systemUser) {
        return systemUserService.updateById(systemUser);
    }

    /**
     * 查询所有系统用户表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<SystemUser> list() {
        return systemUserService.list();
    }

    /**
     * 根据系统用户表主键获取详细信息。
     *
     * @param id 系统用户表主键
     * @return 系统用户表详情
     */
    @GetMapping("getInfo/{id}")
    public SystemUser getInfo(@PathVariable String id) {
        return systemUserService.getById(id);
    }

    /**
     * 分页查询系统用户表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<SystemUser> page(Page<SystemUser> page) {
        return systemUserService.page(page);
    }

}
