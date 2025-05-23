package br.com.postechfiap.fiap_estoque_service.usercase;

import br.com.postechfiap.fiap_estoque_service.dto.AtualizarEstoqueDto;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueRequest;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueResponse;
import br.com.postechfiap.fiap_estoque_service.entities.EstoqueEntity;
import br.com.postechfiap.fiap_estoque_service.exceptions.estoque.EstoqueNotFoundException;
import br.com.postechfiap.fiap_estoque_service.interfaces.EstoqueRepository;
import br.com.postechfiap.fiap_estoque_service.usecases.AtualizarEstoqueUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AtualizarEstoqueUseCaseTest {
    @InjectMocks
    private AtualizarEstoqueUseCaseImpl atualizarEstoqueUseCase;
    @Mock
    private EstoqueRepository estoqueRepository;

    @Test
    void deveRealizarAtualizacaoEstoque() {
        // Arrange
        EstoqueEntity estoqueMock = new EstoqueEntity(1L,"Estoque 1", "SKU1", 0L);
        AtualizarEstoqueDto atualizaEstoque = new AtualizarEstoqueDto("SKU1", new EstoqueRequest("Estoque 2", "SKU1", 10L));

        when(estoqueRepository.findBySku("SKU1")).thenReturn(of(estoqueMock));

        when(estoqueRepository.save(any(EstoqueEntity.class))).thenReturn(estoqueMock);


        //  Act
        EstoqueResponse response = atualizarEstoqueUseCase.execute(atualizaEstoque);


        //  Assert (Validação)
        assertNotNull(response);
        assertEquals("Estoque 2", response.nome());
        assertEquals("SKU1", response.sku());

        verify(estoqueRepository, times(1)).save(estoqueMock);
    }

    @Test
    void deveLancarExcecao_EstoqueNaoEncontrado() {
        // Arrange
        AtualizarEstoqueDto atualizaEstoque = new AtualizarEstoqueDto("SKU2", new EstoqueRequest("Estoque 2", "SKU2", 10L));

        when(estoqueRepository.findBySku("SKU2")).thenReturn(Optional.empty());

        //  Act
        var exception = assertThrows(EstoqueNotFoundException.class,
                () -> atualizarEstoqueUseCase.execute(atualizaEstoque));

        //  Assert (Validação)
        assertNotNull(exception);

        verify(estoqueRepository, times(1)).findBySku("SKU2");
        verify(estoqueRepository, never()).save(any(EstoqueEntity.class));
    }

}
