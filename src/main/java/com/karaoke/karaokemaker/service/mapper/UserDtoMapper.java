package com.karaoke.karaokemaker.service.mapper;

import com.karaoke.karaokemaker.dto.UserDto;
import com.karaoke.karaokemaker.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {

    public UserDto from(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public List<UserDto> fromList(List<User> users) {
        return users.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }
}
