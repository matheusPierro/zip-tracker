package com.matheuspierro.zip_tracker.client;

import lombok.Data;

@Data
public class CepResponse {
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
}
