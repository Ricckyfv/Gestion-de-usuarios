package com.GestionUsuariosByLucas.GestionUsuarios.Utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Component;

@Component
public class Argon2Hasher {

    private static final int ITERATIONS = 1;
    private static final int MEMORY = 65536; // 64MB
    private static final int PARALLELISM = 1;

    public String encode(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        try {
            // El hash generado ya incluye la sal automáticamente
            return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }

    public boolean check(String hash, String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        try {
            return argon2.verify(hash, password.toCharArray());
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }
}
