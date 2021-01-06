package com.hugovallada.bookstoremanager.users.service;

import com.hugovallada.bookstoremanager.users.dto.MessageDTO;
import com.hugovallada.bookstoremanager.users.dto.UserDTO;
import com.hugovallada.bookstoremanager.users.entity.User;
import com.hugovallada.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.hugovallada.bookstoremanager.users.mapper.UserMapper;
import com.hugovallada.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final static UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());

        var userToCreate = userMapper.toModel(userToCreateDTO);
        var createdUser = userRepository.save(userToCreate);

        return creationMessage(createdUser);
    }

    private MessageDTO creationMessage(User createdUser) {

        var message = String.format("User %s with ID %s successfully created",
                createdUser.getUsername(), createdUser.getId());

        return MessageDTO.builder()
                .message(message)
                .build();
    }

    private void verifyIfExists(String email, String username) {
        var user = userRepository.findByEmailOrUsername(email, username);

        if (user.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }
}
