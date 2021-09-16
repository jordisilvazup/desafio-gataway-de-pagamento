package br.com.zup.edu.desafiopagamentos.strategy;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;

import java.util.List;

public interface ListarFormasDepagamentoStrategy {
    List<FormasDePagamentoResponse> formasDePagamentoEmcomun(List<FormaDePagamento> formaDePagamentos);
}
