package com.fancier.prototype.auth.mapstruct;

import com.fancier.prototype.common.model.dto.UserUpdateMyRequest;
import com.fancier.prototype.common.model.vo.LoginUserVO;
import com.fancier.prototype.common.model.vo.UserVO;
import com.fancier.prototype.common.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 用户 Pojo 转换器
 *
 * @author <a href="https://github.com/hola1009">fancier</a>
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    LoginUserVO DO2LoginUserVO(User user);

    UserVO DO2UserVO(User user);

    @Mapping(target = "userRole", ignore = true)
    @Mapping(target = "userPassword", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "unionId", ignore = true)
    @Mapping(target = "mpOpenId", ignore = true)
    @Mapping(target = "isDelete", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "editTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    User updateDTO2DO(UserUpdateMyRequest userUpdateMyRequest);

}
