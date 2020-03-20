package com.api.dev.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class UsuarioDto extends AbstractDto {
    @NotBlank(message = "Nome deve ser informado.")
    private String nome;

    @NotBlank(message = "Email deve ser informado.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Senha deve ser informada.")
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
