package br.com.postechfiap.fiap_estoque_service.interfaces.usecases;

import br.com.postechfiap.fiap_estoque_service.dto.EstoqueRequest;
import br.com.postechfiap.fiap_estoque_service.interfaces.UseCase;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueResponse;

public interface CadastrarEstoqueUseCase extends UseCase<EstoqueRequest,EstoqueResponse> {
}
