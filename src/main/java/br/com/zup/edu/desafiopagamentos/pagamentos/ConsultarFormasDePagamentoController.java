package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.FormasDePagamentoEmComumRequest;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ConsultarFormasDePagamentoController {

    private final EntityManager manager;

    public ConsultarFormasDePagamentoController(EntityManager manager) {
        this.manager = manager;
    }

    @GetMapping("/forma-de-pagamento")
    @Transactional
    public ResponseEntity<?> consultarFormasDePagamentoEmComum(@RequestBody @Valid FormasDePagamentoEmComumRequest request) {

        Usuario usuario = manager.find(Usuario.class, request.getIdUsuario());
        Restaurante restaurante = manager.find(Restaurante.class, request.getIdRestaurante());

        List<FormasDePagamentoResponse> formasDePagamentoEmComum = restaurante.meiosDePagamentoPara(usuario)
                .stream()
                .map(FormasDePagamentoResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(formasDePagamentoEmComum);
    }
}
