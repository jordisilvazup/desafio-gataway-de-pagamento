package br.com.zup.edu.desafiopagamentos.gateways.clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class SaoriClient implements GatewayClient{
    private RestTemplate template = GatewayClientFactory.getRestTemplate();
    private String ENDERECO = "http://localhost:8082/api/saori/payment";

    public TentativaPagamentoResponse realizarPagamento(PaymentExternalRequest request) {
        HttpEntity<PaymentExternalRequest> payload = new HttpEntity<>(request);
        ResponseEntity<TentativaPagamentoResponse> response = template.postForEntity(ENDERECO, payload, TentativaPagamentoResponse.class);
        return response.getBody();
    }

}
