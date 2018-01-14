package com.example.demo.util.convert;

import com.example.demo.model.User;
import com.example.demo.model.UserPage;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.dto.UserPageDTO;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户信息转换器
 * Author by JingQ on 2018/1/1
 */
@Service
public class UserConverter extends ModelMapper {

    public UserDTO user2DTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = this.map(user, UserDTO.class);
        String authority = StringUtils.leftPad(Integer.toBinaryString(Integer.valueOf(userDTO.getAuthority())), 4, "0");
        userDTO.setAuthority(authority);
        return userDTO;
    }

    public User dto2User(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        return this.map(dto, User.class);
    }

    /**
     * 补齐权限的前缀0
     *
     * @param users users
     * @return userDTO
     */
    public List<UserDTO> users2DTOS(List<User> users) {
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        List<UserDTO> userDTOS = this.map(users, new TypeToken<List<UserDTO>>() {
        }.getType());
        for (UserDTO userDTO : userDTOS) {
            String authority = StringUtils.leftPad(Integer.toBinaryString(Integer.valueOf(userDTO.getAuthority())), 4, "0");
            userDTO.setAuthority(authority);
        }
        return userDTOS;
    }

    /**
     * userPage ---> userPage DTO
     *
     * @param page 分页
     * @return DTO
     */
    public UserPageDTO userPage2DTO(UserPage page) {
        if (page == null) {
            return null;
        }
        return this.map(page, UserPageDTO.class);
    }
}
