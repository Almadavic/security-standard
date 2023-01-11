package com.almadavic.securitystandard.controller;


import com.almadavic.securitystandard.config.exceptionConfig.standardError.commonStandardError.StandardError;
import com.almadavic.securitystandard.config.securityConfig.Token;
import com.almadavic.securitystandard.dto.request.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Tag(name = "authentication", description = "Authenticate in the system")
// Como será chamado o nome do "controller" no swagger e a descrição.
public interface AuthenticationController { // Controller de autenticação deve implementar essa interface.

    @Operation(summary = "Sign in the system.") // Summary - Documentação no Swagger
    @ApiResponses(value = {   // Informações relacionadas ao response!
            @ApiResponse(responseCode = "200", description = "User logged",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Token.class))}),
            @ApiResponse(responseCode = "400", description = "E-mail and / or password are / is wrong",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))})
    })
    ResponseEntity<Token> authenticate(@RequestBody @Valid LoginDTO loginData); // Método para se autenticar no sistema

}