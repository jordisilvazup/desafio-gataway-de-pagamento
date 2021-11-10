package br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento;

import br.com.zup.edu.desafiopagamentos.gateways.clients.PaymentExternalRequest;
import br.com.zup.edu.desafiopagamentos.gateways.clients.SaoriClient;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;

import java.math.BigDecimal;
import java.util.Optional;


public class RealizarPagamentoSaori implements RealizarPagamento {

    private SaoriClient client = new SaoriClient();


    @Override
    public Optional<TentativaPagamentoResponse> realizarPagamento(PagamentoRequest request, BigDecimal valorCompra) {

        TentativaPagamentoResponse tentativaPagamentoResponse;


        try {

            PaymentExternalRequest paymentExternalRequest = new PaymentExternalRequest(request);
            paymentExternalRequest.setValorCompra(valorCompra);

            tentativaPagamentoResponse = client.realizarPagamento(paymentExternalRequest);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }


        return Optional.of(tentativaPagamentoResponse);


    }

    public void setClient(SaoriClient client) {
        this.client = client;
    }
}