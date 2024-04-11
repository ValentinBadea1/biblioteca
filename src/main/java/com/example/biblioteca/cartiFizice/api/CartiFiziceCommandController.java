package com.example.biblioteca.cartiFizice.api;

import com.example.biblioteca.autori.domain.Autor;
import com.example.biblioteca.autori.domain.AutorRepository;
import com.example.biblioteca.cartiFizice.api.dto.CarteFizicaDto;
import com.example.biblioteca.cartiFizice.domain.CarteFizica;
import com.example.biblioteca.cartiFizice.domain.CartiFiziceRepository;
import com.example.biblioteca.categoriiCarti.domain.CategorieCarti;
import com.example.biblioteca.categoriiCarti.domain.CategorieCartiRepository;
import com.example.biblioteca.cereriConturi.domain.CereriDto;
import com.example.biblioteca.edituri.domain.Editura;
import com.example.biblioteca.edituri.domain.EdituraRepository;
import com.example.biblioteca.imprumuturi.domain.ImprumuturiRepository;
import com.example.biblioteca.utilizatori.domain.Utilizator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/carti-fizice")
@CrossOrigin
@RequiredArgsConstructor
public class CartiFiziceCommandController {

    final CartiFiziceRepository cartiFiziceRepository;
    final AutorRepository autorRepository;
    final CategorieCartiRepository categorieCartiRepository;
    final EdituraRepository edituraRepository;
    final ImprumuturiRepository imprumuturiRepository;

    @SneakyThrows
    @PostMapping(path = "/add")
    public ResponseEntity<String> create(
            @RequestBody CarteFizicaCommand command
    ) {
        CarteFizica carteFizica = new CarteFizica();
        carteFizica.setDataAparitiei(command.getDataAparitie());
        carteFizica.setEditie(command.getEditie());
        carteFizica.setFormat(command.getFormat());
        carteFizica.setNrDisponibile(command.getNrDisponibile());
        carteFizica.setNrPagini(command.getNrPagini());
        carteFizica.setTipCoperta(command.getTipCoperta());
        carteFizica.setTitlu(command.getTitlu());
        carteFizica.setTraducator(command.getTraducator());
        carteFizica.setIsbn(command.getIsbn());

        Autor autor = autorRepository.findById(command.getAutor()).get();
        carteFizica.setAutorId(autor.getId());
        CategorieCarti categorieCarti = categorieCartiRepository.findById(command.getCategorie()).get();
        carteFizica.setCategorieCartiId(categorieCarti.getId());
        Editura editura = edituraRepository.findById(command.getEditura()).get();
        carteFizica.setEdituraId(editura.getId());

        cartiFiziceRepository.save(carteFizica);

        return new ResponseEntity<>(carteFizica.getId().toString(), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String>  deleteById(
            @PathVariable Integer id
    ) {
        imprumuturiRepository.findAllByCarteFizicaId(id)
                .forEach(
                        imprumut -> imprumuturiRepository.delete(imprumut)
                );

        CarteFizica carteFizica = cartiFiziceRepository.findById(id).get();
        cartiFiziceRepository.delete(carteFizica);

        return new ResponseEntity<>("Carte stearsa!", HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable Integer id,
            @RequestBody CarteFizicaCommand command
    ) {
        CarteFizica carteFizica = cartiFiziceRepository.findById(id).get();
        carteFizica.setDataAparitiei(command.getDataAparitie());
        carteFizica.setEditie(command.getEditie());
        carteFizica.setFormat(command.getFormat());
        carteFizica.setNrDisponibile(command.getNrDisponibile());
        carteFizica.setNrPagini(command.getNrPagini());
        carteFizica.setTipCoperta(command.getTipCoperta());
        carteFizica.setTitlu(command.getTitlu());
        carteFizica.setTraducator(command.getTraducator());
        carteFizica.setIsbn(command.getIsbn());

        Autor autor = autorRepository.findById(command.getAutor()).get();
        carteFizica.setAutorId(autor.getId());
        CategorieCarti categorieCarti = categorieCartiRepository.findById(command.getCategorie()).get();
        carteFizica.setCategorieCartiId(categorieCarti.getId());
        Editura editura = edituraRepository.findById(command.getEditura()).get();
        carteFizica.setEdituraId(editura.getId());

        cartiFiziceRepository.save(carteFizica);

        return new ResponseEntity<>(carteFizica.getId().toString(), HttpStatus.OK);
    }



    @SneakyThrows
    @GetMapping(path = "/get-all")
    public @ResponseBody List<CarteFizicaDto> get(
    ) {
        List<CarteFizicaDto> lista = (List<CarteFizicaDto>) cartiFiziceRepository.getAllCarti();

        return lista;
    }

    @SneakyThrows
    @GetMapping(path = "/{id}")
    public @ResponseBody CarteFizicaDto get(
            @PathVariable Integer id
    ) {
        CarteFizicaDto carteFizica = cartiFiziceRepository.getCarte(id).get();

        return carteFizica;
    }

    @SneakyThrows
    @GetMapping(path = "/search/{value}")
    public @ResponseBody List<CarteFizicaDto> get(
            @PathVariable String value
    ) {
        List<CarteFizicaDto> carti = cartiFiziceRepository.getAllCartiByFilter(value);

        return carti;
    }
}
