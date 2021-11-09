package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.pagamentos.*;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;

import static org.springframework.http.ResponseEntity.*;

@Component
public class ProcesarPagamentoOffilne implements ProcessarPagamento {

    @Override
    public ResponseEntity<?> processar(ProcessaPagamento processaPagamento, ExecutorTransacional executorTransacional, ProcessarPagamentoOnlineService service) {

        BigDecimal valorPedido=processaPagamento.getValorPedido();
        PagamentoRequest request=processaPagamento.getPagamentoRequest();
        FormaDePagamento formaDePagamento= processaPagamento.getFormaDePagamento();

        Pagamento pagamento = executorTransacional.executa(() -> {

            EntityManager manager = executorTransacional.getManager();

            BiFunction<Long, Class<?>, Object> buscarNoBancoDeDados = (id, classe) -> executorTransacional.getManager().find(classe, id);

            TentativaDeTransacao tentativaDeTransacao = new TentativaDeTransacao(request.getIdPedido(), valorPedido);

            Pagamento tentativaDePagamento = request.paraPagamentoOffline(buscarNoBancoDeDados, formaDePagamento, tentativaDeTransacao);

            manager.persist(tentativaDePagamento);

            return tentativaDePagamento;

        });
        return ok().body(Map.of("codigoParaContinuarProcessing", pagamento.getCodigoParaConfirmacaoDePagamento()));
    }


}
