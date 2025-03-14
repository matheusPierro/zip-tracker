package com.matheuspierro.zip_tracker.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient", url = "http://localhost:8081") // URL da API Mockada
public interface CepClient {

    @GetMapping("/cep/{cep}")
    CepResponse buscarCep(@PathVariable String cep);
}
