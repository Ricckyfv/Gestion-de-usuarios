package com.GestionUsuariosByLucas.GestionUsuarios.Controllers;

import com.GestionUsuariosByLucas.GestionUsuarios.DTOs.request.LoginRequestDTO;
import com.GestionUsuariosByLucas.GestionUsuarios.DTOs.request.UserRequestDTO;
import com.GestionUsuariosByLucas.GestionUsuarios.DTOs.response.LoginResponseDTO;
import com.GestionUsuariosByLucas.GestionUsuarios.Models.User;
import com.GestionUsuariosByLucas.GestionUsuarios.Services.AuthService;
import com.GestionUsuariosByLucas.GestionUsuarios.Utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;
//    public AuthController(JwtUtil jwtUtil, AuthService authService) {
//        this.jwtUtil = jwtUtil;
//        this.authService = authService;
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        User user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            String token = jwtUtil.create(user.getId().toString(), user.getId().toString(), user.getRole());

            LoginResponseDTO response = new LoginResponseDTO(token, user.getRole(), user.getEmail());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO userDto) {
        try {
            // Convertimos DTO a Entidad antes de pasar al servicio
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setPassword(userDto.getPassword()); // El servicio se encargará del hash
            user.setRole("USER"); // Rol por defecto

            authService.register(user);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error en el registro: " + e.getMessage());
        }
    }
}
