package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.Pagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessarPagamentoOnlineService;
import br.com.zup.edu.desafiopagamentos.pagamentos.TentativaDeTransacao;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;
@Component
public class ProcesarPagamentoOffilne implements ProcessarPagamento{


    @Override
    public ResponseEntity<?> processar(BiFunction<Long, Class<?>, Object> buscarNoBancoDeDados, BigDecimal valorPedido, PagamentoRequest request, FormaDePagamento formaDePagamento, ExecutorTransacional executorTransacional, ProcessarPagamentoOnlineService service) {

        Pagamento pagamento = executorTransacional.executa(() -> {

            EntityManager manager = executorTransacional.getManager();

            Pagamento tentativaDePagamento = request.paraPagamentoOffline(buscarNoBancoDeDados, formaDePagamento,new TentativaDeTransacao(request.getIdPedido(), valorPedido));

            manager.persist(tentativaDePagamento);

            return tentativaDePagamento;

        });
        return ResponseEntity.ok().body(Map.of("codigoParaContinuarProcessing", pagamento.getCodigoParaConfirmacaoDePagamento()));
    }


}
