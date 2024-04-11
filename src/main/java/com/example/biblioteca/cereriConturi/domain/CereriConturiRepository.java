package com.example.biblioteca.cereriConturi.domain;

import com.google.common.annotations.Beta;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CereriConturiRepository extends CrudRepository<CereriConturi, Integer> {

    @Query("""
              SELECT u.id as u_id,u.username,u.email,cc.id as cc_id
                  from Utilizatori u
                  join Cereri_Conturi cc on u.id = cc.User_Id
                  where cc.validat = 0
""")
    List<CereriDto> getNevalidati();

    Optional<CereriConturi> findByUserId(Integer id);

}