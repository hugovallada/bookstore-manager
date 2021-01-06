package com.hugovallada.bookstoremanager.users.controller;

import com.hugovallada.bookstoremanager.users.builder.UserDTOBuilder;
import com.hugovallada.bookstoremanager.users.dto.MessageDTO;
import com.hugovallada.bookstoremanager.users.service.UserService;
import com.hugovallada.bookstoremanager.utils.JsonConversionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    public static final String USERS_API_URL = "/api/v1/users";
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        var expectedUserDTO = userDTOBuilder.buildUserDTO();
        var expectedMessage = "User hugovallada with ID 1 successfully created";
        var expectedCreationMessage = MessageDTO.builder().message(expectedMessage).build();

        Mockito.when(userService.create(expectedUserDTO))
                .thenReturn(expectedCreationMessage);

        mockMvc.perform(MockMvcRequestBuilders.post(USERS_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedUserDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(expectedMessage)));

    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeReturned() throws Exception {
        var expectedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUserDTO.setUsername("");


        mockMvc.perform(MockMvcRequestBuilders.post(USERS_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedUserDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
