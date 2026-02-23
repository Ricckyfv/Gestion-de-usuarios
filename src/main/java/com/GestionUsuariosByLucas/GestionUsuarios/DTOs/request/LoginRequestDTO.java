package com.GestionUsuariosByLucas.GestionUsuarios.DTOs.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;
}
