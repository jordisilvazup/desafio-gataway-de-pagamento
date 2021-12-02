package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento.ConsultarFormaDePagamentoDoUsuarioNoBancoDeDadosService;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.FormasDePagamentoEmComumRequest;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
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

        Optional<RestaurantePreferidoDoUsuario> possivelRestauranteFavoritoDoUsuario = cacheService.buscarEmCache(request.getIdRestaurante(), request.getIdUsuario());

        if (possivelRestauranteFavoritoDoUsuario.isPresent()) {

            RestaurantePreferidoDoUsuario restaurantePreferidoDoUsuario = possivelRestauranteFavoritoDoUsuario.get();

            if (restaurantePreferidoDoUsuario.aptoACache()) {

                return ResponseEntity.ok()
                        .header("Cache-Control", String.format("private, max-age=%d", DEFAULT_TIMING))
                        .body(restaurantePreferidoDoUsuario.formasDePagamento());

            }
            restaurantePreferidoDoUsuario.incrementarAcesso();

            cacheService.atualizar(restaurantePreferidoDoUsuario);
            return ResponseEntity.ok(restaurantePreferidoDoUsuario.formasDePagamento());

        }

        var responses = consultarNoBancoDeDadosService.consultar(request);

        cacheService.salvar(request.getIdUsuario(), request.getIdRestaurante(), responses);

        return ResponseEntity.ok(responses);

    }
}
