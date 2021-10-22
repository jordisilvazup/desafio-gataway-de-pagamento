package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
public class UsuarioAceitaFormaDePagamentoValidator implements AceitaFormaDePagmentoValidator {
    private final EntityManager manager;

    public UsuarioAceitaFormaDePagamentoValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<String> validar(PagamentoRequest request) {
        FormaDePagamento formaDePagamento = manager.find(FormaDePagamento.class, request.getIdFormaPagamento());
        Usuario usuario = manager.find(Usuario.class, request.getIdUsuario());

        if (!usuario.aceita(formaDePagamento)) {
            return Optional.of("Usuario não aceita esta forma de pagamento. ");
        }

        return Optional.empty();
    }
}
