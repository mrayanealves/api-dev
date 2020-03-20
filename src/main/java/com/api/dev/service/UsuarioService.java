package com.api.dev.service;

import com.api.dev.dto.UsuarioDto;
import com.api.dev.model.Usuario;
import com.api.dev.repository.GeneticRepository;
import com.api.dev.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends AbstractService<Usuario, UsuarioDto>{
    @Autowired
    private UsuarioRepository repository;

    @Override
    protected GeneticRepository<Usuario> repository() {
        return this.repository;
    }

    @Override
    protected UsuarioDto convertToDto(Usuario entity) {
        return modelMapper.map(entity, UsuarioDto.class);
    }

    @Override
    protected Usuario convertToEntity(UsuarioDto dto) {
        return modelMapper.map(dto, Usuario.class);
    }
}
