package br.com.zup.edu.desafiopagamentos.strategy;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class ListarFormaDePagamentosParaUsuarioComSuspeitaDeFraude implements ListarFormasDepagamentoStrategy{

    @Override
    public List<FormasDePagamentoResponse> formasDePagamentoEmcomun(List<FormaDePagamento> formaDePagamentos) {
        return formaDePagamentos.stream()
                .filter(FormaDePagamento::disponivelOffline)
                .map(FormasDePagamentoResponse::new)
                .collect(Collectors.toList());
    }
}
