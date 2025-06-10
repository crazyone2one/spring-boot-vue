package cn.master.phoenix.controller;

import cn.master.phoenix.entity.SystemUser;
import cn.master.phoenix.payload.BasePageRequest;
import cn.master.phoenix.service.SystemUserService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统用户表 控制层。
 *
 * @author 11's papa
 * @since 1.0.0 2025-04-27
 */
@RestController
@RequestMapping("/system/user")
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
    public String save(@RequestBody SystemUser systemUser) {
        return systemUserService.saveUser(systemUser);
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
     * 通过email或id查找用户。
     *
     * @param keyword 系统用户表主键
     * @return 系统用户表详情
     */
    @GetMapping("getInfo/{keyword}")
    @Operation(summary = "通过email或id查找用户")
    public SystemUser getInfo(@PathVariable String keyword) {
        return systemUserService.getUserByKeyword(keyword);
    }

    /**
     * 分页查询系统用户表。
     *
     * @param request 分页对象
     * @return 分页对象
     */
    @PostMapping("/page")
    public Page<SystemUser> page(@Validated @RequestBody BasePageRequest request) {
        return systemUserService.getUserByPage(request);
    }

    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    @Operation(summary = "系统设置-系统-用户-导入用户")
    public String importUser(@RequestPart(value = "file", required = false) MultipartFile file) {
        return systemUserService.importByExcel(file);
    }
}
