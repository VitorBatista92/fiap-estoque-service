package br.com.postechfiap.fiap_estoque_service.usercase;

import br.com.postechfiap.fiap_estoque_service.dto.EstoqueRequest;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueResponse;
import br.com.postechfiap.fiap_estoque_service.entities.EstoqueEntity;
import br.com.postechfiap.fiap_estoque_service.interfaces.EstoqueRepository;
import br.com.postechfiap.fiap_estoque_service.interfaces.usecases.CadastrarEstoqueUseCase;
import br.com.postechfiap.fiap_estoque_service.usecases.CadastrarEstoqueUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CadastrarEstoqueUseCaseTest {

    @InjectMocks
    private CadastrarEstoqueUseCaseImpl cadastrarEstoqueUseCase;
    @Mock
    private EstoqueRepository estoqueRepository;

    @Test
    void deveCadastrarEstoque(){
        EstoqueEntity estoqueMock = new EstoqueEntity(1l,"Teste 1","SKU-1",10l);
        EstoqueRequest request = new EstoqueRequest("Teste 1","SKU-1",10l);
        String listaRequest ="Teste 1";

        when(estoqueRepository.save(any(EstoqueEntity.class))).thenReturn(estoqueMock);

        EstoqueResponse response = cadastrarEstoqueUseCase.execute(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Teste 1", response.nome());
        verify(estoqueRepository, times(1)).save(any(EstoqueEntity.class));
    }

    @Test
    void deveLancarExcecao_QuandoErroAoSalvar(){
        EstoqueRequest request = new EstoqueRequest("Teste 1","SKU-1",10l);
        when(estoqueRepository.save(any(EstoqueEntity.class)))
                .thenThrow(new RuntimeException("Erro ao salvar Estoque"));
        var exception = assertThrows(RuntimeException.class,
                () -> cadastrarEstoqueUseCase.execute(request));

        assertEquals("Erro ao salvar Estoque", exception.getMessage());
    }
}
