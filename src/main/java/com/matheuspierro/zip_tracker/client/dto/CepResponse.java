package com.matheuspierro.zip_tracker.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CepResponse {
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
}

