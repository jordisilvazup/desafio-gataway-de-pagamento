package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.exception.PagamentoNaoProcessadoException;
import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessaPagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessarPagamentoOnlineService;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

public class ProcessarPagamentoOnline implements ProcessarPagamento {

    @Override
    public Map<String,String> processar(ProcessaPagamento processaPagamento, ExecutorTransacional executorTransacional, ProcessarPagamentoOnlineService service) throws PagamentoNaoProcessadoException {
        return service.realizarPagamento(processaPagamento,executorTransacional);
    }
}
