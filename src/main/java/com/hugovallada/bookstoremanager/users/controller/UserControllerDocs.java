package com.hugovallada.bookstoremanager.users.controller;

import com.hugovallada.bookstoremanager.users.dto.MessageDTO;
import com.hugovallada.bookstoremanager.users.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("System users management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Success user creation"),
                    @ApiResponse(code = 400, message = "Missing required fields, or an error on validation field rules")
            }
    )
    MessageDTO create(UserDTO userToCreateDTO);
}
