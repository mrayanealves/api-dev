package com.api.dev.repository;

import com.api.dev.model.AbstractModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Interface genéria para os repositórios do projeto
 * @param <M> model do qual se trata o repositório
 * @author Maria Rayane Alves (mrayanealves)
 */
@NoRepositoryBean
public interface GeneticRepository <M extends AbstractModel> extends JpaRepository<M, Long> {
    /**
     * Buscar todos ativos
     * @return List<M>
     */
    @Query(value = "select * from #{#entityName} limit :limit offset(:page - 1) * :limit", nativeQuery = true)
    List<M> findAll(@Param(value = "limit") int limit, @Param(value = "page") int page);

    /**
     * Buscar um objeto active
     * @param arg0 Long: id a ser encontrado
     * @return object
     */
    @Override
    @Query(value = "select * from #{#entityName} where id = ?1", nativeQuery = true)
    Optional<M> findById(Long arg0);
}
