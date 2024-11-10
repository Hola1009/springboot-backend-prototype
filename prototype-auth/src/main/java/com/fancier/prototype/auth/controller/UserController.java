package com.fancier.prototype.auth.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fancier.prototype.auth.mapstruct.UserConvert;
import com.fancier.prototype.auth.service.UserService;
import com.fancier.prototype.common.constant.UserConstant;
import com.fancier.prototype.common.expection.ErrorCode;
import com.fancier.prototype.common.expection.ThrowUtils;
import com.fancier.prototype.common.model.common.BaseResponse;
import com.fancier.prototype.common.model.common.ResultUtils;
import com.fancier.prototype.common.model.dto.UserLoginRequest;
import com.fancier.prototype.common.model.dto.UserQueryRequest;
import com.fancier.prototype.common.model.dto.UserRegisterRequest;
import com.fancier.prototype.common.model.dto.UserUpdateMyRequest;
import com.fancier.prototype.common.model.entity.User;
import com.fancier.prototype.common.model.vo.LoginUserVO;
import com.fancier.prototype.common.model.vo.UserVO;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理控制层
 *
 * @author <a href="https://github.com/hola1009">fancier</a>
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // region 登录相关

    /**
     * 用户注册
     *
     * @param userRegisterRequest userRegisterRequest
     * @return result
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        Preconditions.checkArgument(userRegisterRequest != null);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest userLoginRequest
     * @param request request
     * @return result
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        Preconditions.checkArgument(userLoginRequest != null);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        Preconditions.checkArgument(!StringUtils.isAnyBlank(userAccount, userPassword));

        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }


    /**
     * 用户注销
     *
     * @param request the request
     * @return the result
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        Preconditions.checkArgument(request != null);
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request request
     * @return result
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResultUtils.success(UserConvert.INSTANCE.DO2LoginUserVO(user));
    }



    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest userQueryRequest
     * @return result
     */
    @PostMapping("/list/page")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        Page<User> userPage = userService.pageQuery(userQueryRequest);
        return ResultUtils.success(userPage);
    }

    /**
     * 分页获取用户封装列表
     *
     * @param userQueryRequest userQueryRequest
     * @return result
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        Page<UserVO> userVOPage = userService.VOPageQuery(userQueryRequest);
        return ResultUtils.success(userVOPage);
    }

    /**
     * 更新个人信息
     *
     * @param userUpdateMyRequest userUpdateMyRequest
     * @param request  request
     * @return result
     */
    @PostMapping("/update/my")
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest,
                                              HttpServletRequest request) {
        // 参数校验
        Preconditions.checkArgument(userUpdateMyRequest != null);

        // 封装 DO
        User loginUser = userService.getLoginUser(request);
        User user = UserConvert.INSTANCE.updateDTO2DO(userUpdateMyRequest);
        user.setId(loginUser.getId());

        // 执行
        ThrowUtils.throwIf(!userService.updateById(user), ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(true);
    }
}
