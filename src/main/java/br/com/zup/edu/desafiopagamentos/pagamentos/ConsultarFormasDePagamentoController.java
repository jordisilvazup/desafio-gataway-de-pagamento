package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.FormasDePagamentoEmComumRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static br.com.zup.edu.desafiopagamentos.util.PropriedadeCache.DEFAULT_TIMING;

@RestController
@RequestMapping("/api/v1")
public class ConsultarFormasDePagamentoController {

    private final ConsultarFormaDePagamentoDoUsuarioNoBancoDeDadosService consultarNoBancoDeDadosService;
    private final RestaurantePreferidoDoUsuarioCacheService cacheService;

    public ConsultarFormasDePagamentoController(ConsultarFormaDePagamentoDoUsuarioNoBancoDeDadosService consultarNoBancoDeDadosService, RestaurantePreferidoDoUsuarioCacheService cacheService) {
        this.consultarNoBancoDeDadosService = consultarNoBancoDeDadosService;
        this.cacheService = cacheService;
    }


    @GetMapping("/forma-de-pagamento")
    public ResponseEntity<?> consultarFormasDePagamentoEmComum(@RequestBody @Valid FormasDePagamentoEmComumRequest request) {

        var responses = consultarNoBancoDeDadosService.consultar(request);

        return ResponseEntity.ok(responses);

    }
}
