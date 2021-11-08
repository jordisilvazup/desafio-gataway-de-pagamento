package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessarPagamentoOnlineService;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.pedidos.clients.PedidoClient;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public interface ProcessarPagamento {
    ResponseEntity<?> processar(BiFunction<Long, Class<?>, Object>buscarNoBancoDeDados, BigDecimal valorPedido, PagamentoRequest request, FormaDePagamento formaDePagamento, ExecutorTransacional executorTransacional, ProcessarPagamentoOnlineService processarPagamentoOnlineService);
}
