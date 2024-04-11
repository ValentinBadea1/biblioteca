package com.example.biblioteca.imprumuturi.domain;

import com.example.biblioteca.cartiFizice.domain.CarteFizica;
import com.example.biblioteca.edituri.domain.Editura;
import com.example.biblioteca.scheduled.ScheduledTasks;
import com.example.biblioteca.utilizatori.domain.Utilizator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Table(name = "Imprumuturi")
@Getter
@Setter
public class Imprumut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer carteFizicaId;

    private LocalDateTime dataImprumut;
    private LocalDateTime dataRestituire;
    private LocalDateTime dataReturnare;
    private Integer penalizari;

    public void calculeazaPenalizari() {
        LocalDate now = LocalDate.now();
        LocalDate dataTeoreticaRestituire = this.dataRestituire.toLocalDate();
        Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
        int daysDifference = (int) ChronoUnit.DAYS.between(dataTeoreticaRestituire, now);

        int result = now.compareTo(dataTeoreticaRestituire);
        if (result > 0) {
            int penalizariVal = daysDifference * 50;
            this.penalizari = penalizariVal;
            log.info(String.valueOf(penalizariVal));
        }

    }


}
