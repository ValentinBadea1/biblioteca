package com.example.biblioteca.utilizatori.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    Integer id;
    String username;
    String password;
    Boolean admin;
}
