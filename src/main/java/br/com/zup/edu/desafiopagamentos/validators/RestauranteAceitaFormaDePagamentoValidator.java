package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class RestauranteAceitaFormaDePagamentoValidator implements AceitaFormaDePagmentoValidator {

    private final EntityManager manager;
    private String report = "";

    public RestauranteAceitaFormaDePagamentoValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public String aceita(PagamentoRequest request) {
        this.report="";
        FormaDePagamento formaDePagamento = manager.find(FormaDePagamento.class, request.getIdFormaPagamento());
        Restaurante restaurante = manager.find(Restaurante.class, request.getIdRestaurante());

        if (!restaurante.aceita(formaDePagamento)) {
            this.report = this.report.concat("Restaurante não aceita esta forma de pagamento. ");
        }

        return report;
    }

}
