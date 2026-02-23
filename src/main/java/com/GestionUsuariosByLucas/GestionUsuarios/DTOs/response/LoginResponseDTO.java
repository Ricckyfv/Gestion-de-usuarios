package com.GestionUsuariosByLucas.GestionUsuarios.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String role;
    private String email;
}
