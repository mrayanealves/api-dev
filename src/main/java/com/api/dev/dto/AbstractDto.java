package com.api.dev.dto;

/**
 * Classe que representa a estrutura de base para todos os Dto do sistema
 * @author Maria Rayane Alves (mrayanealves)
 */
public abstract class AbstractDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
