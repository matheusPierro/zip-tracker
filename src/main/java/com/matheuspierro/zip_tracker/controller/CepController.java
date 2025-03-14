package com.matheuspierro.zip_tracker.controller;


import com.matheuspierro.zip_tracker.model.CepLog;
import com.matheuspierro.zip_tracker.service.CepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cep")
@RequiredArgsConstructor
public class CepController {

    private final CepService cepService;

    @GetMapping("/{cep}")
    public CepLog buscarCep(@PathVariable String cep) {
        return cepService.buscarCep(cep);
    }
}
