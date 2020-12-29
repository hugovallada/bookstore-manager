package com.hugovallada.bookstoremanager.author.service;

import com.hugovallada.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.hugovallada.bookstoremanager.author.dto.AuthorDTO;
import com.hugovallada.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.hugovallada.bookstoremanager.author.mapper.AuthorMapper;
import com.hugovallada.bookstoremanager.author.repository.AuthorRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
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
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
    }

    @Test
    void whenNewAuthorIsInformedThenInShouldBeCreated() {
        //given
        var expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
        var expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        //when
        Mockito.when(authorRepository.save(expectedCreatedAuthor))
                .thenReturn(expectedCreatedAuthor);

        Mockito.when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
                .thenReturn(Optional.empty());

        var createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);

        //then
        MatcherAssert.assertThat(createdAuthorDTO, Is.is(IsEqual.equalTo(expectedAuthorToCreateDTO)));
    }

    @Test
    void whenExistingAuthorIsInformedThenAnExceptionShouldBeThrown() {
        //given
        var expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
        var expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        //when
        Mockito.when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
                .thenReturn(Optional.of(expectedCreatedAuthor));

        //then
        Assertions.assertThrows(AuthorAlreadyExistsException.class, () -> authorService.create(expectedAuthorToCreateDTO));


    }
}
