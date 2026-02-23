package com.GestionUsuariosByLucas.GestionUsuarios.Services;

import com.GestionUsuariosByLucas.GestionUsuarios.DTOs.request.UserRequestDTO;
import com.GestionUsuariosByLucas.GestionUsuarios.DTOs.response.UserResponseDTO;
import com.GestionUsuariosByLucas.GestionUsuarios.Models.User;
import com.GestionUsuariosByLucas.GestionUsuarios.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }


//    public UserResponseDTO createUser(UserRequestDTO user) {
//        User newUser = user.toEntity();
//        // Aquí podrías agregar lógica adicional, como validar el email o asignar un rol por defecto
//        User save = userRepository.save(newUser);
//
//        return UserResponseDTO.fromEntity(save);
//    }
}
