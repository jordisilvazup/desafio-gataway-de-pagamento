package br.com.zup.edu.desafiopagamentos.gateways.clients;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.FALHA;
import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.SUCESSO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.ok;

class TangoClientTest {
    private TangoClient client;
    private RestTemplate template = mock(RestTemplate.class);
    private PagamentoRequest pagamentoRequest;
    private Long ID_FORMA_DE_PAGAMENTO = 4L;
    private Long ID_USUARIO = 1L;
    private Long ID_RESTAURANTE = 1L;
    private Long ID_PEDIDO = 1L;
    private String ENDERECO = "http://localhost:8080/api/tango/payment";
    private TentativaPagamentoResponse tentativaPagamentoSucesso;
    private TentativaPagamentoResponse tentativaPagamentoFalha;

    @BeforeEach
    void setUp() {
        this.client = new TangoClient();
        ReflectionTestUtils.setField(client,"template",template);
        this.pagamentoRequest = new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_DE_PAGAMENTO);
        this.tentativaPagamentoSucesso =new TentativaPagamentoResponse("qualquer informacao extra", SUCESSO);
        this.tentativaPagamentoFalha =new TentativaPagamentoResponse("qualquer informacao extra", FALHA);
    }


    @Test
    void deveRealizarOPagamento() {

        PaymentExternalRequest request = new PaymentExternalRequest(pagamentoRequest);

        request.setValorCompra(BigDecimal.TEN);

        HttpEntity<PaymentExternalRequest> payload= new HttpEntity<>(request);

        when(template.postForEntity(ENDERECO, payload, TentativaPagamentoResponse.class))
                .thenReturn(ok(tentativaPagamentoSucesso));

        TentativaPagamentoResponse response = this.client.realizarPagamento(request);

        assertEquals(tentativaPagamentoSucesso,response);
    }

    @Test
    void naoDeveRealizarOPagamento() {

        PaymentExternalRequest request = new PaymentExternalRequest(pagamentoRequest);

        request.setValorCompra(BigDecimal.ONE);

        HttpEntity<PaymentExternalRequest> payload= new HttpEntity<>(request);

        when(template.postForEntity(ENDERECO, payload, TentativaPagamentoResponse.class))
                .thenReturn(ok(tentativaPagamentoFalha));

        TentativaPagamentoResponse response = this.client.realizarPagamento(request);

        assertEquals(tentativaPagamentoFalha,response);
    }

}