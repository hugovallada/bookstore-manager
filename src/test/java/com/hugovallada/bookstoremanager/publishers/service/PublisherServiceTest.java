package com.hugovallada.bookstoremanager.publishers.service;

import com.hugovallada.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.hugovallada.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.hugovallada.bookstoremanager.publishers.mapper.PublisherMapper;
import com.hugovallada.bookstoremanager.publishers.repository.PublisherRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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
public class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    }

    @Test
    void whenNewPublisherIsInformedThenItShouldBeCreated() {
        var expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherToCreate = publisherMapper.toModel(expectedPublisherToCreateDTO);

        Mockito.when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.empty());
        Mockito.when(publisherRepository.save(expectedPublisherToCreate))
                .thenReturn(expectedPublisherToCreate);

        var createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);
        MatcherAssert.assertThat(createdPublisherDTO, Matchers.is(Matchers.equalTo(expectedPublisherToCreateDTO)));
    }


    @Test
    void whenExistingPublisherIsInformedThenAnExceptionShouldBeThrown() {
        var expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherDuplicated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        Mockito.when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.of(expectedPublisherDuplicated));

        Assertions.assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.create(expectedPublisherToCreateDTO));
    }
}
