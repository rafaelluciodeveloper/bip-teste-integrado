package com.beneficio.backend.exception;

/**
 * Exceção customizada para recursos não encontrados.
 * Usada para padronizar respostas HTTP 404.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     * 
     * @param message mensagem de erro
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa da exceção.
     * 
     * @param message mensagem de erro
     * @param cause causa da exceção
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
