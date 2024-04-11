package com.example.biblioteca.cartiOnline.domain;

import com.example.biblioteca.autori.domain.Autor;
import com.example.biblioteca.categoriiCarti.domain.CategorieCarti;
import com.example.biblioteca.edituri.domain.Editura;
import com.example.biblioteca.utilizatori.domain.Utilizator;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "Carti_Online")
@Getter
@Setter
public class CarteOnline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer edituraId;

    private Integer autorId;

    private Integer categorieCartiId;

    private String titlu;
    private String editie;
    private String traducator;
    private String path;
    private int nrPagini;
    private LocalDate dataAparitiei;
}
