package com.wong.common.feign;

import com.wong.common.model.entity.User;
import com.wong.common.utils.CommonResponse;
import com.wong.common.utils.PageRequest;
import com.wong.common.validate.LoginGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wongbuer
 */
@FeignClient(value = "user-system", path = "/user")
public interface UserFeign {
    /**
     * 分页查询数据
     *
     * @param pageRequest
     * @return
     */
    @GetMapping
    CommonResponse<List<User>> selectByPage(@NotNull PageRequest pageRequest);

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    CommonResponse<User> selectOne(@NotNull @PathVariable Serializable id);

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping
    CommonResponse<Boolean> insert(@Valid @RequestBody User user);

    /**
     * 修改数据
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @PutMapping
    CommonResponse<Boolean> update(@RequestBody User user);

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    CommonResponse<Boolean> delete(@NotEmpty @RequestParam("idList") List<Long> idList);

    /**
     * 用户登录
     *
     * @param user 用户实体
     * @return 登录结果
     */
    @PostMapping("/login")
    CommonResponse<?> login(@Validated(LoginGroup.class) @RequestBody User user);

}
