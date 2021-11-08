package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.transacoes.TransacaoRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

@Component
public class ExisteTransacaoParaPedidoValidator implements Validator {
    private final TransacaoRepository repository;
    private final EntityManager manager;

    public ExisteTransacaoParaPedidoValidator(TransacaoRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PagamentoRequest.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        PagamentoRequest request = (PagamentoRequest) o;
        FormaDePagamento formaDePagamento = manager.find(FormaDePagamento.class, request.getIdFormaPagamento());

        if (!formaDePagamento.disponivelOffline()) {

            boolean exist = repository.existsByPedido(request.getIdPedido());
            if (exist) {
                errors.rejectValue("idPedido", null, "ja existe uma transacao iniciada para este pedido.");
            }


        }
    }
}
