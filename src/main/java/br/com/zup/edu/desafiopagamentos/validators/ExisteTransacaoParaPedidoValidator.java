package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.transacoes.TransacaoRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ExisteTransacaoParaPedidoValidator implements Validator {
    private final TransacaoRepository repository;

    public ExisteTransacaoParaPedidoValidator(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PagamentoRequest.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        PagamentoRequest request = (PagamentoRequest) o;

        boolean exist = repository.existsByPedido(request.getIdPedido());

        if(exist){
            errors.rejectValue("idPedido",null,"ja existe uma transacao iniciada para este pedido.");
        }


    }
}
