CREATE TABLE biblioteca.Cereri_Conturi (
                                id int AUTO_INCREMENT PRIMARY KEY,
                                User_Id VARCHAR(255),
                                validat BOOLEAN
);

CREATE TABLE biblioteca.Imprumuturi (
                             id int AUTO_INCREMENT  PRIMARY KEY,
                             User_Id VARCHAR(255),
                             Carte_Fizica_Id VARCHAR(255),
                             Data_Imprumut DATETIME,
                             Data_Restituire DATETIME,
                             Data_Returnare DATETIME,
                             penalizari INT
);

CREATE TABLE biblioteca.Utilizatori (
                             id int AUTO_INCREMENT PRIMARY KEY,
                             username VARCHAR(255),
                             password VARCHAR(255),
                             phone VARCHAR(20),
                             email VARCHAR(255),
                             path_ci VARCHAR(255),
                             activ BOOLEAN,
                             admin BOOLEAN
);
