package com.example.biblioteca.cartiOnline.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CarteOnlineDto {
    private Integer id;
    private String editura;
    private String autor;
    private String categorie;
    private String titlu;
    private String editie;
    private String traducator;
    private String path;
    private int nrPagini;
    private LocalDate dataAparitiei;
}
