package com.api.dev.controller;

import com.api.dev.dto.AbstractDto;
import com.api.dev.model.AbstractModel;
import com.api.dev.service.AbstractService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Classe que abstrai os métodos comuns dos controladores da aplicação
 * @param <M> model do qual se trata o controlador
 * @param <Dto> dto que reflete o model para retornar ao cliente
 * @author Maria Rayane Alves (mrayanealves)
 */
@CrossOrigin
public abstract class AbstractRestController <M extends AbstractModel, Dto extends AbstractDto>{
    protected abstract AbstractService<M, Dto> service();

    @ApiOperation(value = "Retorna a todos os registros paginados.",
            httpMethod = "GET")
    @GetMapping
    public List<Dto> findAll(@RequestParam("limit") int limit, @RequestParam("page") int page) {
        return service().findAll(limit, page);
    }

    @ApiOperation(value = "Retorna o registro que possui um determinado id.",
            httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<Dto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service().getById(id));
    }

    @ApiOperation(value = "Salva um novo registro.",
            httpMethod = "POST")
    @PostMapping
    public ResponseEntity<Dto> save(@RequestBody Dto entity) {
        return ResponseEntity.ok(service().save(entity));
    }

    @ApiOperation(value = "Remove um registro.",
            httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
        service().delete(id);
        return ResponseEntity.ok().build();
    }
}
