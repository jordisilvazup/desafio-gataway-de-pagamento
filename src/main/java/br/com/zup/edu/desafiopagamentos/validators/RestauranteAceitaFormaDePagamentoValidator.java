package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
public class RestauranteAceitaFormaDePagamentoValidator implements AceitaFormaDePagmentoValidator {

    private final EntityManager manager;

    public RestauranteAceitaFormaDePagamentoValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<String> validar(PagamentoRequest request) {
        FormaDePagamento formaDePagamento = manager.find(FormaDePagamento.class, request.getIdFormaPagamento());
        Restaurante restaurante = manager.createQuery("select r from Restaurante r join fetch r.formaDePagamentos where r.id=:id", Restaurante.class)
                .setParameter("id", request.getIdRestaurante())
                .getSingleResult();

        if (!restaurante.aceita(formaDePagamento)) {
            return Optional.of("Restaurante não aceita esta forma de pagamento. ");
        }

        return Optional.empty();
    }

}
