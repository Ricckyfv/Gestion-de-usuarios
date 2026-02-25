package com.GestionUsuariosByLucas.GestionUsuarios.Utils;

import com.GestionUsuariosByLucas.GestionUsuarios.Models.User;
import com.GestionUsuariosByLucas.GestionUsuarios.Repositories.UserRepository;
import com.GestionUsuariosByLucas.GestionUsuarios.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeedConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail("admin@admin.com").isEmpty()) {

            User admin = new User();
            admin.setName("Administrador Inicial");
            admin.setEmail("admin@admin.com");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            admin.setPhoneNumber("000000000");

            authService.register(admin);
            System.out.println("✅ Usuario ADMIN creado automáticamente: admin@admin.com / admin123");
        }else {
            System.out.println("ℹ️ El usuario administrador ya existe en la base de datos.");
        }
    }
}
