package com.hugovallada.bookstoremanager.publishers.controller;

import com.hugovallada.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.hugovallada.bookstoremanager.publishers.service.PublisherService;
import com.hugovallada.bookstoremanager.utils.JsonConversionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

    private final static String PUBLISHER_API_URL_PATH = "/api/v1/publishers";

    private MockMvc mockMvc;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(publisherController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeInformed() throws Exception {

        var expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        Mockito.when(publisherService.create(expectedCreatedPublisherDTO))
                .thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(expectedCreatedPublisherDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed() throws Exception {

        var expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(MockMvcRequestBuilders.post(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGETWithValidIdIsCalledThenOKStatusShouldBeInformed() throws Exception {

        var expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        Mockito.when(publisherService.findById(expectedCreatedPublisherDTO.getId()))
                .thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(PUBLISHER_API_URL_PATH + "/" + expectedCreatedPublisherDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(expectedCreatedPublisherDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenGETListIsCalledThenOKStatusShouldBeInformed() throws Exception {

        var expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        Mockito.when(publisherService.findAll())
                .thenReturn(Collections.singletonList(expectedCreatedPublisherDTO));

        mockMvc.perform(MockMvcRequestBuilders.get(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(expectedCreatedPublisherDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].code", Matchers.is(expectedCreatedPublisherDTO.getCode())));
    }
}
