package com.hugovallada.bookstoremanager.publishers.service;

import com.hugovallada.bookstoremanager.publishers.mapper.PublisherMapper;
import com.hugovallada.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final static PublisherMapper publishMapper = PublisherMapper.INSTANCE;


    private final PublisherRepository repository;
    
    @Autowired
    public PublisherService(PublisherRepository repository) {
        this.repository = repository;
    }
}
