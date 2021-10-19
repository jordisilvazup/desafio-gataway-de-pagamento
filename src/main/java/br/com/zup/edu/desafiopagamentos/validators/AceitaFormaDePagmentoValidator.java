package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;

public interface AceitaFormaDePagmentoValidator {
    String aceita(PagamentoRequest request);
}
