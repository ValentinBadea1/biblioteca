package com.example.biblioteca.cartiOnline.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CarteOnlineCommand {
    private Integer id;
    private Integer editura;
    private Integer autor;
    private Integer categorie;
    private String titlu;
    private String editie;
    private String traducator;
    private String path;
    private int nrPagini;
    private LocalDate dataAparitie;
}
