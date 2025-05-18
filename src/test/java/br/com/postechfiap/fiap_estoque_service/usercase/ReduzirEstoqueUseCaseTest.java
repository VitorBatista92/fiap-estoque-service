package br.com.postechfiap.fiap_estoque_service.usercase;

import br.com.postechfiap.fiap_estoque_service.dto.*;
import br.com.postechfiap.fiap_estoque_service.entities.EstoqueEntity;
import br.com.postechfiap.fiap_estoque_service.exceptions.estoque.EstoqueNotFoundException;
import br.com.postechfiap.fiap_estoque_service.interfaces.EstoqueRepository;
import br.com.postechfiap.fiap_estoque_service.usecases.ReduzirEstoqueUseCaseImpl;
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
public class ReduzirEstoqueUseCaseTest {
    @InjectMocks
    private ReduzirEstoqueUseCaseImpl reduzirEstoqueUseCase;
    @Mock
    private EstoqueRepository estoqueRepository;

    @Test
    void deveRealizarAtualizacaoEstoque() {
        // Arrange
        EstoqueEntity estoqueMock = new EstoqueEntity(1L,"Estoque 1", "SKU1", 0L);
        ReduzirEstoqueDto adicionarEstoqueDto = new ReduzirEstoqueDto("SKU1", new ReduzirEstoqueRequest("SKU1", 10L));

        when(estoqueRepository.findBySku("SKU1")).thenReturn(of(estoqueMock));

        when(estoqueRepository.save(any(EstoqueEntity.class))).thenReturn(estoqueMock);


        //  Act
        EstoqueResponse response = reduzirEstoqueUseCase.execute(adicionarEstoqueDto);


        //  Assert (Validação)
        assertNotNull(response);
        assertEquals("Estoque 1", response.nome());
        assertEquals("SKU1", response.sku());
        assertEquals(-10L, response.quantidade());

        verify(estoqueRepository, times(1)).save(estoqueMock);
    }

    @Test
    void deveLancarExcecao_EstoqueNaoEncontrado() {
        // Arrange
        ReduzirEstoqueDto reduzirEstoqueDto = new ReduzirEstoqueDto("SKU1", new ReduzirEstoqueRequest("SKU1", 10L));

        when(estoqueRepository.findBySku("SKU1")).thenReturn(Optional.empty());

        //  Act
        var exception = assertThrows(EstoqueNotFoundException.class,
                () -> reduzirEstoqueUseCase.execute(reduzirEstoqueDto));

        //  Assert (Validação)
        assertNotNull(exception);

        verify(estoqueRepository, times(1)).findBySku("SKU1");
        verify(estoqueRepository, never()).save(any(EstoqueEntity.class));
    }

}
