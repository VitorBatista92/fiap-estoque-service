package br.com.postechfiap.fiap_estoque_service.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estoque")
@RequiredArgsConstructor
@Validated
@Tag(name = "Estoque",description = "Api de gerenciamento de estoque")
public class EstoqueController {
    // C
    // R
    // U
    // D

}
