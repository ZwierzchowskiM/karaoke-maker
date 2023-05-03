package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.dto.UserDto;
import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.repositories.UserRepository;
import com.karaoke.karaokemaker.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Test
    void getUser_thenReturnUserStatusOk() throws Exception {

        User user1 = new User();
        user1.setFirstName("Foo");
        user1.setId(1L);

        when(userService.findUserById(user1.getId())).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/api/v1/users/" + user1.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Foo")));

    }

    @Test
    void getUsers_thenReturnAllUsersStatusOk() throws Exception {

        UserDto user1 = new UserDto();
        user1.setFirstName("Foo");

        UserDto user2 = new UserDto();
        user2.setFirstName("Bar");


        List<UserDto> allUsers = Arrays.asList(user1,user2);
        when(userService.findUsers()).thenReturn(allUsers);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Foo")))
                .andExpect(jsonPath("$[1].firstName", is("Bar")));
    }

}