package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;

import java.util.Optional;

public interface AceitaFormaDePagmentoValidator {
    Optional<String> validar(PagamentoRequest request);
}
