package com.example.biblioteca.categoriiCarti.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieCartiRepository extends CrudRepository<CategorieCarti,Integer> {
}
