package com.GestionUsuariosByLucas.GestionUsuarios.Services;

import com.GestionUsuariosByLucas.GestionUsuarios.Models.User;
import com.GestionUsuariosByLucas.GestionUsuarios.Repositories.UserRepository;
import com.GestionUsuariosByLucas.GestionUsuarios.Utils.Argon2Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private Argon2Hasher passwordHasher;

    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        String normalizedEmail = email.trim().toLowerCase();

        return userRepository.findByEmail(normalizedEmail)
                .filter(user -> passwordHasher.check(user.getPassword(), password))
                .orElse(null);
    }

    public void register(User user) {
        String normalizedEmail = user.getEmail().trim().toLowerCase();
        user.setEmail(normalizedEmail);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String passwordHasheada = passwordHasher.encode(user.getPassword());
        user.setPassword(passwordHasheada);

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        userRepository.save(user);
    }

    //PARA LA MIGRACIÓN DE USUARIOS ANTIGUOS CON CONTRASEÑA EN TEXTO PLANO
//    public User login(String email, String password) {
//        // 1. Normalizamos el email igual que en el registro
//        String normalizedEmail = email.trim().toLowerCase();
//
//        // 2. Buscamos al usuario
//        Optional<User> userOpt = userRepository.findByEmail(normalizedEmail);
//
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            String passwordEnBD = user.getPassword();
//
//            // 3. Lógica de verificación y migración
//            if (passwordEnBD.startsWith("$argon2")) {
//                // Caso: Usuario ya tiene Argon2
//                if (passwordHasher.check(passwordEnBD, password)) {
//                    return user;
//                }
//            } else {
//                // Caso: Usuario antiguo (Texto plano)
//                if (passwordEnBD.equals(password)) {
//                    // Si la clave es correcta, lo migramos a Argon2 de una vez
//                    String nuevoHash = passwordHasher.encode(password);
//                    user.setPassword(nuevoHash);
//                    userRepository.save(user);
//                    return user;
//                }
//            }
//        }
//
//        return null;
//    }
}
