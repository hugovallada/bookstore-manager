package com.hugovallada.bookstoremanager.users.utils;

import com.hugovallada.bookstoremanager.users.dto.MessageDTO;
import com.hugovallada.bookstoremanager.users.entity.User;

public class MessageDTOUtils {

    public static MessageDTO creationMessage(User createdUser) {

        return returnMessage(createdUser, "created");
    }

    public static MessageDTO updatedMessage(User updatedUser) {
        return returnMessage(updatedUser, "updated");
    }

    private static MessageDTO returnMessage(User user, String action) {
        var message = String.format(
                "User %s with ID %s successfully %s",
                user.getUsername(),
                user.getId(),
                action
        );

        return MessageDTO.builder().message(message).build();
    }
}
