package br.com.zup.edu.desafiopagamentos.gateways.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TangoClient {
    private String ENDERECO="http://localhost:8080/api/tango/payment";


    public TentativaPagamentoResponse realizarPagamento(PaymentExternalRequest request){
        RestTemplate template = new RestTemplate();
        HttpEntity<PaymentExternalRequest> payload = new HttpEntity<>(request);
        ResponseEntity<TentativaPagamentoResponse> response = template.postForEntity(ENDERECO, payload, TentativaPagamentoResponse.class);
        return response.getBody();
    }
}
