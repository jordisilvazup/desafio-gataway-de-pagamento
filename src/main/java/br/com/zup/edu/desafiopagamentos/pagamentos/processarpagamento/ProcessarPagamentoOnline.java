package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessarPagamentoOnlineService;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.BiFunction;
public class ProcessarPagamentoOnline implements ProcessarPagamento{




    @Override
    public ResponseEntity<?> processar(BiFunction<Long, Class<?>, Object> buscarNoBancoDeDados, BigDecimal valorPedido, PagamentoRequest request, FormaDePagamento formaDePagamento, ExecutorTransacional executorTransacional,ProcessarPagamentoOnlineService service) {
        return service.realizarPagamento(request,valorPedido);
    }
}
