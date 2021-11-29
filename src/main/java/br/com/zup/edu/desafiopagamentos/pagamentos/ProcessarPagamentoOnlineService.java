package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.exception.PagamentoNaoProcessadoException;
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
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.springframework.http.ResponseEntity.*;

@Service
public class ProcessarPagamentoOnlineService {
    private final GatewayRepository repository;

    public ProcessarPagamentoOnlineService(GatewayRepository repository) {
        this.repository = repository;
    }


    public Map<String,String> realizarPagamento(ProcessaPagamento processaPagamento, ExecutorTransacional executorTransacional) throws PagamentoNaoProcessadoException {


        BiFunction<Long, Class<?>, Object> buscarNoBancoDeDados = (id, classe) -> executorTransacional.getManager().find(classe, id);
        List<Gateway> gatewaysPorMenorCusto = gatewaysPorMenorCusto(processaPagamento.getValorPedido());


        Pagamento pagamento = processaPagamento.paraPagamento();

        pagamento(processaPagamento, gatewaysPorMenorCusto, pagamento, executorTransacional, buscarNoBancoDeDados);

        if (pagamento.naoProcessado()) {
            throw new PagamentoNaoProcessadoException("Este pagamento nao foi processado.");
        }

        return Map.of();
    }

    private void pagamento(ProcessaPagamento processaPagamento, List<Gateway> gatewaysPorMenorCusto, Pagamento pagamento, ExecutorTransacional executorTransacional, BiFunction<Long, Class<?>, Object> buscarNoBancoDeDados) {
        EntityManager manager = executorTransacional.getManager();
        FormaDePagamento formaDePagamento= processaPagamento.getFormaDePagamento();

        executorTransacional.executa(() -> {
            manager.persist(pagamento);
            return pagamento;
        });

        while (!gatewaysPorMenorCusto.isEmpty()) {

            Gateway gateway = gatewaysPorMenorCusto.get(0);

            Optional<TentativaPagamentoResponse> possivelTentativaPagamentoResponse = gateway.realizarPagamento(processaPagamento);

            if (possivelTentativaPagamentoResponse.isPresent()) {
                TentativaPagamentoResponse tentativaPagamentoResponse = possivelTentativaPagamentoResponse.get();
                executorTransacional.executa(() -> {
                    Transacao transacao = tentativaPagamentoResponse.paraTransacao(buscarNoBancoDeDados, pagamento, processaPagamento.getPagamentoRequest(), processaPagamento.getValorPedido());
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
