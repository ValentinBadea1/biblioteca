package com.example.biblioteca.cereriConturi.api;

import com.example.biblioteca.cartiFizice.domain.CarteFizica;
import com.example.biblioteca.cereriConturi.domain.CereriConturi;
import com.example.biblioteca.cereriConturi.domain.CereriConturiRepository;
import com.example.biblioteca.edituri.api.dto.EdituraDto;
import com.example.biblioteca.edituri.domain.Editura;
import com.example.biblioteca.utilizatori.domain.Utilizator;
import com.example.biblioteca.utilizatori.domain.UtilizatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/cereri")
@CrossOrigin
@RequiredArgsConstructor
public class CereriCommandController {
    final CereriConturiRepository cereriConturiRepository;
    final UtilizatorRepository utilizatorRepository;



    @PostMapping(path = "/accept/{id}")
    public ResponseEntity<String> addAutor(
            @PathVariable Integer id
    ){
        CereriConturi cerere = cereriConturiRepository.findByUserId(id).get();
        cerere.setValidat(Boolean.TRUE);
        cereriConturiRepository.save(cerere);

        Utilizator utilizator = utilizatorRepository.findById(id).get();
        utilizator.setActiv(Boolean.TRUE);
        utilizatorRepository.save(utilizator);

        return new ResponseEntity<>("Cerere Aprobata!", HttpStatus.OK);
    }
}
