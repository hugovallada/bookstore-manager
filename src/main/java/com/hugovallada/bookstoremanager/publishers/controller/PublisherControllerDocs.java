package com.hugovallada.bookstoremanager.publishers.controller;

import com.hugovallada.bookstoremanager.publishers.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api("Publishers management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Publisher creation operation")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Success publisher creation"),
                    @ApiResponse(
                            code = 400,
                            message = "Missing required fields, wrong field range value or user already registered"
                    )
            }
    )
    PublisherDTO create(PublisherDTO publisherDTO);
}
