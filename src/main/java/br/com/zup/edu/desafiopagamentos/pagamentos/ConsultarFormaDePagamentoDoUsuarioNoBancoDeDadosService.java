package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.fraude.FraudeRepository;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.FormasDePagamentoEmComumRequest;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

            Usuario usuario = manager.createQuery("select r from Usuario r join fetch r.formaDePagamentos where r.id=:id", Usuario.class)
                    .setHint("org.hibernate.cacheable", true)
                    .setParameter("id", request.getIdUsuario())
                    .getSingleResult();

            Restaurante restaurante = manager.createQuery("select r from Restaurante r join fetch r.formaDePagamentos where r.id=:id", Restaurante.class)
                    .setHint("org.hibernate.cacheable", true)
                    .setParameter("id", request.getIdRestaurante())
                    .getSingleResult();

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
