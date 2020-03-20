package com.api.dev.handler;

import com.api.dev.exception.BusinessException;
import com.api.dev.exception.EntityNotFoundException;
import org.hibernate.JDBCException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;

/**
 * Classe de manipulação dos erros lançados e construção de uma resposta mais legível para o cliente.
 * @author Maria Rayane Alves (mrayanealves)
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return buildResponseEntity(new APIError(HttpStatus.BAD_REQUEST, "Problemas de formação do JSON"));
    }

    /**
     * Este método manipula os erros lançados como EntityNotFound e constrói o retorno tratado
     * com o status lançado como 404 e a mensagem obtida do erro.
     * @param entityNotFoundException EntityNotFoundException: erro capturado
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handlerExceptionEntityNotFound(EntityNotFoundException entityNotFoundException){
        APIError apiError = new APIError(HttpStatus.NOT_FOUND, this.getSimpleMessage(entityNotFoundException));

        return buildResponseEntity(apiError);
    }

    /**
     * Este método manipula os erros lançados como BusinessException e constrói o retorno tratado
     * com o status lançado como 400 e a mensagem obtida do erro.
     * @param businessException BusinessException: erro capturado
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handlerExceptionBusiness(BusinessException businessException){
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, this.getSimpleMessage(businessException));

        return buildResponseEntity(apiError);
    }

    /**
     * Este método manipula os erros lançados como erros de banco JDBCException e constrói o retorno tratado
     * com o status lançado como 500 e a mensagem obtida do erro.
     * @param jdbcException JDBCException: erro capturado
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(JDBCException.class)
    protected ResponseEntity<Object> handlerExceptionConstraintViolation(JDBCException jdbcException){
        APIError apiError = new APIError(HttpStatus.INTERNAL_SERVER_ERROR, this.getJDBCExceptionMessage(jdbcException));
        apiError.getApiSpecificErrors().add(this.getApiSpecificErrorJDBCException(jdbcException));

        return buildResponseEntity(apiError);
    }

    /**
     * Este método manipula os erros lançados como RollbackException e constrói o retorno tratado
     * com o status lançado como 400 e a mensagem obtida do erro.
     * @param rollbackException RollbackException: erro capturado
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(RollbackException.class)
    protected ResponseEntity<Object> handlerExceptionValidation(RollbackException rollbackException){
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, this.getRollbackMessage(rollbackException));

        return this.buildResponseEntity(apiError);
    }

    /**
     * Este método constrói o retorno do tratamento da exceção a partir do objeto ApiError informado.
     * @param apiError ApiError: objeto formado a partir dos erros capturados.
     * @return ResponseEntity<Object>
     */
    private ResponseEntity<Object> buildResponseEntity(APIError apiError){
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    /**
     * Método simples que forma a mensagem que o usuário lerá.
     * Esse método deve ser chamado quando as exceções são construídas pelo programador e sua mensagem é
     * personalizada por ele.
     * @param exception Throwable: exceção lançada que contém a mensagem.
     * @return String
     */
    private String getSimpleMessage(Throwable exception){
        if ((exception.getMessage() == null) || (exception.getMessage().isEmpty())) return "Mensagem não informada";
        return exception.getMessage();
    }

    /**
     * Retorna a mensagem vinda da causa da exceção
     * @param rollbackException RollbackException: exceção lançada
     * @return String
     */
    private String getRollbackMessage(RollbackException rollbackException){
        String [] messageCause = rollbackException.getCause().getMessage().split("Message='");
        messageCause = messageCause[1].split("',");

        return messageCause[0];
    }

    /**
     * Método que forma a mensagem que o usuário lerá a partir de exceções lançadas do tipo JDBCException
     * @param jdbcException JDBCException: exceção lançada
     * @return String
     */
    private String getJDBCExceptionMessage(JDBCException jdbcException){
        String [] n = jdbcException.getCause().getMessage().split("Detalhe: ");

        if (n.length < 2){
            n = jdbcException.getCause().getMessage().split("Detail: ");
        }

        return n[n.length-1];
    }

    /**
     * Constrói um objeto ApiSpecificError a partir das informações lançadas na exceção JDBCException
     * @param jdbcException JDBCException: exceção lançada
     * @return ApiSpecificError
     */
    private APISpecificError getApiSpecificErrorJDBCException(JDBCException jdbcException){
        APISpecificError apiSpecificError = new APISpecificError("JDBCException");
        apiSpecificError.getDebugMessage().add("Debug Message: " + jdbcException.getMessage());
        apiSpecificError.getDebugMessage().add("SQL: " + jdbcException.getSQL());
        apiSpecificError.getDebugMessage().add("Cause Message: " + jdbcException.getCause().getMessage());

        return apiSpecificError;
    }
}
