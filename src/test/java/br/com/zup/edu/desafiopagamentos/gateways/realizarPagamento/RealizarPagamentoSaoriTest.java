package br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento;

import br.com.zup.edu.desafiopagamentos.gateways.clients.PaymentExternalRequest;
import br.com.zup.edu.desafiopagamentos.gateways.clients.SaoriClient;
import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.SUCESSO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
class RealizarPagamentoSaoriTest {

    private RealizarPagamentoSaori realizarPagamento;
    private SaoriClient clientMock = mock(SaoriClient.class);
    private Long ID_FORMA_PAGAMENTO = 4L;
    private Long ID_USUARIO = 1L;
    private Long ID_RESTAURANTE = 1L;
    private Long ID_PEDIDO = 1L;
    private PagamentoRequest pagamentoRequest;
    private BigDecimal valorPedido;


    @BeforeEach
    void setUp() {
        this.realizarPagamento = new RealizarPagamentoSaori();
        this.pagamentoRequest= new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_PAGAMENTO);
        this.valorPedido=new BigDecimal("24.5");
    }

    @Test
    void deveRealizarOPagamento() {
        PaymentExternalRequest request = new PaymentExternalRequest(pagamentoRequest);

        request.setValor_compra(valorPedido);

        TentativaPagamentoResponse respostaSucesso=new TentativaPagamentoResponse("Pagamento realizado com Saori.", SUCESSO);

        when(clientMock.realizarPagamento(request)).thenReturn(respostaSucesso);

        ReflectionTestUtils.setField(realizarPagamento,"client",clientMock);

        Optional<TentativaPagamentoResponse> possivelTentativaDePagamento = realizarPagamento.realizarPagamento(pagamentoRequest, valorPedido);

        assertTrue(possivelTentativaDePagamento.isPresent());
        assertEquals(respostaSucesso,possivelTentativaDePagamento.get());
    }

}