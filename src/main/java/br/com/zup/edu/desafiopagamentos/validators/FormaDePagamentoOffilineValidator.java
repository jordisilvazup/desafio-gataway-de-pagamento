package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.TipoPagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Objects;

import static br.com.zup.edu.desafiopagamentos.pagamentos.TipoPagamento.*;
import static java.util.Objects.*;


@Component
public class FormaDePagamentoOffilineValidator implements Validator {
    private final EntityManager manager;

    public FormaDePagamentoOffilineValidator(EntityManager manager) {
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

        if(!formaDePagamento.disponivelOffline() && isNull(request.getNumCartao()) && isNull(request.getCodSeguranca())){
            errors.rejectValue("idFormaPagamento",null,"A forma de pagamento escolhida não é offline.");
        }


    }
}
