package br.com.zup.edu.desafiopagamentos.pagamentos.response;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FormasDePagamentoResponse implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String descricao;
    @JsonProperty
    private String formaDePagamento;

    public FormasDePagamentoResponse(Long id, String descricao, String formaDePagamento) {
        this.id = id;
        this.descricao = descricao;
        this.formaDePagamento = formaDePagamento;
    }


    public FormasDePagamentoResponse(FormaDePagamento formaDePagamento) {
        this.id=formaDePagamento.getId();
        this.descricao=formaDePagamento.getDescricao();
        this.formaDePagamento=formaDePagamento.getTipo().name();
    }

    public FormasDePagamentoResponse() {
    }
}
