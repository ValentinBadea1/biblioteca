package com.example.biblioteca.utilizatori.domain;

import com.example.biblioteca.utilizatori.api.dto.LoginDto;
import com.example.biblioteca.utilizatori.api.dto.UserDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilizatorRepository extends CrudRepository<Utilizator,Integer> {


    @Query("""
        Select u.id, u.username, u.password, u.admin
        from Utilizatori u
        where u.username= :username and u.password= :password
        and u.activ = 1
    """)
    Optional<LoginDto> getUserByUsernameAndPassword(String username,String password);
    Optional<Utilizator> findByUsername(String username);

    @Query("""
    select u.id, u.username
    from Utilizatori u
    where u.admin = 0 and u.activ=1
    """)
    List<UserDto> getAll();
}
