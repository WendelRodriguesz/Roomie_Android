package com.project.roomie.dto.response;

import com.project.roomie.core.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponseDTO {

    private String token;
    private Role role;
}