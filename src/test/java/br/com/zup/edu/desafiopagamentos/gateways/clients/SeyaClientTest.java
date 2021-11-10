package br.com.zup.edu.desafiopagamentos.gateways.clients;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.ResponseEntity.*;

class SeyaClientTest {
    private SeyaClient client;
    private RestTemplate template = mock(RestTemplate.class);
    private PagamentoRequest pagamentoRequest;
    private Long ID_FORMA_DE_PAGAMENTO = 1L;
    private Long ID_USUARIO = 1L;
    private Long ID_RESTAURANTE = 1L;
    private Long ID_PEDIDO = 1L;
    private String ENDERECO_BASE = "http://localhost:8080/api/seya";
    private VerificacaoDeCartaoResponse verificacaoDeCartaoResponse;
    private TentativaPagamentoResponse tentativaPagamentoSucesso;
    private TentativaPagamentoResponse tentativaPagamentoFalha;


    @BeforeEach
    void setUp() {
        this.client = new SeyaClient();
        ReflectionTestUtils.setField(client,"template",template);
        this.pagamentoRequest = new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_DE_PAGAMENTO);
        this.verificacaoDeCartaoResponse = new VerificacaoDeCartaoResponse("Qualquer id");
        this.tentativaPagamentoSucesso =new TentativaPagamentoResponse("qualquer informacao extra", SUCESSO);
        this.tentativaPagamentoFalha =new TentativaPagamentoResponse("qualquer informacao extra", FALHA);
    }

    @Test
    void deveVerificarOCartao() {
        PaymentExternalRequest request = new PaymentExternalRequest(pagamentoRequest);

        HttpEntity<PaymentExternalRequest> payload= new HttpEntity<>(request);

        when(template.postForEntity(ENDERECO_BASE + "/verify", payload, VerificacaoDeCartaoResponse.class))
                .thenReturn(ok(verificacaoDeCartaoResponse));

        VerificacaoDeCartaoResponse response = this.client.verificaCartao(request);

        assertEquals(verificacaoDeCartaoResponse,response);
    }
    @Test
    void deveRealizarOPagamento() {
        PaymentExternalRequest request = new PaymentExternalRequest(pagamentoRequest);
        request.setValorCompra(BigDecimal.ONE);
        HttpEntity<PaymentExternalRequest> payload= new HttpEntity<>(request);

        when(template.postForEntity(ENDERECO_BASE + "/qualquerId/payment", payload, TentativaPagamentoResponse.class))
                .thenReturn(ResponseEntity.ok(tentativaPagamentoSucesso));

        TentativaPagamentoResponse response = this.client.realizarPagamento(request,"qualquerId");

        assertEquals(tentativaPagamentoSucesso,response);
    }

    @Test
    void naoDeveRealizarOPagamento() {
        PaymentExternalRequest request = new PaymentExternalRequest(pagamentoRequest);
        request.setValorCompra(BigDecimal.ONE);
        HttpEntity<PaymentExternalRequest> payload= new HttpEntity<>(request);
        when(template.postForEntity(ENDERECO_BASE + "/qualquerId/payment", payload, TentativaPagamentoResponse.class))
                .thenReturn(ok(tentativaPagamentoFalha));

        TentativaPagamentoResponse response = this.client.realizarPagamento(request,"qualquerId");

        assertEquals(tentativaPagamentoFalha,response);
    }
}