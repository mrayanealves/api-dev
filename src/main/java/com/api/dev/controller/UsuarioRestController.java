package com.api.dev.controller;

import com.api.dev.dto.UsuarioDto;
import com.api.dev.model.Usuario;
import com.api.dev.service.AbstractService;
import com.api.dev.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioRestController extends AbstractRestController<Usuario, UsuarioDto> {
    @Autowired
    private UsuarioService service;

    @Override
    protected AbstractService<Usuario, UsuarioDto> service() {
        return this.service;
    }
}
