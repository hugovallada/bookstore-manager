package com.hugovallada.bookstoremanager.author.service;

import com.hugovallada.bookstoremanager.author.mapper.AuthorMapper;
import com.hugovallada.bookstoremanager.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // transforma em um serviço gerenciado pelo spring - implementa a lógica de negócio
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired // Spring cria uma instancia e injeta automaticamente
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }



}


