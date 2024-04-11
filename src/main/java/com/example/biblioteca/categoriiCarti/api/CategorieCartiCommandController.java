package com.example.biblioteca.categoriiCarti.api;


import com.example.biblioteca.autori.api.dto.AutorDto;
import com.example.biblioteca.autori.domain.Autor;
import com.example.biblioteca.categoriiCarti.api.dto.CategorieDto;
import com.example.biblioteca.categoriiCarti.domain.CategorieCarti;
import com.example.biblioteca.categoriiCarti.domain.CategorieCartiRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/categorie-carti")
@CrossOrigin
@RequiredArgsConstructor
public class CategorieCartiCommandController {

    final CategorieCartiRepository categorieCartiRepository;

    @SneakyThrows
    @GetMapping(path = "/get-all")
    public @ResponseBody List<CategorieCarti> get(
    ) {
        List<CategorieCarti> lista = (List<CategorieCarti>) categorieCartiRepository.findAll();

        return lista;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addAutor(@RequestBody CategorieDto command){
        CategorieCarti categorieCarti = new CategorieCarti();
        categorieCarti.setNume(command.getNume());
        categorieCartiRepository.save(categorieCarti);

        return new ResponseEntity<>("Categorie introdusa cu succes!", HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(path = "/{id}")
    public @ResponseBody CategorieCarti get(
            @PathVariable Integer id
    ) {
        CategorieCarti categorieCarti = categorieCartiRepository.findById(id).get();
        return categorieCarti;
    }

    @SneakyThrows
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String>  deleteById(
            @PathVariable Integer id
    ) {
        CategorieCarti categorieCarti = categorieCartiRepository.findById(id).get();
        categorieCartiRepository.delete(categorieCarti);

        return new ResponseEntity<>("Categorie stearsa!", HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/update/{id}")
    public ResponseEntity<String> getAll(
            @RequestBody CategorieDto command,
            @PathVariable Integer id
    ) {
        CategorieCarti categorieCarti = categorieCartiRepository.findById(id).get();
        categorieCarti.setNume(command.getNume());
        categorieCartiRepository.save(categorieCarti);

        return new ResponseEntity<>("Categorie updated!", HttpStatus.OK);
    }
}
