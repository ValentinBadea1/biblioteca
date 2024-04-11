package com.example.biblioteca.utilizatori.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;  //pentru eroarea cu par 0 not found

@Table(name = "Utilizatori")
@Setter
@Getter
public class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String path_ci;

    private boolean activ;

    private boolean admin;
}
