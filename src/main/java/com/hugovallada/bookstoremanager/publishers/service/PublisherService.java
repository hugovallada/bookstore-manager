package com.hugovallada.bookstoremanager.publishers.service;

import com.hugovallada.bookstoremanager.publishers.dto.PublisherDTO;
import com.hugovallada.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.hugovallada.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.hugovallada.bookstoremanager.publishers.mapper.PublisherMapper;
import com.hugovallada.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final static PublisherMapper publishMapper = PublisherMapper.INSTANCE;


    private final PublisherRepository repository;

    @Autowired
    public PublisherService(PublisherRepository repository) {
        this.repository = repository;
    }


    public PublisherDTO create(PublisherDTO publisherDTO) {
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());

        var publisher = publishMapper.toModel(publisherDTO);
        var createdPublisher = repository.save(publisher);

        return publishMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id) {
        return repository.findById(id)
                .map(publishMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public List<PublisherDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(publisher -> publishMapper.toDTO(publisher))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        verifyIfExists(id);
        repository.deleteById(id);

    }

    private void verifyIfExists(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    private void verifyIfExists(String name, String code) {
        var duplicatedPublisher = repository.findByNameOrCode(name, code);

        if (duplicatedPublisher.isPresent()) throw new PublisherAlreadyExistsException(name, code);
    }

}
