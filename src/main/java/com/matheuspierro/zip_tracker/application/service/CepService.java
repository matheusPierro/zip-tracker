package com.matheuspierro.zip_tracker.application.service;

import com.matheuspierro.zip_tracker.application.exception.ExternalServiceException;
import com.matheuspierro.zip_tracker.infrastructure.client.CepClient;
import com.matheuspierro.zip_tracker.application.dto.CepResponse;
import com.matheuspierro.zip_tracker.application.exception.CepNotFoundException;
import com.matheuspierro.zip_tracker.domain.model.CepLog;
import com.matheuspierro.zip_tracker.domain.repository.CepLogRepository;
import com.matheuspierro.zip_tracker.infrastructure.logging.AwsLoggingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CepService {

    private final CepClient cepClient;
    private final CepLogRepository cepLogRepository;
    private final AwsLoggingService awsLoggingService;

    @Cacheable(value = "cepCache", key = "#cep")
    @Transactional
    @Retry(name = "cepRetry")
    @CircuitBreaker(name = "cepCircuit", fallbackMethod = "fallbackBuscarCep")
    public CepLog buscarCep(String cep) {
        long start = System.currentTimeMillis();
        try {
            CepResponse response = cepClient.buscarCep(cep);
            long duration = System.currentTimeMillis() - start;

            // se response é null, lance NotFound (se for possível)
            if (response == null || response.getCep() == null) {
                CepLog notFoundLog = buildLogForError(cep, null, "NOT_FOUND", duration);
                cepLogRepository.save(notFoundLog);
                awsLoggingService.logConsulta(cep, "CEP not found");
                throw new CepNotFoundException("CEP not found: " + cep);
            }

            CepLog log = new CepLog();
            log.setCep(response.getCep());
            log.setLogradouro(response.getLogradouro());
            log.setBairro(response.getBairro());
            log.setCidade(response.getCidade());
            log.setEstado(response.getEstado());
            log.setConsultaHora(LocalDateTime.now());
            log.setStatus("SUCCESS");
            log.setResponseTimeMs(duration);
            log.setRawResponse(response.toString());

            cepLogRepository.save(log);
            awsLoggingService.logConsulta(cep, log.toString());

            return log;
        } catch (CepNotFoundException e) {
            // Log e repassa
            long duration = System.currentTimeMillis() - start;
            CepLog notFoundLog = buildLogForError(cep, e, "NOT_FOUND", duration);
            cepLogRepository.save(notFoundLog);
            awsLoggingService.logConsulta(cep, "CEP not found: " + e.getMessage());
            throw e;
        } catch (ExternalServiceException e) {
            long duration = System.currentTimeMillis() - start;
            CepLog errorLog = buildLogForError(cep, e, "ERROR", duration);
            cepLogRepository.save(errorLog);
            awsLoggingService.logConsulta(cep, "External service error: " + e.getMessage());
            throw e;
        }
    }

    public CepLog fallbackBuscarCep(String cep, Throwable t) {
        long now = System.currentTimeMillis();
        CepLog fallback = buildLogForError(cep, t, "FALLBACK", 0L);
        cepLogRepository.save(fallback);
        awsLoggingService.logConsulta(cep, "Fallback invoked: " + t.getMessage());
        throw new ExternalServiceException("External CEP service unavailable. Please try again later.");
    }

    private CepLog buildLogForError(String cep, Throwable t, String status, Long responseTimeMs) {
        CepLog log = new CepLog();
        log.setCep(cep);
        log.setConsultaHora(LocalDateTime.now());
        log.setStatus(status);
        log.setResponseTimeMs(responseTimeMs);
        log.setRawResponse(t != null ? t.toString() : null);
        return log;
    }
}

