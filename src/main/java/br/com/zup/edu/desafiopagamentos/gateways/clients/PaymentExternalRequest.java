package br.com.zup.edu.desafiopagamentos.gateways.clients;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class PaymentExternalRequest {
    @JsonProperty("num_cartao")
    @NotBlank
    private String numCartao;
    @JsonProperty("codigo_seguranca")
    @NotBlank
    private String codigoSeguranca;
    @JsonProperty("valor_compra")
    private BigDecimal valorCompra;

    public PaymentExternalRequest(PagamentoRequest pagamentoRequest){
        this.numCartao= pagamentoRequest.getNumCartao();
        this.codigoSeguranca= pagamentoRequest.getCodSeguranca();
    }

    public PaymentExternalRequest() {
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }
}
