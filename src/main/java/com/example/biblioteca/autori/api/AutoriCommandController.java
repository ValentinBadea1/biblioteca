package com.example.biblioteca.autori.api;



import com.example.biblioteca.autori.api.dto.AutorDto;
import com.example.biblioteca.autori.domain.Autor;
import com.example.biblioteca.autori.domain.AutorRepository;
import com.example.biblioteca.utilizatori.domain.Utilizator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/autori-command")
@CrossOrigin
public class AutoriCommandController {
    final AutorRepository autorRepository;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addAutor(@RequestBody AutorDto command){
        Autor autor = new Autor();
        autor.setNume(command.getNume());
        autorRepository.save(autor);

        return new ResponseEntity<>("Autor introdus cu succes!", HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(path = "/{id}")
    public @ResponseBody Autor get(
            @PathVariable Integer id
    ) {
        Autor autor = autorRepository.findById(id).get();
        return autor;
    }

    @SneakyThrows
    @GetMapping(path = "/get-all")
    public @ResponseBody List<Autor> getAll(
    ) {
        List<Autor> autori = (List<Autor>) autorRepository.findAll();
        return autori;
    }

    @SneakyThrows
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String>  deleteById(
            @PathVariable Integer id
    ) {
        Autor autor = autorRepository.findById(id).get();
        autorRepository.delete(autor);

        return new ResponseEntity<>("Autor sters!", HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/update/{id}")
    public ResponseEntity<String> getAll(
            @RequestBody AutorDto command,
            @PathVariable Integer id
    ) {
        Autor autor = autorRepository.findById(id).get();
        autor.setNume(command.getNume());
        autorRepository.save(autor);

        return new ResponseEntity<>("Autor update!", HttpStatus.OK);
    }

}
