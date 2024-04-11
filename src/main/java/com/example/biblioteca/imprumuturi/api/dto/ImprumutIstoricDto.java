package com.example.biblioteca.imprumuturi.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ImprumutIstoricDto {
    String autor;
    String titlu;
    Integer id;
    LocalDateTime dataImprumut;
    LocalDateTime dataReturnare;
    Integer penalizari;
    Integer userid;
    String username;
}
