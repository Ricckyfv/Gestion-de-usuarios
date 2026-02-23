package com.GestionUsuariosByLucas.GestionUsuarios.DTOs.request;

import com.GestionUsuariosByLucas.GestionUsuarios.Models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
//@Builder
//@ToString
//@EqualsAndHashCode
public class UserRequestDTO {
//    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    public static UserRequestDTO fromEntity(User user) {
//        if (user == null) return null;
//        return UserRequestDTO.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .phoneNumber(user.getPhoneNumber())
//                .role(user.getRole())
//                // password intencionalmente omitido al mapear desde la entidad
//                .build();
//    }
//
//    public User toEntity() {
//        User user = new User();
//        user.setId(this.id);
//        user.setName(this.name);
//        user.setEmail(this.email);
//        user.setPhoneNumber(this.phoneNumber);
//        user.setRole(this.role);
//        user.setPassword(this.password); // incluido para creación/actualización desde request
//        return user;
//    }
}
