package com.api.dev.exception;

/**
 * Exceção padrão para todos os problemas de regra de negócio que forem lançadas pela API
 * @author Maria Rayane Alves (mrayanealves)
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super( message );
    }
}
