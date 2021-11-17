package br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessaPagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessarPagamentoOnlineService;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.text.Normalizer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.*;

@DataJpaTest
class ProcessarPagamentoOnlineTest {
    private ProcessaPagamento processaPagamento;
    private ProcessarPagamentoOnline processarPagamentoOnline;
    private ExecutorTransacional execTransacional = mock(ExecutorTransacional.class);
    private ProcessarPagamentoOnlineService service = mock(ProcessarPagamentoOnlineService.class);
    private final static Long ID_PEDIDO = 1L;
    private final static Long ID_RESTAURANTE = 1L;
    private final static Long ID_FORMA_DE_PAGAMENTO_ONLINE = 4L;
    private final static Long ID_USUARIO = 1L;
    private PagamentoRequest pagamentoRequest;
    private FormaDePagamento formaDePagamento;
    @PersistenceContext
    private EntityManager manager;
    private BigDecimal valorPedido;

    @BeforeEach
    void setUp() {
        this.pagamentoRequest = new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_DE_PAGAMENTO_ONLINE, "123", "12341231", "QUALQUER INFO EXTRA.");
        this.formaDePagamento = manager.find(FormaDePagamento.class, ID_FORMA_DE_PAGAMENTO_ONLINE);
        this.valorPedido = new BigDecimal("23.5");
        this.processaPagamento = new ProcessaPagamento(valorPedido, pagamentoRequest, formaDePagamento);
        this.processarPagamentoOnline = new ProcessarPagamentoOnline();
    }

    @Test
    void deveProcessarUmPagamentoOnline() {
        when(service.realizarPagamento(processaPagamento, execTransacional)).thenReturn(ok().build());
        ResponseEntity<?> processar = processarPagamentoOnline.processar(processaPagamento, execTransacional, service);
        assertEquals(ok().build(), processar);
    }

    @Test
    void naoDeveProcessarUmPagamentoOnline() {
        when(service.realizarPagamento(processaPagamento, execTransacional)).thenReturn(unprocessableEntity().build());
        ResponseEntity<?> processar = processarPagamentoOnline.processar(processaPagamento, execTransacional, service);
        assertEquals(unprocessableEntity().build(), processar);
    }

}