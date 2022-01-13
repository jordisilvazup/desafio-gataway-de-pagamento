package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.FormasDePagamentoEmComumRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ConsultarFormasDePagamentoController {

    private final ConsultarFormaDePagamentoDoUsuarioNoBancoDeDadosService consultarNoBancoDeDadosService;


    public ConsultarFormasDePagamentoController(ConsultarFormaDePagamentoDoUsuarioNoBancoDeDadosService consultarNoBancoDeDadosService) {
        this.consultarNoBancoDeDadosService = consultarNoBancoDeDadosService;
    }


    @GetMapping("/forma-de-pagamento")
    public ResponseEntity<?> consultarFormasDePagamentoEmComum(@RequestBody @Valid FormasDePagamentoEmComumRequest request) {

        var responses = consultarNoBancoDeDadosService.consultar(request);

        return ResponseEntity.ok(responses);

    }
}
