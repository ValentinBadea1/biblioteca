package com.example.biblioteca.cartiFizice.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CarteFizicaDto {

        Integer id;
        LocalDate dataAparitiei;
        String editie;
        String format;
        int nrDisponibile;
        int nrPagini;
        String tipCoperta;
        String isbn;
        String titlu;
        String traducator;
        String  autor;
        String categorie;
        String editura;

}
