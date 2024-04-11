package com.example.biblioteca.imprumuturi.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImprumutDto {
    String autor;
    String titlu;
    Integer id;
    LocalDateTime dataRestituire;
    Integer penalizari;

}
