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
import cn.master.phoenix.entity.SystemLog;
import cn.master.phoenix.service.SystemLogService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

/**
 * 系统操作日志表 控制层。
 *
 * @author 11's papa
 * @since 1.0.0 2025-06-13
 */
@RestController
@Tag(name = "系统操作日志表接口")
@RequestMapping("/systemLog")
public class SystemLogController {

    @Autowired
    private SystemLogService systemLogService;

    /**
     * 添加系统操作日志表。
     *
     * @param systemLog 系统操作日志表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(description="保存系统操作日志表")
    public boolean save(@RequestBody @Parameter(description="系统操作日志表")SystemLog systemLog) {
        return systemLogService.save(systemLog);
    }

    /**
     * 根据主键删除系统操作日志表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(description="根据主键系统操作日志表")
    public boolean remove(@PathVariable @Parameter(description="系统操作日志表主键") String id) {
        return systemLogService.removeById(id);
    }

    /**
     * 根据主键更新系统操作日志表。
     *
     * @param systemLog 系统操作日志表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(description="根据主键更新系统操作日志表")
    public boolean update(@RequestBody @Parameter(description="系统操作日志表主键") SystemLog systemLog) {
        return systemLogService.updateById(systemLog);
    }

    /**
     * 查询所有系统操作日志表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(description="查询所有系统操作日志表")
    public List<SystemLog> list() {
        return systemLogService.list();
    }

    /**
     * 根据系统操作日志表主键获取详细信息。
     *
     * @param id 系统操作日志表主键
     * @return 系统操作日志表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(description="根据主键获取系统操作日志表")
    public SystemLog getInfo(@PathVariable @Parameter(description="系统操作日志表主键") String id) {
        return systemLogService.getById(id);
    }

    /**
     * 分页查询系统操作日志表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(description="分页查询系统操作日志表")
    public Page<SystemLog> page(@Parameter(description="分页信息") Page<SystemLog> page) {
        return systemLogService.page(page);
    }

}
