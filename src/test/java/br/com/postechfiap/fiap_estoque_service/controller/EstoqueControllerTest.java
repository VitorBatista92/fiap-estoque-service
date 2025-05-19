package br.com.postechfiap.fiap_estoque_service.controller;

import br.com.postechfiap.fiap_estoque_service.controllers.EstoqueController;
import br.com.postechfiap.fiap_estoque_service.dto.*;
import br.com.postechfiap.fiap_estoque_service.entities.EstoqueEntity;
import br.com.postechfiap.fiap_estoque_service.exceptions.estoque.EstoqueNotFoundException;
import br.com.postechfiap.fiap_estoque_service.interfaces.EstoqueRepository;
import br.com.postechfiap.fiap_estoque_service.interfaces.usecases.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.mockito.Mock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class EstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueController estoqueController;

    @Mock
    private CadastrarEstoqueUseCase cadastrarEstoque;
    @Mock
    private BuscarEstoqueUseCase buscarEstoque;
    @Mock
    private AtualizarEstoqueUseCase atualizarEstoque;
    @Mock
    private ReduzirEstoqueUseCase reduzirEstoque;
    @Mock
    private AdicionarEstoqueUseCase adicionarEstoque;
    @Mock
    private DeletarEstoqueUseCase deletarEstoque;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void DeveCadastrarEstoqueComSucesso() throws Exception {
        // Arrange
        var request = new EstoqueRequest("Teste 1", "SKU10", 0L);
        var response = new EstoqueResponse(1L,"Teste 1", "SKU10", 0L);

        // Assert
        when(cadastrarEstoque.execute(any(EstoqueRequest.class))).thenReturn(response);

        // Act
        mockMvc.perform(post("/estoque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nome").value("Teste 1"))
                .andExpect(jsonPath("$.sku").value("SKU10"))
                .andExpect(jsonPath("$.quantidade").value(0));

    }

    @Test
    void deveBuscarEstoqueComSucesso() throws Exception {
        // Arrange
        var request = "Teste";
        var listaResponse = new ListaEstoqueResponse(List.of(new EstoqueResponse(1L,"Teste 1", "SKU1", 1L)));
        // Assert
        when(buscarEstoque.execute(request)).thenReturn(listaResponse);

        // Act
        mockMvc.perform(get("/estoque")
                        .param("nome", request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estoques[0].nome").value("Teste 1"))
                .andExpect(jsonPath("$.estoques[0].sku").value("SKU1"))
                .andExpect(jsonPath("$.estoques[0].quantidade").value(0));

    }


    @Test
    void deveRetornarBadRequest_QuandoQueryDeBuscaEstiverVazia() throws Exception {
        mockMvc.perform(get("/estoque")
                        .param("nome", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveAtualizarEstoqueComSucesso() throws Exception {
        // Arrange
        var request = new EstoqueRequest("Teste 1", "SKU1", 0L);
        var response = new EstoqueResponse(1L,"Teste 2", "SKU1", 0L);

        // Assert
        when(atualizarEstoque.execute(any(AtualizarEstoqueDto.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/estoque/{sku}", "SKU1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Teste 1"))
                .andExpect(jsonPath("$.sku").value("SKU1"));
    }

    @Test
    void deveAdicionarEstoqueComSucesso() throws Exception {
        // Arrange
        var request = new AdicionarEstoqueRequest("SKU1", 10L) ;
        var response = new EstoqueResponse(1L,"Teste 1", "SKU1", 11L);

        // Assert
        when(adicionarEstoque.execute(any(AdicionarEstoqueDto.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/estoque/adicionar/{sku}", "SKU1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Teste 1"))
                .andExpect(jsonPath("$.sku").value("SKU1"))
                .andExpect(jsonPath("$.quantidade").value(11L));
    }

    @Test
    void deveRetornarNotFound_QuandoAdicionarEstoqueInexistente() throws Exception {
        // Arrange
        var request = new ReduzirEstoqueRequest("SKU21", 2L) ;

        when(adicionarEstoque.execute(any(AdicionarEstoqueDto.class)))
                .thenThrow(new EstoqueNotFoundException());

        // Act & Assert
        mockMvc.perform(put("/estoque/adicionar/{sku}", "SKU21")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Estoque não encontrado."));
    }

    @Test
    void deveRetornarNotFound_QuandoAdicionarJsonErrado() throws Exception {
        // Arrange
        when(adicionarEstoque.execute(any(AdicionarEstoqueDto.class)))
                .thenThrow(new EstoqueNotFoundException());

        String json = """
            {
              "nome": "SKU21"
            }
        """;
        // Act & Assert
        mockMvc.perform(put("/estoque/adicionar/{sku}", "SKU21")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("must not be blank"));
    }


    @Test
    void deveReduzirEstoqueComSucesso() throws Exception {
        // Arrange
        var request = new ReduzirEstoqueRequest("SKU2", 2L) ;
        var response = new EstoqueResponse(1L,"Teste 2", "SKU2", 10L);

        // Assert
        when(reduzirEstoque.execute(any(ReduzirEstoqueDto.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/estoque/reduzir/{sku}", "SKU2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.nome").value("Teste 2"))
                .andExpect(jsonPath("$.sku").value("SKU2"))
                .andExpect(jsonPath("$.quantidade").value(0L));
    }

    @Test
    void deveRetornarNotFound_QuandoReduzirEstoqueInexistente() throws Exception {
        // Arrange
        var request = new ReduzirEstoqueRequest("SKU21", 2L) ;
        when(reduzirEstoque.execute(any(ReduzirEstoqueDto.class)))
                .thenThrow(new EstoqueNotFoundException());

        // Act & Assert
        mockMvc.perform(put("/estoque/reduzir/{sku}", "SKU21")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Estoque não encontrado."));
    }

    @Test
    void deveDeletarEstoqueComSucesso() throws Exception {
        // Arrange
        var repository = EstoqueEntity.builder().nome("Teste1").sku("SKU100").quantidade(10L).build();

        repository = estoqueRepository.save(repository);

        String sku = repository.getSku();

        String mensagem = "Estoque com sku " + sku + " foi deletado com sucesso!";

        when(deletarEstoque.execute(sku)).thenReturn(mensagem);

        // Act & Assert
        mockMvc.perform(delete("/estoque/{sku}", sku))
                .andExpect(status().isOk())
                .andExpect(content().string(mensagem));
    }

    @Test
    void deveRetornarNotFound_QuandoDeletarEstoqueInexistente() throws Exception {
        // Arrange
        String sku = "SKU101";
        when(deletarEstoque.execute(sku))
                .thenThrow(new EstoqueNotFoundException());

        // Act & Assert
        mockMvc.perform(delete("/estoque/{sku}", sku))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Estoque não encontrado."));
    }
}
