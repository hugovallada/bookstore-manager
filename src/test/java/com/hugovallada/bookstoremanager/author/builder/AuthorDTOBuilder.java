package com.hugovallada.bookstoremanager.author.builder;


import com.hugovallada.bookstoremanager.author.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default // Id será inicializado com valor de 1
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Hugo Lopes";

    @Builder.Default
    private final Integer age = 25;

    public AuthorDTO buildAuthorDTO(){
        return new AuthorDTO(id, name, age);
    }

}
