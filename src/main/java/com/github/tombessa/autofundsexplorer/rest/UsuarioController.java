package com.github.tombessa.autofundsexplorer.rest;

import com.github.tombessa.autofundsexplorer.model.dto.UsuarioDTO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author antonyonne.bessa
 */

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);




    @ApiOperation("Retorna os dados do usuário logado")
    @GetMapping("/me")
    public UsuarioDTO me(@AuthenticationPrincipal User user)  {
        return UsuarioDTO.builder()
                .username(user.getUsername())
                .build();
    }
}
