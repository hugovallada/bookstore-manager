package com.hugovallada.bookstoremanager.users.service;

import com.hugovallada.bookstoremanager.users.builder.UserDTOBuilder;
import com.hugovallada.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.hugovallada.bookstoremanager.users.exception.UserNotFoundException;
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


    @Test
    void whenValidUserIsInformedThenItShouldBeDeleted() {
        var expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        var expectedDeletedUser = userMapper.toModel(expectedDeletedUserDTO);

        Mockito.when(userRepository.findById(expectedDeletedUserDTO.getId()))
                .thenReturn(Optional.of(expectedDeletedUser));

        Mockito.doNothing().when(userRepository).deleteById(expectedDeletedUserDTO.getId());

        userService.delete(expectedDeletedUserDTO.getId());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(expectedDeletedUserDTO.getId());
    }

    @Test
    void whenInvalidUserIsInformedThenAnExceptionShouldBeThrown() {
        var expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();

        Mockito.when(userRepository.findById(expectedDeletedUserDTO.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.delete(expectedDeletedUserDTO.getId()));
    }

    @Test
    void whenExistingUserIsInformedThenItShouldBeUpdated() {
        var expectedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUserDTO.setUsername("valladahugo");
        var expectedUser = userMapper.toModel(expectedUserDTO);
        var expectedMessage = "User valladahugo with ID 1 successfully updated";

        Mockito.when(userRepository.findById(expectedUserDTO.getId()))
                .thenReturn(Optional.of(expectedUser));

        Mockito.when(userRepository.save(expectedUser))
                .thenReturn(expectedUser);

        var successMessage = userService.update(expectedUserDTO.getId(), expectedUserDTO);

        MatcherAssert.assertThat(successMessage.getMessage(), Matchers.is(Matchers.equalTo(expectedMessage)));
    }

    @Test
    void whenNotExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        var expectedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUserDTO.setUsername("valladahugo");


        Mockito.when(userRepository.findById(expectedUserDTO.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.update(expectedUserDTO.getId(), expectedUserDTO);
        });
    }
}
