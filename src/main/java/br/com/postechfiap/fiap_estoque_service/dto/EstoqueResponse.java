package br.com.postechfiap.fiap_estoque_service.dto;

public record EstoqueResponse(
        Long id,
        String nome,
        Long quantidade
) {
}
