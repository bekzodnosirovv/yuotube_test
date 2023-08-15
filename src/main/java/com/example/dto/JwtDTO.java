package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtDTO {
    private Integer id;
    private String email;

    public JwtDTO(Integer id, String email) {
        this.id = id;
        this.email = email;
    }
}
