package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.fraude.FraudeRepository;
import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.FormasDePagamentoEmComumRequest;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
public class ConsultarFormaDePagamentoDoUsuarioNoBancoDeDadosService {
    private final ExecutorTransacional executorTransacional;
    private final FraudeRepository fraudeRepository;

    public ConsultarFormaDePagamentoDoUsuarioNoBancoDeDadosService(ExecutorTransacional executorTransacional, FraudeRepository repository) {
        this.executorTransacional = executorTransacional;
        this.fraudeRepository = repository;
    }

    public List<FormasDePagamentoResponse> consultar(FormasDePagamentoEmComumRequest request) {

        List<FormaDePagamento> formaDePagamentos = executorTransacional.executa(() -> {
            EntityManager manager = executorTransacional.getManager();

            Usuario usuario = manager.find(Usuario.class, request.getIdUsuario());

            Restaurante restaurante = manager.find(Restaurante.class, request.getIdRestaurante());

            if (fraudeRepository.existsByEmail(usuario.getEmail())) {
                return restaurante.meiosDePagamentoParaUsuarioSuspeitoFraude(usuario);
            }

            return restaurante.meiosDePagamentoPara(usuario);
        });

        return formaDePagamentos.stream()
                .map(FormasDePagamentoResponse::new)
                .collect(toList());

    }


}
