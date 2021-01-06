package com.hugovallada.bookstoremanager.users.builder;

import com.hugovallada.bookstoremanager.users.dto.UserDTO;
import com.hugovallada.bookstoremanager.users.entity.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Hugo Vallada";

    @Builder.Default
    private int age = 25;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "hugo@teste.com";

    @Builder.Default
    private String username = "hugovallada";

    @Builder.Default
    private String password = "123456";

    @Builder.Default
    private LocalDate birthDate = LocalDate.of(1995, 12, 16);

    public UserDTO buildUserDTO() {
        return new UserDTO(
                id,
                name,
                age,
                gender,
                email,
                username,
                password,
                birthDate
        );
    }
}
