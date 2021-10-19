package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
        this.mensagem="";
        AtomicBoolean existValidacao = new AtomicBoolean(false);

        PagamentoRequest request = (PagamentoRequest) o;

        validacoes.forEach(
                cliente -> {
                    String resposta = cliente.aceita(request);
                    acrescentarRespostaDeValidacao(resposta);
                    if (!resposta.isBlank())
                        existValidacao.set(true);
                }
        );

        if (existValidacao.get())
            errors.rejectValue("idFormaPagamento", null, mensagem);

    }

    void acrescentarRespostaDeValidacao(String mensagem) {
        this.mensagem = this.mensagem.concat(mensagem);
    }
}
