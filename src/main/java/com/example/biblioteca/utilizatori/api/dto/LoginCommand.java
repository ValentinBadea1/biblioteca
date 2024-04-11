package com.example.biblioteca.utilizatori.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LoginCommand {
    String username;
    String password;
    String password2;
    String phone;
    String email;
    Boolean admin;
    //MultipartFile document;
}
