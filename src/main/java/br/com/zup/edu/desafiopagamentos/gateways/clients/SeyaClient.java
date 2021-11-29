package br.com.zup.edu.desafiopagamentos.gateways.clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SeyaClient {

    private RestTemplate template = GatewayClientFactory.getRestTemplate();

    private String ENDERECO_BASE = "http://localhost:8080/api/seya";

    public VerificacaoDeCartaoResponse verificaCartao(PaymentExternalRequest request) {
        HttpEntity<PaymentExternalRequest> payload = new HttpEntity<>(request);
        String endereco = ENDERECO_BASE + "/verify";
        ResponseEntity<VerificacaoDeCartaoResponse> response = template.postForEntity(endereco, payload, VerificacaoDeCartaoResponse.class);
        return response.getBody();
    }


    public TentativaPagamentoResponse realizarPagamento(PaymentExternalRequest request, String id) {
        HttpEntity<PaymentExternalRequest> payload = new HttpEntity<>(request);
        String endereco = ENDERECO_BASE + "/" + id + "/payment";
        ResponseEntity<TentativaPagamentoResponse> response = template.postForEntity(endereco, payload, TentativaPagamentoResponse.class);
        return response.getBody();
    }

}
