package com.example.biblioteca.cartiFizice.domain;

import com.example.biblioteca.autori.domain.Autor;
import com.example.biblioteca.categoriiCarti.domain.CategorieCarti;
import com.example.biblioteca.edituri.domain.Editura;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "Carti_Fizice")
@Getter
@Setter
public class CarteFizica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer autorId;
    Integer categorieCartiId;
    Integer edituraId;

    String isbn;
    LocalDate dataAparitiei;
    String editie;
    String format;
    Integer nrDisponibile;
    Integer nrPagini;
    String tipCoperta;
    String titlu;
    String traducator;

}
