package br.com.zup.edu.desafiopagamentos.validators;


import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Objects;

import static br.com.zup.edu.desafiopagamentos.pagamentos.TipoPagamento.CARTAO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class FormaDePagamentoOnlineValidator implements Validator {
    private final EntityManager manager;

    public FormaDePagamentoOnlineValidator(EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PagamentoRequest.class);
    }

    @Override
    @Transactional
    public void validate(Object o, Errors errors) {

        PagamentoRequest request = (PagamentoRequest) o;
        FormaDePagamento formaDePagamento = manager.find(FormaDePagamento.class, request.getIdFormaPagamento());

        if (formaDePagamento.disponivelOffline() && nonNull(request.getNum_cartao()) && nonNull(request.getCod_seguranca())) {
            errors.rejectValue("idFormaPagamento", null, "A forma de pagamento escolhida não é online.");
        } else if (formaDePagamento.getTipo().equals(CARTAO)) {
            if (Objects.isNull(request.getCod_seguranca())) {

                errors.rejectValue("cod_seguranca", null, "O codigo de segurança não deve ser vazio.");
            }
            if (Objects.isNull(request.getNum_cartao())) {

                errors.rejectValue("cod_seguranca", null, "O numero do cartao não deve ser vazio.");
            }
        }

    }

}
