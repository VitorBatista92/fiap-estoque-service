package br.com.postechfiap.fiap_estoque_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AdicionarEstoqueDto(
        @NotNull
        String sku,
        @Valid @NotNull AdicionarEstoqueRequest adicionarEstoqueRequest
) {
}

