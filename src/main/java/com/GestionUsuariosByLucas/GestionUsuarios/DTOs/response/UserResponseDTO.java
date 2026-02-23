
package com.GestionUsuariosByLucas.GestionUsuarios.DTOs.response;

import com.GestionUsuariosByLucas.GestionUsuarios.Models.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserResponseDTO {

    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;

//    public static UserResponseDTO fromEntity(User user) {
//        if (user == null) return null;
//        return UserResponseDTO.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .phoneNumber(user.getPhoneNumber())
//                .role(user.getRole())
//                .build();
//    }
}
