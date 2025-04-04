package br.com.postechfiap.fiap_estoque_service.interfaces.usecases;

import br.com.postechfiap.fiap_estoque_service.dto.EstoqueResponse;
import br.com.postechfiap.fiap_estoque_service.interfaces.UseCase;

public interface BuscarEstoqueUseCase extends UseCase<Long, EstoqueResponse> {
}
