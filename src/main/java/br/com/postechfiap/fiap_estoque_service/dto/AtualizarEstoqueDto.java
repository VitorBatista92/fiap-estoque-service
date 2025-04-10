package br.com.postechfiap.fiap_estoque_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AtualizarEstoqueDto(
    @NotNull String sku,
    @Valid @NotNull EstoqueRequest estoqueRequest
) {
}
