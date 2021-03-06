package com.hugovallada.bookstoremanager.publishers.builder;

import com.hugovallada.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Vallada Editora";

    @Builder.Default
    private String code = "VAL1234";

    @Builder.Default
    private LocalDate foundationDate = LocalDate.of(2020, 6, 1);


    public PublisherDTO buildPublisherDTO() {
        return new PublisherDTO(id, name, code, foundationDate);
    }
}
