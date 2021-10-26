package br.com.zup.edu.desafiopagamentos.pagamentos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {
    Optional<Pagamento> findByCodigoParaConfirmacaoDePagamento(String codigo);
}
