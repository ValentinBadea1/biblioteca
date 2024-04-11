package com.example.biblioteca.cartiFizice.domain;

import com.example.biblioteca.cartiFizice.api.dto.CarteFizicaDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartiFiziceRepository extends CrudRepository<CarteFizica,Integer> {

    @Query("""
        Select cf.* , a.nume as autor, e.nume as editura, cc.nume as categorie
        From Carti_Fizice cf
        join Autor a on cf.autor_id = a.id
        join Editura e on cf.editura_id = e.id
        join Categorii_Carti cc on cf.categorie_carti_id = cc.id
        """)
    List<CarteFizicaDto> getAllCarti();

    @Query("""
        Select cf.* , a.nume as autor, e.nume as editura, cc.nume as categorie
        From Carti_Fizice cf
        join Autor a on cf.autor_id = a.id
        join Editura e on cf.editura_id = e.id
        join Categorii_Carti cc on cf.categorie_carti_id = cc.id
        where cf.id = :id
        """)
    Optional<CarteFizicaDto> getCarte(Integer id);

    @Query("""
        Select cf.* , a.nume as autor, e.nume as editura, cc.nume as categorie
        From Carti_Fizice cf
        join Autor a on cf.autor_id = a.id
        join Editura e on cf.editura_id = e.id
        join Categorii_Carti cc on cf.categorie_carti_id = cc.id
        where lower(cf.titlu) like CONCAT('%', LOWER(:value), '%')
        or lower(a.nume) like CONCAT('%', LOWER(:value), '%')
    
    """)
    List<CarteFizicaDto> getAllCartiByFilter(String value);
}
