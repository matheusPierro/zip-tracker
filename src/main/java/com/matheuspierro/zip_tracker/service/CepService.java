package com.matheuspierro.zip_tracker.service;


import com.matheuspierro.zip_tracker.client.CepClient;
import com.matheuspierro.zip_tracker.client.CepResponse;
import com.matheuspierro.zip_tracker.model.CepLog;
import com.matheuspierro.zip_tracker.repository.CepLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CepService {

    private final CepClient cepClient;
    private final CepLogRepository cepLogRepository;
    private final AwsLoggingService awsLoggingService;

    public CepLog buscarCep(String cep) {
        CepResponse response = cepClient.buscarCep(cep);

        CepLog log = new CepLog();
        log.setCep(response.getCep());
        log.setLogradouro(response.getLogradouro());
        log.setBairro(response.getBairro());
        log.setCidade(response.getCidade());
        log.setEstado(response.getEstado());
        log.setConsultaHora(LocalDateTime.now());

        cepLogRepository.save(log);
        awsLoggingService.logConsulta(cep, log.toString());
        return log;
    }
}
