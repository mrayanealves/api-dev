package com.api.dev.exception;

/**
 * Exceção padrão de quando a entidade não for encontrada no banco
 * @author Maria Rayane Alves (mrayanealves)
 */
public class EntityNotFoundException extends javax.persistence.EntityNotFoundException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super( message );
    }
}
