package com.example.demo.util.convert;

import com.example.demo.model.User;
import com.example.demo.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        return this.map(user, UserDTO.class);
    }

    public User dto2User(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        return this.map(dto, User.class);
    }
}
