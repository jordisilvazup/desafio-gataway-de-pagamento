package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.gateways.Gateway;
import br.com.zup.edu.desafiopagamentos.gateways.GatewayRepository;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class ProcessarPagamentoOnlineService {
    private final GatewayRepository repository;

    public ProcessarPagamentoOnlineService(GatewayRepository repository) {
        this.repository = repository;
    }


    public ResponseEntity<?> realizarPagamento(PagamentoRequest request, BigDecimal valorPedido, ExecutorTransacional executorTransacional) {

        BiFunction<Long, Class<?>, Object> buscarNoBancoDeDados = (id, classe) -> executorTransacional.getManager().find(classe, id);

        List<Gateway> gatewaysPorMenorCusto = gatewaysPorMenorCusto(valorPedido);


        Pagamento pagamento = request.paraPagamentoOnline();

        pagamento(request, valorPedido, gatewaysPorMenorCusto, pagamento, executorTransacional, buscarNoBancoDeDados);

        if (pagamento.naoProcessado()) {
            return ResponseEntity.status(402).build();
        }

        return ResponseEntity.ok().build();
    }

    private void pagamento(PagamentoRequest request, BigDecimal valorPedido, List<Gateway> gatewaysPorMenorCusto, Pagamento pagamento, ExecutorTransacional executorTransacional, BiFunction<Long, Class<?>, Object> buscarNoBancoDeDados) {
        EntityManager manager = executorTransacional.getManager();

        executorTransacional.executa(() -> {
            manager.persist(pagamento);
            return pagamento;
        });

        while (!gatewaysPorMenorCusto.isEmpty()) {

            Gateway gateway = gatewaysPorMenorCusto.get(0);

            Optional<TentativaPagamentoResponse> possivelTentativaPagamentoResponse = gateway.realizarPagamento(request, valorPedido);

            if (possivelTentativaPagamentoResponse.isPresent()) {
                TentativaPagamentoResponse tentativaPagamentoResponse = possivelTentativaPagamentoResponse.get();
                executorTransacional.executa(() -> {
                    Transacao transacao = tentativaPagamentoResponse.paraTransacao(buscarNoBancoDeDados, pagamento, request, valorPedido);
                    manager.persist(transacao);
                    pagamento.associar(transacao);
                    return null;
                });
                if (tentativaPagamentoResponse.sucesso()) {
                    return;
                }
            }

            gatewaysPorMenorCusto.remove(0);
        }
    }


    private List<Gateway> gatewaysPorMenorCusto(BigDecimal valor) {

        List<Gateway> gateways = repository.findAll();

        Comparator<Gateway> gatewayComparator = (o1, o2) -> {
            BigDecimal g1 = o1.taxa(valor);
            BigDecimal g2 = o2.taxa(valor);
            return g1.compareTo(g2);
        };

        gateways.sort(gatewayComparator);

        return gateways;

    }


}
