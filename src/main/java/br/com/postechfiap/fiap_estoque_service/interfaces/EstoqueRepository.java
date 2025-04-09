package br.com.postechfiap.fiap_estoque_service.interfaces;

import br.com.postechfiap.fiap_estoque_service.entities.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {
    List<EstoqueEntity> findByNomeContainingIgnoreCase(String nome);
}
