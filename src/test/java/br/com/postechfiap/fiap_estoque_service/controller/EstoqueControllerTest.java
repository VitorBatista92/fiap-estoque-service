package br.com.postechfiap.fiap_estoque_service.controller;

import br.com.postechfiap.fiap_estoque_service.controllers.EstoqueController;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueRequest;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueResponse;
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
        var request = new EstoqueRequest("Teste 1", "SKU10", 1L);
        var response = new EstoqueResponse(1L,"Teste 1", "SKU10", 1L);

        // Assert
        when(cadastrarEstoque.execute(any(EstoqueRequest.class))).thenReturn(response);

        // Act
        mockMvc.perform(post("/estoque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nome").value("Teste 1"))
                .andExpect(jsonPath("$.sku").value("SKU10"));

    }
}
