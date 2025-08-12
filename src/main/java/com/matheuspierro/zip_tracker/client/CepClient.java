package com.matheuspierro.zip_tracker.client;

import com.matheuspierro.zip_tracker.client.dto.CepResponse;
import com.matheuspierro.zip_tracker.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient", url = "${cep.service.url}", configuration = FeignConfig.class)
public interface CepClient {
    @GetMapping("/cep/{cep}")
    CepResponse buscarCep(@PathVariable("cep") String cep);
}
