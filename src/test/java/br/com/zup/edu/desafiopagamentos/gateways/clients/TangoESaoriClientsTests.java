package br.com.zup.edu.desafiopagamentos.gateways.clients;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.http.HttpEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.FALHA;
import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.SUCESSO;
import static java.util.Map.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.ok;

@Testable
public class TangoESaoriClientsTests {
    private Map<String, GatewayClient> clientsEquals;
    private RestTemplate template = mock(RestTemplate.class);
    private PagamentoRequest pagamentoRequest;
    private Long ID_FORMA_DE_PAGAMENTO = 4L;
    private Long ID_USUARIO = 1L;
    private Long ID_RESTAURANTE = 1L;
    private Long ID_PEDIDO = 1L;
    private TentativaPagamentoResponse tentativaPagamentoSucesso;
    private TentativaPagamentoResponse tentativaPagamentoFalha;

    @BeforeEach
    void setUp() {
        this.clientsEquals = of("http://localhost:8080/api/saori/payment", new SaoriClient(), "http://localhost:8080/api/tango/payment", new TangoClient());
        this.pagamentoRequest = new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_DE_PAGAMENTO);
        this.tentativaPagamentoSucesso = new TentativaPagamentoResponse("qualquer informacao extra", SUCESSO);
        this.tentativaPagamentoFalha = new TentativaPagamentoResponse("qualquer informacao extra", FALHA);
    }

    @Test
    void deveRealizarOPagamento() {

        PaymentExternalRequest request = new PaymentExternalRequest(pagamentoRequest);

        request.setValorCompra(BigDecimal.TEN);

        Set<Map.Entry<String, GatewayClient>> clients = this.clientsEquals.entrySet();


        clients.forEach(client -> {


            ReflectionTestUtils.setField(client.getValue(), "template", template);

            HttpEntity<PaymentExternalRequest> payload = new HttpEntity<>(request);

            when(template.postForEntity(client.getKey(), payload, TentativaPagamentoResponse.class))
                    .thenReturn(ok(tentativaPagamentoSucesso));

            TentativaPagamentoResponse response = client.getValue().realizarPagamento(request);

            assertEquals(tentativaPagamentoSucesso, response);
        });
    }

    @Test
    void naoDeveRealizarOPagamento() {

        PaymentExternalRequest request = new PaymentExternalRequest(pagamentoRequest);

        request.setValorCompra(BigDecimal.ONE);
        Set<Map.Entry<String, GatewayClient>> clients = this.clientsEquals.entrySet();


        clients.forEach(client -> {


            ReflectionTestUtils.setField(client.getValue(), "template", template);
            HttpEntity<PaymentExternalRequest> payload = new HttpEntity<>(request);

            when(template.postForEntity(client.getKey(), payload, TentativaPagamentoResponse.class))
                    .thenReturn(ok(tentativaPagamentoFalha));

            TentativaPagamentoResponse response = client.getValue().realizarPagamento(request);

            assertEquals(tentativaPagamentoFalha, response);
        });
    }


}
