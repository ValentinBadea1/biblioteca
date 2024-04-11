package com.example.biblioteca.utilizatori.api;

import com.example.biblioteca.cereriConturi.domain.CereriConturi;
import com.example.biblioteca.cereriConturi.domain.CereriConturiRepository;
import com.example.biblioteca.cereriConturi.domain.CereriDto;
import com.example.biblioteca.utilizatori.api.dto.LoginCommand;
import com.example.biblioteca.utilizatori.api.dto.LoginDto;
import com.example.biblioteca.utilizatori.api.dto.LoginReceivedDto;
import com.example.biblioteca.utilizatori.api.dto.UserDto;
import com.example.biblioteca.utilizatori.domain.Utilizator;
import com.example.biblioteca.utilizatori.domain.UtilizatorRepository;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping(path = "/user-command")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserCommandController {

     final CereriConturiRepository cereriConturiRepository;
     final UtilizatorRepository utilizatorRepository;


    private MinioClient minioClient;
    private String bucket = "library";



    @SneakyThrows
    @GetMapping(path = "/download/{id}")
    public ResponseEntity<byte[]> download(
            @PathVariable Integer id
    ) {
        Utilizator user = utilizatorRepository.findById(id).get();

        String objectname = user.getPath_ci();

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

            // Return the file content in the response body

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (Exception e) {
            // Handle exceptions appropriately, log or return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        //return new ResponseEntity<>("Download successful!", HttpStatus.OK);

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

        Utilizator user = utilizatorRepository.findById(id).get();

        String objectname = id + "_" + user.getUsername() + "_buletin." + type;
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


        user.setPath_ci(objectname);
        utilizatorRepository.save(user);

        return new ResponseEntity<>("Utilizator adaugat cu succes!",
                HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(path = "/create")
    public ResponseEntity<String> create(
            @RequestBody LoginCommand command
    ) {

        if (!command.getPassword2().equals(command.getPassword())) {
            throw new BadRequestException("Parolele difera!");
        }
        if (utilizatorRepository.findByUsername(command.getUsername()).isPresent())
            throw new BadRequestException("Username existent!");

        Utilizator user = new Utilizator();
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword());
        user.setEmail(command.getEmail());
        user.setPhone(command.getPhone());
        user.setAdmin(command.getAdmin());
        user.setActiv(Boolean.FALSE);

        utilizatorRepository.save(user);

        CereriConturi cerere = new CereriConturi();
        cerere.setUserId(user.getId());
        cerere.setValidat(Boolean.FALSE);

        cereriConturiRepository.save(cerere);

        return new ResponseEntity<>(user.getId().toString(), HttpStatus.OK);
    }


    @SneakyThrows
    @PostMapping(path = "/login")
    public @ResponseBody LoginDto login(
            @RequestBody LoginReceivedDto command
            ) {
        LoginDto user = utilizatorRepository.getUserByUsernameAndPassword(command.getUsername(), command.getPassword())
                .orElseThrow(
                        () -> new BadRequestException("Utilizatorul nu exista sau nu este aprobat!")
                );
        return user;
    }

    @SneakyThrows
    @GetMapping(path = "/{id}")
    public @ResponseBody Utilizator get(
            @PathVariable Integer id
    ) {
        Utilizator user = utilizatorRepository.findById(id).get();

        return user;
    }

    @SneakyThrows
    @GetMapping(path = "/get-all")
    public @ResponseBody List<UserDto> getUsers(
    ) {
        List<UserDto> lista = utilizatorRepository.getAll();

        return lista;
    }

    @SneakyThrows
    @GetMapping(path = "/cereri-get-all")
    public @ResponseBody List<CereriDto> get(
    ) {
       List<CereriDto> lista = cereriConturiRepository.getNevalidati();

       return lista;
    }


}
