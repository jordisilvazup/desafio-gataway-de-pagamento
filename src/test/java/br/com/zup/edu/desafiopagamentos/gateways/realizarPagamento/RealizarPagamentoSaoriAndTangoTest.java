package br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento;

import br.com.zup.edu.desafiopagamentos.gateways.clients.GatewayClient;
import br.com.zup.edu.desafiopagamentos.gateways.clients.SaoriClient;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TangoClient;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessaPagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.SUCESSO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RealizarPagamentoSaoriAndTangoTest {
    private Map<GatewayClient, RealizarPagamento> pagamentoMap;
    private RealizarPagamentoSaori realizarPagamento;
    private Long ID_FORMA_PAGAMENTO = 4L;
    private Long ID_USUARIO = 1L;
    private Long ID_RESTAURANTE = 1L;
    private Long ID_PEDIDO = 1L;
    private PagamentoRequest pagamentoRequest;
    private BigDecimal valorPedido;
    private ProcessaPagamento processaPagamento;


    @BeforeEach
    void setUp() {
        this.pagamentoMap = Map.of(mock(SaoriClient.class), new RealizarPagamentoSaori(), mock(TangoClient.class), new RealizarPagamentoTango());

        this.pagamentoRequest = new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_PAGAMENTO);
        this.valorPedido = new BigDecimal("24.5");
        FormaDePagamento formaDePagamento = mock(FormaDePagamento.class);
        this.processaPagamento = new ProcessaPagamento(valorPedido, pagamentoRequest, formaDePagamento);

    }

    @Test
    void deveRealizarOPagamento() {


        TentativaPagamentoResponse respostaSucesso = new TentativaPagamentoResponse("Pagamento realizado com Saori.", SUCESSO);
        Set<Map.Entry<GatewayClient, RealizarPagamento>> entries = pagamentoMap.entrySet();
        entries.forEach(realizarPagamento -> {

            ReflectionTestUtils.setField(realizarPagamento.getValue(), "client", realizarPagamento.getKey());
            when(realizarPagamento.getKey().realizarPagamento(any())).thenReturn(respostaSucesso);

            Optional<TentativaPagamentoResponse> possivelTentativaDePagamento = realizarPagamento.getValue().realizarPagamento(processaPagamento);

            assertTrue(possivelTentativaDePagamento.isPresent());
            assertEquals(respostaSucesso, possivelTentativaDePagamento.get());
        });
    }

    @Test
    void naoDeveRealizarOPagamento() {
        Set<Map.Entry<GatewayClient, RealizarPagamento>> entries = pagamentoMap.entrySet();
        entries.forEach(realizarPagamento -> {

            ReflectionTestUtils.setField(realizarPagamento.getValue(), "client", realizarPagamento.getKey());
            when(realizarPagamento.getKey().realizarPagamento(any())).thenThrow(ResourceAccessException.class);

            Optional<TentativaPagamentoResponse> possivelTentativaDePagamento = realizarPagamento.getValue().realizarPagamento(processaPagamento);

            assertTrue(possivelTentativaDePagamento.isEmpty());

        });
    }

}