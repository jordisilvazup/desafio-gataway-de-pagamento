package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.pedidos.clients.PedidoClient;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@RestController
@RequestMapping("/api/v1/pagamentos")
public class ProcessarPagamentoController {
    private final List<Validator> validators;
    private final ExecutorTransacional executorTransacional;
    private final PedidoClient client;
    private final ProcessarPagamentoOnlineService processarPagamentoOnlineService;

    public ProcessarPagamentoController(List<Validator> validators, ExecutorTransacional executorTransacional, PedidoClient client, ProcessarPagamentoOnlineService processarPagamentoOnlineService) {
        this.validators = validators;
        this.executorTransacional = executorTransacional;
        this.client = client;
        this.processarPagamentoOnlineService = processarPagamentoOnlineService;
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        validators.forEach(binder::addValidators);
    }

    @PostMapping
    public ResponseEntity<?> processarPagamento(@RequestBody @Valid PagamentoRequest request) {

        Map<String, BigDecimal> valorDoPedido = client.consultarPedido(request.getIdPedido()).getBody();
        BigDecimal valorPedido = valorDoPedido.get("valor");

        FormaDePagamento formaDePagamento = executorTransacional.executa(() -> executorTransacional.getManager().find(FormaDePagamento.class, request.getIdFormaPagamento()));

        ProcessaPagamento processaPagamento = new ProcessaPagamento(valorPedido,request,formaDePagamento);

        return formaDePagamento.processarPagamento(processaPagamento,executorTransacional,processarPagamentoOnlineService);

    }
}
