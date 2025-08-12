package com.matheuspierro.zip_tracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsLoggingService {

    private final CloudWatchLogsClient cloudWatchLogsClient;

    private static final String LOG_GROUP = "ZipTrackerLogs";
    private static final String LOG_STREAM = "CepConsultas";

    private void inicializarLogs() {
        criarGrupoDeLog();
        criarStreamDeLog();
    }

    private void criarGrupoDeLog() {
        try {
            cloudWatchLogsClient.createLogGroup(
                    CreateLogGroupRequest.builder().logGroupName(LOG_GROUP).build()
            );
            log.info("Log group '{}' criado com sucesso.", LOG_GROUP);
        } catch (ResourceAlreadyExistsException e) {
            log.debug("Log group '{}' já existe.", LOG_GROUP);
        } catch (Exception e) {
            log.error("Erro ao criar log group '{}': {}", LOG_GROUP, e.getMessage(), e);
        }
    }

    private void criarStreamDeLog() {
        try {
            cloudWatchLogsClient.createLogStream(
                    CreateLogStreamRequest.builder()
                            .logGroupName(LOG_GROUP)
                            .logStreamName(LOG_STREAM)
                            .build()
            );
            log.info("Log stream '{}' criado com sucesso no grupo '{}'.", LOG_STREAM, LOG_GROUP);
        } catch (ResourceAlreadyExistsException e) {
            log.debug("Log stream '{}' já existe no grupo '{}'.", LOG_STREAM, LOG_GROUP);
        } catch (Exception e) {
            log.error("Erro ao criar log stream '{}': {}", LOG_STREAM, e.getMessage(), e);
        }
    }

    public void logConsulta(String cep, String response) {
        try {
            PutLogEventsRequest request = construirRequest(cep, response);
            cloudWatchLogsClient.putLogEvents(request);
            log.debug("Consulta de CEP '{}' registrada no CloudWatch.", cep);
        } catch (Exception e) {
            log.error("Falha ao registrar consulta de CEP '{}' no CloudWatch: {}", cep, e.getMessage(), e);
        }
    }

    private PutLogEventsRequest construirRequest(String cep, String response) {
        return PutLogEventsRequest.builder()
                .logGroupName(LOG_GROUP)
                .logStreamName(LOG_STREAM)
                .logEvents(Collections.singletonList(
                        InputLogEvent.builder()
                                .message("Consulta de CEP: " + cep + " - Resposta: " + response)
                                .timestamp(System.currentTimeMillis())
                                .build()
                ))
                .build();
    }
}
