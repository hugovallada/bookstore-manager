package com.hugovallada.bookstoremanager.author.service;

import com.hugovallada.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.hugovallada.bookstoremanager.author.dto.AuthorDTO;
import com.hugovallada.bookstoremanager.author.mapper.AuthorMapper;
import com.hugovallada.bookstoremanager.author.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
