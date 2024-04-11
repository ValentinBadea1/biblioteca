package com.example.biblioteca.edituri.api;

import com.example.biblioteca.categoriiCarti.api.dto.CategorieDto;
import com.example.biblioteca.categoriiCarti.domain.CategorieCarti;
import com.example.biblioteca.edituri.api.dto.EdituraDto;
import com.example.biblioteca.edituri.domain.Editura;
import com.example.biblioteca.edituri.domain.EdituraRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/editura")
@CrossOrigin
@RequiredArgsConstructor
public class EdituraCommandController {

    final EdituraRepository edituraRepository;

    @SneakyThrows
    @GetMapping(path = "/get-all")
    public @ResponseBody List<Editura> get(
    ) {
        List<Editura> lista = (List<Editura>) edituraRepository.findAll();

        return lista;
    }


    @PostMapping(path = "/add")
    public ResponseEntity<String> addAutor(@RequestBody EdituraDto command){
        Editura editura = new Editura();
        editura.setNume(command.getNume());
        edituraRepository.save(editura);

        return new ResponseEntity<>("Editura introdusa cu succes!", HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(path = "/{id}")
    public @ResponseBody Editura get(
            @PathVariable Integer id
    ) {
        Editura editura = edituraRepository.findById(id).get();
        return editura;
    }

    @SneakyThrows
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String>  deleteById(
            @PathVariable Integer id
    ) {
        Editura editura = edituraRepository.findById(id).get();
        edituraRepository.delete(editura);

        return new ResponseEntity<>("Editura stearsa!", HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/update/{id}")
    public ResponseEntity<String> getAll(
            @RequestBody EdituraDto command,
            @PathVariable Integer id
    ) {
        Editura editura = edituraRepository.findById(id).get();
        editura.setNume(command.getNume());
        edituraRepository.save(editura);

        return new ResponseEntity<>("Editura updated!", HttpStatus.OK);
    }
}
