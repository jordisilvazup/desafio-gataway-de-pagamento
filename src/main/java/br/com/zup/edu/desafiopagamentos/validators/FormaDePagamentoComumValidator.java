package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class FormaDePagamentoComumValidator implements Validator {

    private final List<AceitaFormaDePagmentoValidator> validacoes;
    private String mensagem;

    public FormaDePagamentoComumValidator(List<AceitaFormaDePagmentoValidator> validacoes) {
        this.validacoes = validacoes;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PagamentoRequest.class);
    }

    @Override
    @Transactional
    public void validate(Object o, Errors errors) {
        this.mensagem = "";

        PagamentoRequest request = (PagamentoRequest) o;

        this.mensagem = validacoes.stream()
                .map(valitador -> valitador.validar(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(mensagem, String::concat);

        if (!mensagem.isBlank())
            errors.rejectValue("idFormaPagamento", null, mensagem);

    }

}
