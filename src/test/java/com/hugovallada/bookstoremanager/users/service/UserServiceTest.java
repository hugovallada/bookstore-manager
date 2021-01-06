package com.hugovallada.bookstoremanager.users.service;

import com.hugovallada.bookstoremanager.users.builder.UserDTOBuilder;
import com.hugovallada.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.hugovallada.bookstoremanager.users.mapper.UserMapper;
import com.hugovallada.bookstoremanager.users.repository.UserRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }


    @Test
    void whenNewUserIsInformedThenItShouldBeCreated() {
        var expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        var expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        var expectionCreatedMessage = "User hugovallada with ID 1 successfully created";

        Mockito.when(userRepository.findByEmailOrUsername(expectedCreatedUserDTO.getEmail(), expectedCreatedUserDTO.getUsername()))
                .thenReturn(Optional.empty());

        Mockito.when(userRepository.save(expectedCreatedUser))
                .thenReturn(expectedCreatedUser);

        var creationMessage = userService.create(expectedCreatedUserDTO);

        MatcherAssert.assertThat(expectionCreatedMessage, Matchers.is(Matchers.equalTo(creationMessage.getMessage())));

    }

    @Test
    void whenExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        var expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
        var expectedDuplicatedUser = userMapper.toModel(expectedDuplicatedUserDTO);

        Mockito.when(userRepository.findByEmailOrUsername(expectedDuplicatedUserDTO.getEmail(), expectedDuplicatedUserDTO.getUsername()))
                .thenReturn(Optional.of(expectedDuplicatedUser));

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedDuplicatedUserDTO));
    }


}
