package com.example.biblioteca.edituri.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdituraRepository extends CrudRepository<Editura,Integer> {
}
