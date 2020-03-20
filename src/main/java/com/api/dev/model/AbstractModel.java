package com.api.dev.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Classe que representa a estrutura inicial padrão dos modelos
 * @author Maria Rayane Alves
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractModel implements Serializable {
    @Column(name = "data_criacao", nullable = true, updatable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao = new Date();

    public abstract Long getId();

    public abstract void setId(Long id);

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
