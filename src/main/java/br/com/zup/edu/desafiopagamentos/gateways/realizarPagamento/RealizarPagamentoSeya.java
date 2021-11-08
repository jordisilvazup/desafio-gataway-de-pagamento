package br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento;

import br.com.zup.edu.desafiopagamentos.gateways.clients.PaymentExternalRequest;
import br.com.zup.edu.desafiopagamentos.gateways.clients.SeyaClient;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.gateways.clients.VerificacaoDeCartaoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;

import java.math.BigDecimal;
import java.util.Optional;


public class RealizarPagamentoSeya implements RealizarPagamento {

    private final SeyaClient client=new SeyaClient();


    @Override
    public Optional<TentativaPagamentoResponse> realizarPagamento(PagamentoRequest request, BigDecimal valorCompra) {

        TentativaPagamentoResponse tentativaPagamentoResponse;


        try {

            PaymentExternalRequest paymentExternalRequest = new PaymentExternalRequest(request);
            VerificacaoDeCartaoResponse verificacaoCartao = client.verificaCartao(paymentExternalRequest);
            paymentExternalRequest.setValor_compra(valorCompra);
            String id = verificacaoCartao.getId();

            tentativaPagamentoResponse = client.realizarPagamento(paymentExternalRequest, id);

        } catch (Exception e) {
            return Optional.empty();
        }


        return Optional.of(tentativaPagamentoResponse);


    }
}
