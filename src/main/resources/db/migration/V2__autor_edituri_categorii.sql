CREATE TABLE biblioteca.Autor (
                       id int AUTO_INCREMENT  PRIMARY KEY,
                       nume VARCHAR(255) NOT NULL
);

CREATE TABLE biblioteca.Categorii_Carti (
                                 id int AUTO_INCREMENT  PRIMARY KEY,
                                 nume VARCHAR(255) NOT NULL
);

CREATE TABLE biblioteca.Editura (
                         id int AUTO_INCREMENT  PRIMARY KEY,
                         nume VARCHAR(255) NOT NULL
);