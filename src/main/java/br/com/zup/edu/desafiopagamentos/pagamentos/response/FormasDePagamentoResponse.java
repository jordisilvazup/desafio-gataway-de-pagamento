package br.com.zup.edu.desafiopagamentos.pagamentos.response;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormasDePagamentoResponse {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String descricao;

    public FormasDePagamentoResponse(FormaDePagamento formaDePagamento) {
        this.id=formaDePagamento.getId();
        this.descricao=formaDePagamento.getDescricao();
    }
}
