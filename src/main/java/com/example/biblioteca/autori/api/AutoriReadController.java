package com.example.biblioteca.autori.api;

import com.example.biblioteca.autori.domain.Autor;
import com.example.biblioteca.autori.domain.AutorRepository;
import com.example.biblioteca.cereriConturi.domain.CereriDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/autori-read")
@CrossOrigin
@RequiredArgsConstructor
public class AutoriReadController {

    final AutorRepository autorRepository;

    @SneakyThrows
    @GetMapping(path = "/get-all")
    public @ResponseBody List<Autor> get(
    ) {
        List<Autor> lista = (List<Autor>) autorRepository.findAll();

        return lista;
    }
}
