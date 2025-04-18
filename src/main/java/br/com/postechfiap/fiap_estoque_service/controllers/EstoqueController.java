package br.com.postechfiap.fiap_estoque_service.controllers;

import br.com.postechfiap.fiap_estoque_service.dto.AtualizarEstoqueDto;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueRequest;
import br.com.postechfiap.fiap_estoque_service.dto.EstoqueResponse;
import br.com.postechfiap.fiap_estoque_service.dto.ListaEstoqueResponse;
import br.com.postechfiap.fiap_estoque_service.interfaces.EstoqueRepository;
import br.com.postechfiap.fiap_estoque_service.interfaces.usecases.AtualizarEstoqueUseCase;
import br.com.postechfiap.fiap_estoque_service.interfaces.usecases.BuscarEstoqueUseCase;
import br.com.postechfiap.fiap_estoque_service.interfaces.usecases.CadastrarEstoqueUseCase;
import br.com.postechfiap.fiap_estoque_service.interfaces.usecases.DeletarEstoqueUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/estoque")
@RequiredArgsConstructor
@Validated
@Tag(name = "Estoque",description = "Api de gerenciamento de estoque")
public class EstoqueController {

    private final CadastrarEstoqueUseCase cadastrarEstoque;
    private final BuscarEstoqueUseCase buscarEstoque;
    private final AtualizarEstoqueUseCase atualizarEstoque;
    private final DeletarEstoqueUseCase deletarEstoque;

    // C
    @PostMapping
    @Operation(summary = "Cadastra estoque", description = "Cadastra novo estoque")
    public ResponseEntity<EstoqueResponse> create(@Valid @RequestBody EstoqueRequest estoqueRequest) {
        var novoEstoque = cadastrarEstoque.execute(estoqueRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoEstoque.id())
                .toUri();
        return ResponseEntity.created(location).body(novoEstoque);
    }
    // R
    @GetMapping
    @Operation(summary = "Busca Estoque", description = "Busca Estoque por Nome")
    public ResponseEntity<ListaEstoqueResponse> buscarEstoque(@RequestParam String nome) {
        if(nome == null && nome.trim().isEmpty()){
            throw new IllegalArgumentException("O parametro de busca não pode ser vazio");
        }

        var listaEstoque = buscarEstoque.execute(nome);

        return  ResponseEntity.ok(listaEstoque);
    }
    // U

    @PutMapping("/{sku}")
    @Operation(summary = "Atualiza o Estoque", description = "Atualiza o esto com a sku")
    public ResponseEntity<EstoqueResponse> atualizarEstoque(@PathVariable String sku,
                                                            @Valid @RequestBody EstoqueRequest estoqueRequest) {
        var novoEstoque = atualizarEstoque.execute(new AtualizarEstoqueDto(sku, estoqueRequest));
        return  ResponseEntity.ok(novoEstoque);
    }
    // D
    @DeleteMapping("/{sku}")
    @Operation(summary = "Deleta um Estoque", description = "Deleta um item do estoque")
    public ResponseEntity<String> delete(@PathVariable String sku) {
        String mensagem =deletarEstoque.execute(sku);
        return ResponseEntity.ok(mensagem);
    }
}
