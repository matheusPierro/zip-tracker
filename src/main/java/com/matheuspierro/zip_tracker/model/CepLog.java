package com.matheuspierro.zip_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cep_logs")
public class CepLog {

    @Id
    private String id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private LocalDateTime consultaHora;
}
