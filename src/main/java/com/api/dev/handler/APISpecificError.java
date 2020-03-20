package com.api.dev.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe padrão de formatação de informações mais específicas das exceções lançadas
 * a fim de uma análise mais profunda pelo programador.
 * @author Maria Rayane Alves (mrayanealves)
 */
public class APISpecificError {
    // Tipo da exceção lançada
    private String errorType;

    // Lista de mensagens de erro que deseja
    private List<String> debugMessage;

    public APISpecificError() {
        this.debugMessage = new ArrayList<>();
    }

    public APISpecificError(String errorType) {
        this();
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public List<String> getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(List<String> debugMessage) {
        this.debugMessage = debugMessage;
    }
}
