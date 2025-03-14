package com.matheuspierro.zip_tracker.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;

import java.util.Collections;

@Service
public class AwsLoggingService {
    private final CloudWatchLogsClient cloudWatchLogsClient;
    private static final String LOG_GROUP = "ZipTrackerLogs";
    private static final String LOG_STREAM = "CepConsultas";

    public AwsLoggingService(CloudWatchLogsClient cloudWatchLogsClient) {
        this.cloudWatchLogsClient = cloudWatchLogsClient;
        criarGrupoEStream();
    }

    private void criarGrupoEStream() {
        try {
            cloudWatchLogsClient.createLogGroup(CreateLogGroupRequest.builder()
                    .logGroupName(LOG_GROUP)
                    .build());
        } catch (Exception ignored) { }
        try {
            cloudWatchLogsClient.createLogStream(CreateLogStreamRequest.builder()
                    .logGroupName(LOG_GROUP)
                    .logStreamName(LOG_STREAM)
                    .build());
        } catch (Exception ignored) { }
    }

    public void logConsulta(String cep, String response) {
        PutLogEventsRequest request = PutLogEventsRequest.builder()
                .logGroupName(LOG_GROUP)
                .logStreamName(LOG_STREAM)
                .logEvents(Collections.singletonList(
                        InputLogEvent.builder()
                                .message("Consulta de CEP: " + cep + " - Resposta: " + response)
                                .timestamp(System.currentTimeMillis())
                                .build()
                ))
                .build();

        cloudWatchLogsClient.putLogEvents(request);
    }
}
