package br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento;

import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;

import java.math.BigDecimal;
import java.util.Optional;

public interface RealizarPagamento {
    Optional<TentativaPagamentoResponse> realizarPagamento(PagamentoRequest request, BigDecimal valorCompra);
}
