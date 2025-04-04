package br.com.postechfiap.fiap_estoque_service.dto;

import jakarta.validation.constraints.NotBlank;

public record EstoqueRequest(
        @NotBlank Long id
) {
}
