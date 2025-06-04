package cn.master.phoenix.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import cn.master.phoenix.entity.SystemRole;
import cn.master.phoenix.service.SystemRoleService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 *  控制层。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
@RestController
@RequestMapping("/systemRole")
public class SystemRoleController {

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 添加。
     *
     * @param systemRole 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody SystemRole systemRole) {
        return systemRoleService.save(systemRole);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable String id) {
        return systemRoleService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param systemRole 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody SystemRole systemRole) {
        return systemRoleService.updateById(systemRole);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<SystemRole> list() {
        return systemRoleService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public SystemRole getInfo(@PathVariable String id) {
        return systemRoleService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<SystemRole> page(Page<SystemRole> page) {
        return systemRoleService.page(page);
    }

}
