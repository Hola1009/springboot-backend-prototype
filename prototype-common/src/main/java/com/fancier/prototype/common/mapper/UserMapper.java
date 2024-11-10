package com.fancier.prototype.common.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fancier.prototype.common.model.entity.User;
import org.springframework.util.DigestUtils;

import java.util.List;

import static com.fancier.prototype.common.constant.UserConstant.SALT;

public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /**
     * 检查用户是否存在
     */
    default User checkUser(String userAccount, String userPassword) {
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(StringUtils.isNotBlank(userAccount), User::getUserAccount, userAccount)
                .eq(StringUtils.isNotBlank(encryptPassword), User::getUserPassword, encryptPassword);

        return this.selectOne(wrapper);
    }
}