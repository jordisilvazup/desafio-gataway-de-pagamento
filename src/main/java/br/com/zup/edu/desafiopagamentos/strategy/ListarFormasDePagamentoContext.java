package br.com.zup.edu.desafiopagamentos.strategy;

import br.com.zup.edu.desafiopagamentos.fraude.FraudeRepository;
import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarFormasDePagamentoContext {
    private final FraudeRepository repository;

    public ListarFormasDePagamentoContext(FraudeRepository repository) {
        this.repository = repository;
    }


    public List<FormasDePagamentoResponse> execute(Usuario usuario, List<FormaDePagamento> formaDePagamentos) {
        boolean ehFraude = repository.existsByEmail(usuario.getEmail());
        if(ehFraude){
            return new ListarFormaDePagamentosParaUsuarioComSuspeitaDeFraude().formasDePagamentoEmcomun(formaDePagamentos);
        }
        return  new ListarFormaDePagamentosParaUsuarioSemSuspeita().formasDePagamentoEmcomun(formaDePagamentos);

    }


}
