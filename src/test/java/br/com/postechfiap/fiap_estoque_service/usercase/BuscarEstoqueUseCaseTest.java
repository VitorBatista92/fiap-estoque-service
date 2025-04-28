package br.com.postechfiap.fiap_estoque_service.usercase;

import br.com.postechfiap.fiap_estoque_service.dto.ListaEstoqueResponse;
import br.com.postechfiap.fiap_estoque_service.entities.EstoqueEntity;
import br.com.postechfiap.fiap_estoque_service.interfaces.EstoqueRepository;
import br.com.postechfiap.fiap_estoque_service.usecases.BuscarEstoqueUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BuscarEstoqueUseCaseTest {

    @InjectMocks
    private BuscarEstoqueUseCaseImpl buscarEstoqueUseCase;

    @Mock
    private EstoqueRepository estoqueRepository;

    @Test
    void deveRetornarListaEstoque_quandoProcurarPorNomeOuSku(){
        // Arrange
        String query = "Estoque Teste";
        List<EstoqueEntity> estoqueMock = List.of(
                new EstoqueEntity(1L,"Estoque 1", "SKU1", 0L),
                new EstoqueEntity(2L,"Estoque 2", "SKU2", 10L)
        );

        // Simulate
        when(estoqueRepository.findByNomeContainingIgnoreCaseOrSkuIgnoreCase(query,query))
                .thenReturn(estoqueMock);

        // Act
        ListaEstoqueResponse response = buscarEstoqueUseCase.execute(query);

        // Assert
        assertNotNull(response);
        assertEquals(2,response.estoques().size());
        assertEquals("Estoque 1", response.estoques().get(0).nome());
        assertEquals("Estoque 2", response.estoques().get(1).nome());

        // Garantir
        verify(estoqueRepository,times(1)).findByNomeContainingIgnoreCaseOrSkuIgnoreCase(query,query);
    }
}
