CREATE TABLE biblioteca.Carti_Fizice (
                              id int AUTO_INCREMENT  PRIMARY KEY,
                              autor_id INT,
                              categorie_carti_id INT,
                              data_aparitiei DATE,
                              editie VARCHAR(255),
                              isbn VARCHAR(255),
                              editura_id INT,
                              format VARCHAR(50),
                              nr_disponibile INT,
                              nr_pagini INT,
                              tip_coperta VARCHAR(50),
                              titlu VARCHAR(255),
                              traducator VARCHAR(255)
);