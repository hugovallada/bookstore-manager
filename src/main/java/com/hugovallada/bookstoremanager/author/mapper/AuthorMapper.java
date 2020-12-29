package com.hugovallada.bookstoremanager.author.mapper;

import com.hugovallada.bookstoremanager.author.dto.AuthorDTO;
import com.hugovallada.bookstoremanager.author.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO); // método precisa ter esse nome

    AuthorDTO toDTO(Author author); // método precisa ter esse nome

}
