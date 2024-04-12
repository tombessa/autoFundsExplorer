package com.github.tombessa.autofundsexplorer.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 *
 * @author antonyonne.bessa
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UsuarioDTO{
    String username;
    String emailID;
    String lastname;
    String firstname;
}
