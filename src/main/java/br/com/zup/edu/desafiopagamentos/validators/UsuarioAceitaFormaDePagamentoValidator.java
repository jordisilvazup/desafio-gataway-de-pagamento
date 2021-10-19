package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class UsuarioAceitaFormaDePagamentoValidator implements AceitaFormaDePagmentoValidator {
    private final EntityManager manager;
    private String report = "";
    public UsuarioAceitaFormaDePagamentoValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public String aceita(PagamentoRequest request) {
        this.report="";
        FormaDePagamento formaDePagamento = manager.find(FormaDePagamento.class, request.getIdFormaPagamento());
        Usuario usuario = manager.find(Usuario.class, request.getIdUsuario());
//        System.out.println(formaDePagamento.getId());
        if (!usuario.aceita(formaDePagamento)) {
            this.report = this.report.concat("Usuario não aceita esta forma de pagamento. ");
        }

        return report;
    }
}
