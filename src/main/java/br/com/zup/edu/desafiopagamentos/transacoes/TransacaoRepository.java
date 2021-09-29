package br.com.zup.edu.desafiopagamentos.transacoes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao,Long> {

    boolean existsByPedido(Long idPedido);
}
