package com.hugovallada.bookstoremanager.controller;

import com.hugovallada.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.hugovallada.bookstoremanager.author.controller.AuthorController;
import com.hugovallada.bookstoremanager.author.service.AuthorService;
import com.hugovallada.bookstoremanager.utils.JsonConversionUtils;
import org.hamcrest.core.Is;
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
public class AuthorControllerTest {

    private static final String AUTHOR_API_URL_PATH = "/api/v1/authors";

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenStatusCreatedShouldBeReturned() throws Exception {
        var expectedCreatedAuthorDTO = authorDTOBuilder.buildAuthorDTO();

        Mockito.when(authorService.create(expectedCreatedAuthorDTO))
                .thenReturn(expectedCreatedAuthorDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedAuthorDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(expectedCreatedAuthorDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(expectedCreatedAuthorDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is(expectedCreatedAuthorDTO.getAge())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeInformed() throws Exception {
        var expectedCreatedAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        expectedCreatedAuthorDTO.setName(null);


        mockMvc.perform(MockMvcRequestBuilders.post(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedAuthorDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void whenGETwithValidaIdIsCalledThenStatusOKShouldBeReturned() throws Exception {
        var expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();

        Mockito.when(authorService.findById(expectedFoundAuthorDTO.getId()))
                .thenReturn(expectedFoundAuthorDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(AUTHOR_API_URL_PATH + "/" + expectedFoundAuthorDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(expectedFoundAuthorDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(expectedFoundAuthorDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is(expectedFoundAuthorDTO.getAge())));
    }

    @Test
    void whenGETListIsCalledThenStatusOKShouldBeReturned() throws Exception {
        var expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();

        Mockito.when(authorService.findAll())
                .thenReturn(Collections.singletonList(expectedFoundAuthorDTO));

        mockMvc.perform(MockMvcRequestBuilders.get(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(expectedFoundAuthorDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Is.is(expectedFoundAuthorDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", Is.is(expectedFoundAuthorDTO.getAge())));
    }
}
