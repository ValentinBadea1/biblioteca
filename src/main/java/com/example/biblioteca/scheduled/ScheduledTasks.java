package com.example.biblioteca.scheduled;

import com.example.biblioteca.cartiFizice.domain.CarteFizica;
import com.example.biblioteca.cartiFizice.domain.CartiFiziceRepository;
import com.example.biblioteca.imprumuturi.domain.ImprumuturiRepository;
import com.example.biblioteca.utilizatori.domain.Utilizator;
import com.example.biblioteca.utilizatori.domain.UtilizatorRepository;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    ImprumuturiRepository imprumuturiRepository;

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private UtilizatorRepository utilizatorRepository;
    @Autowired
    private CartiFiziceRepository cartiFiziceRepository;

    //    @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 30 22 * * ?")
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        imprumuturiRepository.findAll()
                .forEach(
                        imprumut -> {
                            if (imprumut.getDataReturnare() == null) {
                                imprumut.calculeazaPenalizari();
                                imprumuturiRepository.save(imprumut);

                                LocalDate today = LocalDate.now();
                                LocalDate dataRestituire = imprumut.getDataRestituire().toLocalDate();
                                log.info("id {}", imprumut.getId());
                                log.info("today {}, data restituire {}", today, dataRestituire);
                                Utilizator utilizator = utilizatorRepository.findById(imprumut.getUserId()).get();
                                CarteFizica carteFizica = cartiFiziceRepository.findById(imprumut.getCarteFizicaId()).get();

                                if (today.compareTo(dataRestituire.minusDays(1)) == 0) {

                                    emailSenderService.sendEmail(utilizator.getEmail(),
                                            "Returnare carte",
                                            "Va aducem la cunoștință ca maine trebuie sa returnați cartea: " + carteFizica.getTitlu());

                                }
                            }

                        }
                );

    }
}