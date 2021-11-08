package br.com.zup.edu.desafiopagamentos.gateways.clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class SaoriClient {
    private  String ENDERECO="http://localhost:8080/api/saori/payment";

    public TentativaPagamentoResponse realizarPagamento(PaymentExternalRequest request){
        RestTemplate template= new RestTemplate();
        HttpEntity<PaymentExternalRequest> payload = new HttpEntity<>(request);
        System.out.println(ENDERECO);
        ResponseEntity<TentativaPagamentoResponse> response = template.postForEntity(ENDERECO, request, TentativaPagamentoResponse.class);
        return response.getBody();
    }
}
