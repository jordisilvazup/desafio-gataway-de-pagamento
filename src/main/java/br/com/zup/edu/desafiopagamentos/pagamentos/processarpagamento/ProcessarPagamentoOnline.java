package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessaPagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessarPagamentoOnlineService;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public class ProcessarPagamentoOnline implements ProcessarPagamento {

    @Override
    public ResponseEntity<?> processar(ProcessaPagamento processaPagamento, ExecutorTransacional executorTransacional, ProcessarPagamentoOnlineService service) {
        BigDecimal valorPedido=processaPagamento.getValorPedido();
        PagamentoRequest request=processaPagamento.getPagamentoRequest();
        FormaDePagamento formaDePagamento= processaPagamento.getFormaDePagamento();
        return service.realizarPagamento(request, valorPedido,executorTransacional);
    }
}
