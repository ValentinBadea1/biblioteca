package com.example.biblioteca.cartiFizice.api;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CarteFizicaCommand {
    LocalDate dataAparitie;
    String editie;
    String format;
    int nrDisponibile;
    int nrPagini;
    String isbn;
    String tipCoperta;
    String titlu;
    String traducator;
    int autor;
    int categorie;
    int editura;
}
