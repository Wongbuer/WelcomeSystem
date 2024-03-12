package com.wong.usersystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.common.model.entity.User;
import com.wong.common.utils.CommonResponse;
import com.wong.common.utils.PageRequest;
import com.wong.common.utils.ResultUtil;
import com.wong.common.validate.AddGroup;
import com.wong.common.validate.LoginGroup;
import com.wong.usersystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author Wongbuer
 * @since 2024-03-08 23:17:12
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 分页查询所有数据
     *
     * @param pageRequest 分页请求对象
     * @return 所有数据
     */
    @Operation(summary = "分页查询数据")
    @Parameters({
            @Parameter(name = "pageRequest", description = "分页请求对象")
    })
    @GetMapping
    public CommonResponse<List<User>> selectByPage(@NotNull PageRequest pageRequest) {
        IPage<User> page = new Page<>();
        page.setCurrent(pageRequest.getPage());
        page.setSize(pageRequest.getSize());
        List<User> records = userService.page(page).getRecords();
        return ResultUtil.success(records);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Operation(summary = "通过主键查询单条数据")
    @Parameters({
            @Parameter(name = "id", description = "主键")
    })
    @GetMapping("{id}")
    public CommonResponse<User> selectOne(@NotNull @PathVariable Serializable id) {
        User user = userService.getById(id);
        return ResultUtil.success(user);
    }

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @Operation(summary = "新增数据")
    @Parameters({
            @Parameter(name = "user", description = "实体对象")
    })
    @PostMapping
    public CommonResponse<Boolean> insert(@Validated(AddGroup.class) @RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        boolean isSaved = userService.save(user);

        return isSaved ? ResultUtil.success("用户新增成功") : ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, "用户新增失败");
    }

    /**
     * 修改数据
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @Operation(summary = "修改数据")
    @Parameters({
            @Parameter(name = "user", description = "实体对象")
    })
    @PutMapping
    public CommonResponse<Boolean> update(@RequestBody User user) {
        boolean isUpdated = userService.updateById(user);
        return ResultUtil.success("用户更新成功");
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @Operation(summary = "删除数据")
    @Parameters({
            @Parameter(name = "idList", description = "主键结合")
    })
    @DeleteMapping
    public CommonResponse<Boolean> delete(@NotEmpty @RequestParam("idList") List<Long> idList) {
        boolean isDeleted = userService.removeByIds(idList);
        return ResultUtil.success("用户删除成功");
    }

    /**
     * 用户登录
     *
     * @param user 用户实体
     * @return 登录结果
     */
    @Operation(summary = "用户登录")
    @Parameters({
            @Parameter(name = "user", description = "用户实体")
    })
    @PostMapping("/login")
    public CommonResponse<?> login(@Validated(LoginGroup.class) @RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/success")
    public CommonResponse<String> success() {
        return ResultUtil.success("success");
    }
}

