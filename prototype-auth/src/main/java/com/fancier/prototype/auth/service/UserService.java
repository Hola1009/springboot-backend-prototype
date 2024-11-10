package com.fancier.prototype.auth.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fancier.prototype.common.model.dto.UserQueryRequest;
import com.fancier.prototype.common.model.vo.LoginUserVO;
import com.fancier.prototype.common.model.entity.User;
import com.fancier.prototype.common.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务层接口
 *
 * @author <a href="https://github.com/hola1009">fancier</a>
 *
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request http request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 获取当前登录用户
     *
     * @param request http request
     * @return response
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request http request
     * @return response
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request http request
     * @return response
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user user
     * @return response
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @param request http request
     * @return response
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 用户分页查询
     */
    Page<User> pageQuery(UserQueryRequest userQueryRequest);

    /**
     * 分页获取用户封装列表
     */
    Page<UserVO> VOPageQuery(UserQueryRequest userQueryRequest);

    /**
     * 组装 wrapper
     */
    Wrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
