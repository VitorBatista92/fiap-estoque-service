package br.com.postechfiap.fiap_estoque_service.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, String genero) {
        super(entityName + " não encontrad" + genero + ".");
    }
}
