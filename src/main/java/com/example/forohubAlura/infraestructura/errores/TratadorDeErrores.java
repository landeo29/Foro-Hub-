package com.example.forohubAlura.infraestructura.errores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestControllerAdvice
public class TratadorDeErrores {

    private static final Logger logger = LoggerFactory.getLogger(TratadorDeErrores.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException ex) {
        logger.error("Error ocurrido: ", ex);
        ErrorDetails errorDetails = new ErrorDetails("Error interno del servidor", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class ErrorDetails {
        private String error;
        private String message;

        public ErrorDetails(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
