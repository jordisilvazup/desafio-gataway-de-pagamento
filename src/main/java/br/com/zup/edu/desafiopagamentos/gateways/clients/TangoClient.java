package br.com.zup.edu.desafiopagamentos.gateways.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TangoClient implements GatewayClient{
    private RestTemplate template = GatewayClientFactory.getRestTemplate();
    private String ENDERECO = "http://localhost:8082/api/tango/payment";


    public TentativaPagamentoResponse realizarPagamento(PaymentExternalRequest request) {
        HttpEntity<PaymentExternalRequest> payload = new HttpEntity<>(request);
        ResponseEntity<TentativaPagamentoResponse> response = template.postForEntity(ENDERECO, payload, TentativaPagamentoResponse.class);
        return response.getBody();
    }

}
