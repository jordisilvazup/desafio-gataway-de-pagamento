package br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento;

import br.com.zup.edu.desafiopagamentos.gateways.clients.SaoriClient;
import br.com.zup.edu.desafiopagamentos.gateways.clients.SeyaClient;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.gateways.clients.VerificacaoDeCartaoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.SUCESSO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RealizarPagamentoSeyaTest {
    private RealizarPagamentoSeya realizarPagamento;
    private SeyaClient clientMock = mock(SeyaClient.class);
    private Long ID_FORMA_PAGAMENTO = 4L;
    private Long ID_USUARIO = 1L;
    private Long ID_RESTAURANTE = 1L;
    private Long ID_PEDIDO = 1L;
    private PagamentoRequest pagamentoRequest;
    private BigDecimal valorPedido;

    @BeforeEach
    void setUp() {
        this.realizarPagamento = new RealizarPagamentoSeya();
        this.pagamentoRequest = new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_PAGAMENTO);
        this.valorPedido = new BigDecimal("24.5");
        realizarPagamento.setClient(clientMock);
    }

    @Test
    void deveRealizarOPagamento() {

        VerificacaoDeCartaoResponse verficaCartaoResponse = new VerificacaoDeCartaoResponse("qualquer id");

        TentativaPagamentoResponse respostaSucesso = new TentativaPagamentoResponse("Pagamento realizado com Seya.", SUCESSO);

        when(clientMock.verificaCartao(any())).thenReturn(verficaCartaoResponse);
        when(clientMock.realizarPagamento(any(), any())).thenReturn(respostaSucesso);


        Optional<TentativaPagamentoResponse> possivelTentativaDePagamento = realizarPagamento.realizarPagamento(pagamentoRequest, valorPedido);

        assertTrue(possivelTentativaDePagamento.isPresent());
        assertEquals(respostaSucesso, possivelTentativaDePagamento.get());
    }

    @Test
    void naoDeveRealizarOPagamento() {

        VerificacaoDeCartaoResponse verficaCartaoResponse = new VerificacaoDeCartaoResponse("qualquer id");

        when(clientMock.verificaCartao(any())).thenThrow(ResourceAccessException.class);

        Optional<TentativaPagamentoResponse> possivelTentativaDePagamento = realizarPagamento.realizarPagamento(pagamentoRequest, valorPedido);

        assertTrue(possivelTentativaDePagamento.isEmpty());

    }
}