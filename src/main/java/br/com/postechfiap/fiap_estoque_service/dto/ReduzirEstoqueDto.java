package br.com.postechfiap.fiap_estoque_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ReduzirEstoqueDto(
        @NotNull
        String sku,
        @Valid @NotNull ReduzirEstoqueRequest reduzirEstoqueRequest
) {
}

