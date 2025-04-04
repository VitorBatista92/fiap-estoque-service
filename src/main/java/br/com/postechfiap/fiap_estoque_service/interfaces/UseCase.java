package br.com.postechfiap.fiap_estoque_service.interfaces;

public interface UseCase<Input,Output> {
    Output execute(Input input);
}
