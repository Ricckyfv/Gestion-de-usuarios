package com.GestionUsuariosByLucas.GestionUsuarios.Controllers;


import com.GestionUsuariosByLucas.GestionUsuarios.DTOs.request.UserRequestDTO;
import com.GestionUsuariosByLucas.GestionUsuarios.DTOs.response.UserResponseDTO;
import com.GestionUsuariosByLucas.GestionUsuarios.Models.User;
import com.GestionUsuariosByLucas.GestionUsuarios.Repositories.UserRepository;
import com.GestionUsuariosByLucas.GestionUsuarios.Services.UserService;
import com.GestionUsuariosByLucas.GestionUsuarios.Utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

//    @PostMapping
//    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user) {
//
//        UserResponseDTO createdUser = userService.createUser(user);
//        return ResponseEntity.ok(createdUser);
//    }

    // Método auxiliar de conversión (En el futuro podrías usar ModelMapper)
    private UserResponseDTO convertToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<UserResponseDTO> users = userService.getUsers().stream()
                .map(this::convertToResponseDTO)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(convertToResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile(@RequestHeader("Authorization") String token) {

        String jwt = token.replace("Bearer ", "");
        String userId = jwtUtil.getValue(jwt);

        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
//        Optional<User> user = userRepository.findById(Integer.parseInt(userId));
//        return user.map(u -> ResponseEntity.ok(convertToResponseDTO(u)))
//                .orElse(ResponseEntity.notFound().build());
        return userRepository.findById(Integer.parseInt(userId))
                .map(user -> ResponseEntity.ok(convertToResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateAnyUser(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token,
            @RequestBody UserRequestDTO userDetails) {

        // El JWTUtil debería decirnos quién está haciendo la petición
        String adminId = jwtUtil.getValue(token.replace("Bearer ", ""));
        User requester = userRepository.findById(Integer.parseInt(adminId)).orElse(null);

        // SEGURIDAD: Solo permitimos si es ADMIN o si el ID que quiere editar es el SUYO
        if (requester != null && (requester.getRole().equals("ADMIN") || requester.getId().equals(id))) {
            return userRepository.findById(id).map(user -> {
                user.setName(userDetails.getName());
                user.setEmail(userDetails.getEmail());
                user.setPhoneNumber(userDetails.getPhoneNumber());
                userRepository.save(user);
                return ResponseEntity.ok(convertToResponseDTO(user));
            }).orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.status(403).build(); // Prohibido si intenta editar a otro sin ser admin
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }


}
