package com.hugovallada.bookstoremanager.publishers.service;

import com.hugovallada.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.hugovallada.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.hugovallada.bookstoremanager.publishers.exception.PublisherNotFoundException;
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

import java.util.Collections;
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

    @Test
    void whenValidIdIsGivenThenAPublisherShouldBeReturned() {
        var expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);

        Mockito.when(publisherRepository.findById(expectedPublisherFoundDTO.getId()))
                .thenReturn(Optional.of(expectedPublisherFound));

        var foundPublisherDTO = publisherService.findById(expectedPublisherFoundDTO.getId());

        MatcherAssert.assertThat(foundPublisherDTO, Matchers.is(Matchers.equalTo(foundPublisherDTO)));

    }

    @Test
    void whenInvalidIdIsGivenThenANotFoundExceptionShouldBeThrown() {
        var expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();

        Mockito.when(publisherRepository.findById(expectedPublisherFoundDTO.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(PublisherNotFoundException.class, () -> publisherService.findById(expectedPublisherFoundDTO.getId()));
    }

    @Test
    void whenListPublisherIsCalledThenItShouldBeReturned() {
        var expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);

        Mockito.when(publisherRepository.findAll())
                .thenReturn(Collections.singletonList(expectedPublisherFound));

        var foundPublishersDTO = publisherService.findAll();

        MatcherAssert.assertThat(foundPublishersDTO.size(), Matchers.is(1));
        MatcherAssert.assertThat(foundPublishersDTO.get(0), Matchers.is(Matchers.equalTo(expectedPublisherFoundDTO)));
    }

    @Test
    void whenListPublisherIsCalledThenAnEmptyListShouldBeReturned() {

        Mockito.when(publisherRepository.findAll())
                .thenReturn(Collections.emptyList());

        var foundPublishersDTO = publisherService.findAll();

        MatcherAssert.assertThat(foundPublishersDTO.size(), Matchers.is(0));
    }

    @Test
    void whenValidPublishedIdIsGivenThenItShouldBeDeleted() {
        var expectedPublisherDeletedDTO = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherDeleted = publisherMapper.toModel(expectedPublisherDeletedDTO);

        Mockito.doNothing().when(publisherRepository).deleteById(expectedPublisherDeletedDTO.getId());
        Mockito.when(publisherRepository.findById(expectedPublisherDeleted.getId()))
                .thenReturn(Optional.of(expectedPublisherDeleted));

        publisherService.delete(expectedPublisherDeleted.getId());

        Mockito.verify(publisherRepository, Mockito.times(1)).deleteById(expectedPublisherDeleted.getId());

    }

    @Test
    void whenInvalidPublishedIdIsGivenThenNotFoundExceptionShouldBeThrown() {
        var expectedPublisherInvalidID = 2L;

        Mockito.when(publisherRepository.findById(expectedPublisherInvalidID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(PublisherNotFoundException.class, () -> publisherService.delete(expectedPublisherInvalidID));
    }
}
