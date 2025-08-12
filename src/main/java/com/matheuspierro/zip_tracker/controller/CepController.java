package com.matheuspierro.zip_tracker.controller;


import com.matheuspierro.zip_tracker.commons.exception.InvalidCepFormatException;
import com.matheuspierro.zip_tracker.commons.utils.CepUtils;
import com.matheuspierro.zip_tracker.model.CepLog;
import com.matheuspierro.zip_tracker.service.CepService;
import com.matheuspierro.zip_tracker.service.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cep")
@RequiredArgsConstructor
public class CepController {

    private final CepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<ApiResponse<CepLog>> buscarCep(@PathVariable String cep) {
        // normalize and validate in controller quickly
        String normalized = CepUtils.normalizeCep(cep);
        if (!CepUtils.isValidCep(normalized)) {
            throw new InvalidCepFormatException("Invalid CEP format. Expected 00000-000 or 00000000");
        }

        CepLog result = cepService.buscarCep(normalized);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

