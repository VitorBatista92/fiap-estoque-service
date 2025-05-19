package br.com.postechfiap.fiap_estoque_service.usercase;

import br.com.postechfiap.fiap_estoque_service.entities.EstoqueEntity;
import br.com.postechfiap.fiap_estoque_service.interfaces.EstoqueRepository;
import br.com.postechfiap.fiap_estoque_service.usecases.DeletarEstoqueUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DeletarEstoqueUseCaseTest {

    @InjectMocks
    private DeletarEstoqueUseCaseImpl deletarEstoqueUseCase;

    @Mock
    private EstoqueRepository estoqueRepository;

    @Test
    void DeletaEstoqueComSucesso() {
        // Arrange
        EstoqueEntity estoqueMock = new EstoqueEntity(1L,"Estoque 1", "SKU1", 0L);
        String SKU = "SKU1";

        when(estoqueRepository.findBySku("SKU1")).thenReturn(Optional.of(estoqueMock));
        //when(estoqueRepository.findById(1l)).thenReturn(Optional.of(estoqueMock));
        doNothing().when(estoqueRepository).delete(estoqueMock);

        //  Act
        String response = deletarEstoqueUseCase.execute(SKU);

        //  Assert
        assertNotNull(response);
        assertEquals("Estoque com sku " + SKU + " foi deletado com sucesso!",response);
    }

    @Test
    void DeveLancarExcecao_QuandoEstoqueNaoEncontrado() {
        // Arrange
        //EstoqueEntity estoqueMock = new EstoqueEntity(1L,"Estoque 1", "SKU1", 0L);
        String SKU = "SKU1";

        when (estoqueRepository.findBySku(SKU)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class,
                () -> deletarEstoqueUseCase.execute(SKU));

        assertEquals("Estoque não encontrado.", exception.getMessage());

        verify(estoqueRepository, times(1)).findBySku(SKU);
        verify(estoqueRepository, never()).delete(any(EstoqueEntity.class));
    }
}
