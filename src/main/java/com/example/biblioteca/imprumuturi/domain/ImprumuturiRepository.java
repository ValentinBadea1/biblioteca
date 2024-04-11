package com.example.biblioteca.imprumuturi.domain;

import com.example.biblioteca.imprumuturi.api.dto.ImprumutAdminDto;
import com.example.biblioteca.imprumuturi.api.dto.ImprumutDto;
import com.example.biblioteca.imprumuturi.api.dto.ImprumutIstoricDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImprumuturiRepository extends CrudRepository<Imprumut,Integer> {

    List<Imprumut> findAllByCarteFizicaId(Integer carteId);

    @Query("""
    SELECT i.id, i.data_restituire, cf.titlu, a.nume as autor, i.penalizari
    FROM Imprumuturi i
    join Carti_Fizice cf on cf.id = i.carte_fizica_id
    join Autor a on a.id = cf.autor_id
    where i.user_id = :userId and i.Data_Returnare is null
    order by i.id
    """)
    List<ImprumutDto> findAllByUserId(Integer userId);

    @Query("""
    SELECT i.id, u.id as userid, u.username, i.data_restituire, cf.titlu, a.nume as autor, i.penalizari
    FROM Imprumuturi i
    join Utilizatori u on u.id = i.user_id
    join Carti_Fizice cf on cf.id = i.carte_fizica_id
    join Autor a on a.id = cf.autor_id
    where i.Data_Returnare is null
    order by i.id
    """)
    List<ImprumutAdminDto> findAllActiveForAdmin();

    @Query("""
    SELECT i.id, u.id as userid, u.username, i.data_restituire, cf.titlu, a.nume as autor, i.penalizari
    FROM Imprumuturi i
    join Utilizatori u on u.id = i.user_id
    join Carti_Fizice cf on cf.id = i.carte_fizica_id
    join Autor a on a.id = cf.autor_id
    where i.Data_Returnare is null and
    (lower(u.username) like CONCAT('%', LOWER(:value), '%')
    or lower(cf.titlu) like CONCAT('%', LOWER(:value), '%')
    or lower(a.nume) like CONCAT('%', LOWER(:value), '%'))
    order by i.id
    """)
    List<ImprumutAdminDto> getAllCartiByFilter(String value);

    @Query("""
    SELECT i.id, u.id as userid, u.username, i.data_imprumut, i.data_returnare, cf.titlu, a.nume as autor, i.penalizari
    FROM Imprumuturi i
    join Utilizatori u on u.id = i.user_id
    join Carti_Fizice cf on cf.id = i.carte_fizica_id
    join Autor a on a.id = cf.autor_id
    where i.Data_Returnare is not null
    order by i.id
    """)
    List<ImprumutIstoricDto> findAllInactiveForAdmin();

    @Query("""
    SELECT i.id, u.id as userid, u.username,i.data_imprumut, i.data_returnare, cf.titlu, a.nume as autor, i.penalizari
    FROM Imprumuturi i
    join Utilizatori u on u.id = i.user_id
    join Carti_Fizice cf on cf.id = i.carte_fizica_id
    join Autor a on a.id = cf.autor_id
    where i.Data_Returnare is not null and
    (lower(u.username) like CONCAT('%', LOWER(:value), '%')
    or lower(cf.titlu) like CONCAT('%', LOWER(:value), '%')
    or lower(a.nume) like CONCAT('%', LOWER(:value), '%'))
    order by i.id
    """)
    List<ImprumutIstoricDto> getHistory(String value);

}
