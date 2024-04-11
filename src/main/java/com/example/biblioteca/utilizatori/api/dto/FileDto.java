package com.example.biblioteca.utilizatori.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileDto {
    MultipartFile document;
}
