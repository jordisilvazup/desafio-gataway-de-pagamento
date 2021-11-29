package br.com.zup.edu.desafiopagamentos.gateways.mocks;

import br.com.zup.edu.desafiopagamentos.gateways.clients.PaymentExternalRequest;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.FALHA;
import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.SUCESSO;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/tango/payment")
public class GatewayTangoController {
    private Random random = new Random();
    

    @PostMapping("/payment")
    public ResponseEntity<?> pagar(@RequestBody PaymentExternalRequest request) {

        if (random.nextDouble() <= 0.35) {
            return ok(new TentativaPagamentoResponse("Pagamento não autorizado.", FALHA));
        }
        return ok(new TentativaPagamentoResponse("Pagamento realizado com Saori.", SUCESSO));
    }
}
