package com.api.dev.service;

import com.api.dev.dto.AbstractDto;
import com.api.dev.exception.EntityNotFoundException;
import com.api.dev.model.AbstractModel;
import com.api.dev.repository.GeneticRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe que abstrai os métodos comuns para todos os serviços da aplicação
 * @param <M> model do qual se trata esse serviço
 * @param <Dto> dto que reflete o model
 * @author Maria Rayane Alves (mrayanealves)
 */
@Service
public abstract class AbstractService <M extends AbstractModel, Dto extends AbstractDto>{
    @Autowired
    protected ModelMapper modelMapper;

    /**
     * Representação genérica do repositório concreto o serviço vai manipular
     */
    protected abstract GeneticRepository<M> repository();

    /**
     * Esse método faz a conversão de cada uma das entidades da lista para um objeto do tipo AbstractDto
     * Dessa forma, ele transformará essa lista em uma lista de objetos Dto
     * @param entities List<T>: Lista de entidades
     * @return List<Dto>
     */
    protected List<Dto> convertToListDto(List<M> entities){
        return entities.stream().map(this::convertToDto).collect( Collectors.toList());
    }

    /**
     * Classe a ser implementada cuja função é converter uma entidade do tipo T para um Dto
     * @param entity T: entidade para ser convertida
     * @return Dto
     */
    protected abstract Dto convertToDto(M entity);

    /**
     * Classe a ser implementada cuja função é converter um Dto para uma entidade do tipo T
     * @param dto Dto: dto para ser convertido
     * @return T
     */
    protected abstract M convertToEntity(Dto dto);

    /**
     * Retorna todos as entidades encontradas dentro de um limite e em uma página específica.
     * @param limit int: representa o limite de entidades retornadas
     * @param page int: representa a página na qual entidades retornadas estão
     * @return List<Dto> entidades convertidas para Dto
     */
    public List<Dto> findAll(int limit, int page) {
        List<M> result = repository().findAll(limit, page);

        return this.convertToListDto(result);
    }

    /**
     * Busca uma entidade por id
     * @param id Long: id da entidade desejada
     * @return Dto com a entidade encontrada
     */
    public Dto getById(Long id) {
        Optional<M> entity = repository().findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Não foi possível localizar um(a) " + this.getModelName() + " com esse id.");
        }
        return this.convertToDto(entity.get());
    }

    /**
     * Salva uma nova entidade no banco
     * @param dto Dto com as informações para serem salvas
     * @return Dto com as informações da entidade salva
     */
    public Dto save(Dto dto) {
        M entity = this.convertToEntity(dto);
        beforSave(entity);
        return convertToDto(repository().save(entity));
    }

    /**
     * Remove uma entidade específica
     * @param id Long: id da entidade que se deseja remover
     */
    public void delete(Long id) {
        Optional<M> entity = repository().findById(id);
        if (entity.isPresent()) {
            beforDelete(entity.get());
            repository().deleteById(id);
        } else {
            throw new EntityNotFoundException("Não foi possível localizar um(a) " + this.getModelName() + " com esse id.");
        }
    }

    public void beforSave(M entity) {
    }

    public void beforUpdate(M entity) {
    }

    public void beforDelete(M entity) {
    }

    /**
     * Classe que recupera o nome da classe T para as mensagem personalizadas das exceções lançadas
     * @return String
     */
    private String getModelName(){
        return ((Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
    }
}
