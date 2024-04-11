package com.example.biblioteca.imprumuturi.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ImprumutAlocatAdminDto {
    Integer iduser;
    Integer idcarte;
    LocalDate dataAparitie;
}
