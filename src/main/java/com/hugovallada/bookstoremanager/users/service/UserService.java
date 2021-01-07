package com.hugovallada.bookstoremanager.users.service;

import com.hugovallada.bookstoremanager.users.dto.MessageDTO;
import com.hugovallada.bookstoremanager.users.dto.UserDTO;
import com.hugovallada.bookstoremanager.users.entity.User;
import com.hugovallada.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.hugovallada.bookstoremanager.users.exception.UserNotFoundException;
import com.hugovallada.bookstoremanager.users.mapper.UserMapper;
import com.hugovallada.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hugovallada.bookstoremanager.users.utils.MessageDTOUtils.creationMessage;
import static com.hugovallada.bookstoremanager.users.utils.MessageDTOUtils.updatedMessage;

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

    public void delete(Long id) {
        verifyIfExists(id);
        userRepository.deleteById(id);
    }

    public MessageDTO update(Long id, UserDTO userDTO) {
        var foundUser = verifyAndGetIfExists(id);

        userDTO.setId(foundUser.getId());

        var userToUpdate = userMapper.toModel(userDTO);
        userToUpdate.setCreatedDate(foundUser.getCreatedDate());

        var updatedUser = userRepository.save(userToUpdate);

        return updatedMessage(updatedUser);

    }


    private User verifyAndGetIfExists(Long id) {
        var user = userRepository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException(id);

        return user.get();
    }

    private void verifyIfExists(Long id) {
        var user = userRepository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException(id);
    }

    private void verifyIfExists(String email, String username) {
        var user = userRepository.findByEmailOrUsername(email, username);

        if (user.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }


}
