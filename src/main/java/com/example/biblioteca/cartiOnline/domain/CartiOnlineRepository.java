package com.example.biblioteca.cartiOnline.domain;

import com.example.biblioteca.cartiFizice.api.dto.CarteFizicaDto;
import com.example.biblioteca.cartiOnline.api.dto.CarteOnlineDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartiOnlineRepository extends CrudRepository<CarteOnline,Integer> {

    @Query("""
        Select co.* , a.nume as autor, e.nume as editura, cc.nume as categorie
        From Carti_Online co
        join Autor a on co.autor_id = a.id
        join Editura e on co.editura_id = e.id
        join Categorii_Carti cc on co.categorie_carti_id = cc.id
        """)
    List<CarteOnlineDto> getAllCarti();

    @Query("""
        Select co.* , a.nume as autor, e.nume as editura, cc.nume as categorie
        From Carti_Online co
        join Autor a on co.autor_id = a.id
        join Editura e on co.editura_id = e.id
        join Categorii_Carti cc on co.categorie_carti_id = cc.id
        where co.id = :id
        """)
    Optional<CarteOnlineDto> getCarte(Integer id);

    @Query("""
        Select co.* , a.nume as autor, e.nume as editura, cc.nume as categorie
        From Carti_Online co
        join Autor a on co.autor_id = a.id
        join Editura e on co.editura_id = e.id
        join Categorii_Carti cc on co.categorie_carti_id = cc.id
        where lower(co.titlu) like CONCAT('%', LOWER(:value), '%')
        or lower(a.nume) like CONCAT('%', LOWER(:value), '%')
    
    """)
    List<CarteOnlineDto> getAllCartiByFilter(String value);

}
