package br.com.postechfiap.fiap_estoque_service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ListaEstoqueResponse(List<EstoqueResponse> estoques) {
}
