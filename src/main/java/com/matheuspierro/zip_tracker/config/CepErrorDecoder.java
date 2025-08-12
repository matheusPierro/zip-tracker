package com.matheuspierro.zip_tracker.config;

import com.matheuspierro.zip_tracker.commons.exception.CepNotFoundException;
import com.matheuspierro.zip_tracker.commons.exception.ExternalServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class CepErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);

            if (response.status() == 404) {
                return new CepNotFoundException(errorResponse.getMessage());
            } else if (response.status() >= 500) {
                return new ExternalServiceException(errorResponse.getMessage());
            }
            return new RuntimeException("Erro ao consultar CEP: " + errorResponse.getMessage());
        } catch (IOException e) {
            return defaultDecoder.decode(methodKey, response);
        }
    }

    private static class ErrorResponse {
        private String error;
        private String message;

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
