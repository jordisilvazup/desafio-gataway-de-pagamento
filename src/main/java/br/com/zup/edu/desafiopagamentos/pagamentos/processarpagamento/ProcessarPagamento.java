package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.exception.PagamentoNaoProcessadoException;
import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessaPagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessarPagamentoOnlineService;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.pedidos.clients.PedidoClient;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;

public interface ProcessarPagamento {

    Map<String,String> processar(ProcessaPagamento processaPagamento,
                                 ExecutorTransacional executorTransacional,
                                 ProcessarPagamentoOnlineService processarPagamentoOnlineService) throws PagamentoNaoProcessadoException;
}
