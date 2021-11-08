package br.com.zup.edu.desafiopagamentos.gateways.clients;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class PaymentExternalRequest {
    @JsonProperty
    @NotBlank
    private String num_cartao;
    @JsonProperty
    @NotBlank
    private String codigo_seguranca;
    @JsonProperty
    private BigDecimal valor_compra;

    public PaymentExternalRequest(PagamentoRequest pagamentoRequest){
        this.num_cartao= pagamentoRequest.getNum_cartao();
        this.codigo_seguranca= pagamentoRequest.getCod_seguranca();
    }

    public PaymentExternalRequest() {
    }

    public void setValor_compra(BigDecimal valor_compra) {
        this.valor_compra = valor_compra;
    }
}
