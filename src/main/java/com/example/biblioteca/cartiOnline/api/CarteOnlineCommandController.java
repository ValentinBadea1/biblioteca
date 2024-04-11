package com.example.biblioteca.cartiOnline.api;

import com.example.biblioteca.autori.domain.Autor;
import com.example.biblioteca.autori.domain.AutorRepository;
import com.example.biblioteca.cartiFizice.api.CarteFizicaCommand;
import com.example.biblioteca.cartiFizice.api.dto.CarteFizicaDto;
import com.example.biblioteca.cartiFizice.domain.CarteFizica;
import com.example.biblioteca.cartiFizice.domain.CartiFiziceRepository;
import com.example.biblioteca.cartiOnline.api.dto.CarteOnlineCommand;
import com.example.biblioteca.cartiOnline.api.dto.CarteOnlineDto;
import com.example.biblioteca.cartiOnline.domain.CarteOnline;
import com.example.biblioteca.cartiOnline.domain.CartiOnlineRepository;
import com.example.biblioteca.categoriiCarti.domain.CategorieCarti;
import com.example.biblioteca.categoriiCarti.domain.CategorieCartiRepository;
import com.example.biblioteca.edituri.domain.Editura;
import com.example.biblioteca.edituri.domain.EdituraRepository;
import com.example.biblioteca.imprumuturi.domain.ImprumuturiRepository;
import com.example.biblioteca.utilizatori.domain.Utilizator;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping(path="/carti-online")
@CrossOrigin
@RequiredArgsConstructor
public class CarteOnlineCommandController {
    final CartiOnlineRepository cartiOnlineRepository;
    final AutorRepository autorRepository;
    final CategorieCartiRepository categorieCartiRepository;
    final EdituraRepository edituraRepository;

    private MinioClient minioClient;
    private String bucket = "library";

    @SneakyThrows
    @PostMapping(path = "/add")
    public ResponseEntity<String> create(
            @RequestBody CarteOnlineCommand command
    ) {
        CarteOnline carteOnline = new CarteOnline();
        carteOnline.setTitlu(command.getTitlu());
        carteOnline.setEditie(command.getEditie());
        carteOnline.setTraducator(command.getTraducator());
        carteOnline.setNrPagini(command.getNrPagini());
        carteOnline.setDataAparitiei(command.getDataAparitie());

        /**Fac un check sa confirm existenta celorlalte entitati**/
        Autor autor = autorRepository.findById(command.getAutor()).get();
        carteOnline.setAutorId(autor.getId());
        CategorieCarti categorieCarti = categorieCartiRepository.findById(command.getCategorie()).get();
        carteOnline.setCategorieCartiId(categorieCarti.getId());
        Editura editura = edituraRepository.findById(command.getEditura()).get();
        carteOnline.setEdituraId(editura.getId());

        cartiOnlineRepository.save(carteOnline);

        return new ResponseEntity<>(carteOnline.getId().toString(), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String>  deleteById(
            @PathVariable Integer id
    ) {

        CarteOnline carteOnline = cartiOnlineRepository.findById(id).get();
        cartiOnlineRepository.delete(carteOnline);

        return new ResponseEntity<>("Carte online stearsa!", HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable Integer id,
            @RequestBody CarteOnlineCommand command
    ) {
        CarteOnline carteOnline = cartiOnlineRepository.findById(id).get();
        carteOnline.setTitlu(command.getTitlu());
        carteOnline.setEditie(command.getEditie());
        carteOnline.setTraducator(command.getTraducator());
        carteOnline.setNrPagini(command.getNrPagini());
        carteOnline.setDataAparitiei(command.getDataAparitie());

        Autor autor = autorRepository.findById(command.getAutor()).get();
        carteOnline.setAutorId(autor.getId());
        CategorieCarti categorieCarti = categorieCartiRepository.findById(command.getCategorie()).get();
        carteOnline.setCategorieCartiId(categorieCarti.getId());
        Editura editura = edituraRepository.findById(command.getEditura()).get();
        carteOnline.setEdituraId(editura.getId());

        cartiOnlineRepository.save(carteOnline);

        return new ResponseEntity<>(carteOnline.getId().toString(), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(path = "/search/{value}")
    public @ResponseBody List<CarteOnlineDto> get(
            @PathVariable String value
    ) {
        List<CarteOnlineDto> carti = cartiOnlineRepository.getAllCartiByFilter(value);

        return carti;
    }

    @SneakyThrows
    @GetMapping(path = "/get-all")
    public @ResponseBody List<CarteOnlineDto> get(
    ) {
        List<CarteOnlineDto> lista = (List<CarteOnlineDto>) cartiOnlineRepository.getAllCarti();

        return lista;
    }

    @SneakyThrows
    @GetMapping(path = "/{id}")
    public @ResponseBody CarteOnlineDto get(
            @PathVariable Integer id
    ) {
        CarteOnlineDto carteOnline = cartiOnlineRepository.getCarte(id).get();

        return carteOnline;
    }

    @SneakyThrows
    @PostMapping(path = "/upload/{id}")
    public ResponseEntity<String> upload(
            @PathVariable Integer id,
            @RequestBody MultipartFile file
    ) {

        String[] parts = file.getOriginalFilename().split("\\.");

        String type = parts[parts.length - 1];

        try (BufferedInputStream in = new BufferedInputStream(file.getInputStream());
             FileOutputStream fileOutputStream = new FileOutputStream("test." + type)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }

        CarteOnline carteOnline = cartiOnlineRepository.findById(id).get();

        String objectname = id + "_" + carteOnline.getTitlu() + "_content." + type;
        String filename = file.getName();

        UploadObjectArgs uArgs = UploadObjectArgs.builder()
                .filename("test."+type)
                .bucket(bucket)
                .object(objectname)
                .contentType(file.getContentType())
                .build();

        minioClient = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("admin", "admin123")
                .build();
        minioClient.uploadObject(uArgs);


        carteOnline.setPath(objectname);
        cartiOnlineRepository.save(carteOnline);

        return new ResponseEntity<>("Carte adaugata cu succes!",
                HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(path = "/download/{id}")
    public ResponseEntity<byte[]> download(
            @PathVariable Integer id
    ) {
        CarteOnline carteOnline = cartiOnlineRepository.findById(id).get();

        String objectname = carteOnline.getPath();

        DownloadObjectArgs uArgs = DownloadObjectArgs.builder()
                .filename(objectname)
                .bucket(bucket)
                .object(objectname)
                .build();

        minioClient = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("admin", "admin123")
                .build();

        minioClient.downloadObject(uArgs);

        try {
            // Convert InputStream to byte array
            byte[] fileContent = Files.readAllBytes(Paths.get(objectname));

            Files.delete(Path.of(objectname));
            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(objectname).build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
