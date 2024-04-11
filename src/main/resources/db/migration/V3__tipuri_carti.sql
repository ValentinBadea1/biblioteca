CREATE TABLE biblioteca.Carti_Online (
                              id int AUTO_INCREMENT  PRIMARY KEY,
                              Editura_Id INT,
                              Autor_Id INT,
                              Categorie_Carti_Id INT,
                              titlu VARCHAR(255),
                              editie VARCHAR(255),
                              traducator VARCHAR(255),
                              path VARCHAR(255),
                              Nr_Pagini INT,
                              Data_Aparitiei DATETIME
);
