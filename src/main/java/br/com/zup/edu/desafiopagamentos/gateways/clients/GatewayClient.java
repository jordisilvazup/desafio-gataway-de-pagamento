package br.com.zup.edu.desafiopagamentos.gateways.clients;

public interface GatewayClient {
    TentativaPagamentoResponse realizarPagamento(PaymentExternalRequest request);
}
