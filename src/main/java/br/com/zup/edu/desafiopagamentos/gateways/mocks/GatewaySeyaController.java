package br.com.zup.edu.desafiopagamentos.gateways.mocks;

import br.com.zup.edu.desafiopagamentos.gateways.GatewayRepository;
import br.com.zup.edu.desafiopagamentos.gateways.clients.PaymentExternalRequest;
import br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.*;

@RestController
@RequestMapping("/api/seya")
public class GatewaySeyaController {
    private Random random=new Random();
    private final Set<String> cartoesPermitidos=new HashSet<>();



    @PostMapping("/verify")
    public ResponseEntity<?> verificarCartao(@RequestBody PaymentExternalRequest request){
        if(random.nextDouble()<=0.3){
            return ResponseEntity.unprocessableEntity().build();
        }

        String id = UUID.randomUUID().toString();

        cartoesPermitidos.add(id);

        return ResponseEntity.ok(Map.of("id",id));
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<?> pagar(@PathVariable String id,@RequestBody PaymentExternalRequest request){

        if (!cartoesPermitidos.contains(id)){
            return ResponseEntity.notFound().build();
        }

        if (random.nextDouble()<=0.35){
            return ResponseEntity.ok(new TentativaPagamentoResponse("Pagamento não autorizado.", FALHA));
        }
        return ResponseEntity.ok(new TentativaPagamentoResponse("Pagamento realizado com Seya.",SUCESSO));
    }
}
