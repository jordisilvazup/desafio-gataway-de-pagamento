package br.com.zup.edu.desafiopagamentos.gateways.mocks;

import br.com.zup.edu.desafiopagamentos.gateways.GatewayRepository;
import br.com.zup.edu.desafiopagamentos.gateways.clients.PaymentExternalRequest;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.FALHA;
import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.SUCESSO;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/saori/payment")
public class GatewaySaoriController {
    private Random random = new Random();


    @PostMapping
    public ResponseEntity<?> pagar(@RequestBody PaymentExternalRequest request) {

        if (random.nextDouble() <= 0.35) {
            return ok(new TentativaPagamentoResponse("Pagamento não autorizado.", FALHA));
        }
        return ok(new TentativaPagamentoResponse("Pagamento realizado com Saori.", SUCESSO));
    }
}
