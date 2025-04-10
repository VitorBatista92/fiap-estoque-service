package br.com.postechfiap.fiap_estoque_service.usecases;

import br.com.postechfiap.fiap_estoque_service.dto.AtualizarEstoqueDto;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueResponse;
import br.com.postechfiap.fiap_estoque_service.interfaces.EstoqueRepository;
import br.com.postechfiap.fiap_estoque_service.interfaces.usecases.AtualizarEstoqueUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarEstoqueUseCaseImpl implements AtualizarEstoqueUseCase {

    private  final EstoqueRepository estoqueRepository;

    @Override
    public EstoqueResponse execute (AtualizarEstoqueDto entry){
        long i = 0;

//        List<EstoqueResponse> estoques = estoqueRepository.findByNomeContainingIgnoreCase(entry);
        var estoque= estoqueRepository.findBySku(entry.sku());
        estoque.setNome(entry.estoqueRequest().nome());
        estoque.setQuantidade(entry.estoqueRequest().quantidade());

        estoque = estoqueRepository.save(estoque);

        return  new EstoqueResponse(
                estoque.getId(),
                estoque.getNome(),
                estoque.getSku(),
                estoque.getQuantidade()
        );
    }
}
