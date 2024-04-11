package com.example.biblioteca.imprumuturi.api;

import com.example.biblioteca.cartiFizice.domain.CarteFizica;
import com.example.biblioteca.cartiFizice.domain.CartiFiziceRepository;
import com.example.biblioteca.imprumuturi.api.dto.ImprumutAdminDto;
import com.example.biblioteca.imprumuturi.api.dto.ImprumutAlocatAdminDto;
import com.example.biblioteca.imprumuturi.api.dto.ImprumutDto;
import com.example.biblioteca.imprumuturi.api.dto.ImprumutIstoricDto;
import com.example.biblioteca.imprumuturi.domain.Imprumut;
import com.example.biblioteca.imprumuturi.domain.ImprumuturiRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(path = "/imprumut")
@CrossOrigin
@RequiredArgsConstructor
public class ImprumutCommandController {
    final CartiFiziceRepository cartiFiziceRepository;
    final ImprumuturiRepository imprumuturiRepository;

    @SneakyThrows
    @PostMapping(path = "/return/{imprumutId}")
    public ResponseEntity<String> marcheazaCaReturnata(
            @PathVariable Integer imprumutId
    ) {
        Imprumut imprumut = imprumuturiRepository.findById(imprumutId).get();
        imprumut.setDataReturnare(LocalDateTime.now());
        imprumuturiRepository.save(imprumut);

        return new ResponseEntity<>(imprumut.getId().toString(), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/{carteId}/{userId}")
    public ResponseEntity<String> create(
            @PathVariable Integer carteId,
            @PathVariable Integer userId
    ) {
        CarteFizica carteFizica = cartiFiziceRepository.findById(carteId).get();
        if (carteFizica.getNrDisponibile() == 0)
            throw new BadRequestException("Nu exista carti disponibile!");

        Imprumut imprumut = new Imprumut();
        imprumut.setDataImprumut(LocalDateTime.now());
        imprumut.setUserId(userId);
        imprumut.setCarteFizicaId(carteId);
        imprumut.setDataRestituire(LocalDateTime.now().plusDays(30));
        imprumut.setPenalizari(0);
        imprumuturiRepository.save(imprumut);

        carteFizica.setNrDisponibile(carteFizica.getNrDisponibile()-1);
        cartiFiziceRepository.save(carteFizica);

        return new ResponseEntity<>(imprumut.getId().toString(), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/aloca-admin")
    public ResponseEntity<String> alocaAdmin(
            @RequestBody ImprumutAlocatAdminDto command
            ) {
        CarteFizica carteFizica = cartiFiziceRepository.findById(command.getIdcarte()).get();
        if (carteFizica.getNrDisponibile() == 0)
            throw new BadRequestException("Nu exista carti disponibile!");

        Imprumut imprumut = new Imprumut();
        imprumut.setDataImprumut(command.getDataAparitie().atStartOfDay());
        imprumut.setUserId(command.getIduser());
        imprumut.setCarteFizicaId(command.getIdcarte());
        imprumut.setDataRestituire(command.getDataAparitie().plusDays(30).atStartOfDay());
        imprumut.setPenalizari(0);
        imprumuturiRepository.save(imprumut);

        carteFizica.setNrDisponibile(carteFizica.getNrDisponibile()-1);
        cartiFiziceRepository.save(carteFizica);

        return new ResponseEntity<>(imprumut.getId().toString(), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(path = "/get-all/{id}")
    public @ResponseBody List<ImprumutDto> get(
            @PathVariable Integer id
    ) {
        List<ImprumutDto> lista = (List<ImprumutDto>) imprumuturiRepository.findAllByUserId(id);

        return lista;
    }

    @SneakyThrows
    @GetMapping(path = "/get-all-admin")
    public @ResponseBody List<ImprumutAdminDto> get(
    ) {
        List<ImprumutAdminDto> lista = imprumuturiRepository.findAllActiveForAdmin();

        return lista;
    }

    @SneakyThrows
    @GetMapping(path = "/get-history-admin")
    public @ResponseBody List<ImprumutIstoricDto> getHistory(
    ) {
        List<ImprumutIstoricDto> lista = imprumuturiRepository.findAllInactiveForAdmin();

        return lista;
    }

    @SneakyThrows
    @GetMapping(path = "/search-history/{value}")
    public @ResponseBody List<ImprumutIstoricDto> getHistory(
            @PathVariable String value
    ) {
        List<ImprumutIstoricDto> carti = imprumuturiRepository.getHistory(value);

        return carti;
    }

    @SneakyThrows
    @GetMapping(path = "/search/{value}")
    public @ResponseBody List<ImprumutAdminDto> get(
            @PathVariable String value
    ) {
        List<ImprumutAdminDto> carti = imprumuturiRepository.getAllCartiByFilter(value);

        return carti;
    }
}
