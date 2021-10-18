package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class FormaDePagamentoComumValidator implements Validator {
    private final EntityManager manager;

    public FormaDePagamentoComumValidator(EntityManager manager) {
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

        Usuario usuario = manager.find(Usuario.class, request.getIdUsuario());
        Restaurante restaurante = manager.find(Restaurante.class, request.getIdRestaurante());
        FormaDePagamento formaDePagamento = manager.find(FormaDePagamento.class, request.getIdFormaPagamento());

        if (!usuario.aceita(formaDePagamento) || !restaurante.aceita(formaDePagamento)) {
            errors.rejectValue("idFormaPagamento", null, "O restaurante ou usuario nao possui esta forma de pagamento");
        }


    }
}
