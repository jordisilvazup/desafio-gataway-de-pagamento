package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.pedidos.clients.PedidoClient;
import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@RestController
@RequestMapping("/api/v1/pagamentos")
public class ProcessarPagamentoOfflineController {
    private final List<Validator> validators;
    private final ExecutorTransacional executorTransacional;
    private final PedidoClient client;
    private final BiFunction<Long, Class<?>, Object> buscarNoBancoDeDados;

    public ProcessarPagamentoOfflineController(List<Validator> validators, ExecutorTransacional executorTransacional, PedidoClient client) {
        this.validators = validators;
        this.executorTransacional = executorTransacional;
        this.client = client;
        this.buscarNoBancoDeDados = (id, classe) -> executorTransacional.getManager().find(classe, id);
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        validators.forEach(binder::addValidators);
    }

    @PostMapping
    public ResponseEntity<?> processarPagamento(@RequestBody @Valid PagamentoRequest request) {

        Map<String, BigDecimal> valorDoPedido = client.consultarPedido(request.getIdPedido()).getBody();

        Pagamento pagamento = executorTransacional.executa(() -> {

            EntityManager manager = executorTransacional.getManager();

            Pagamento tentativaDePagamento = request.paraPagamento(buscarNoBancoDeDados, new TentativaDeTransacao(request.getIdPedido(), valorDoPedido.get("valor")));

            manager.persist(tentativaDePagamento);

            return tentativaDePagamento;

        });

        return ResponseEntity.ok().body(Map.of("codigoParaContinuarProcessamento", pagamento.getCodigoParaConfirmacaoDePagamento()));
    }
}
