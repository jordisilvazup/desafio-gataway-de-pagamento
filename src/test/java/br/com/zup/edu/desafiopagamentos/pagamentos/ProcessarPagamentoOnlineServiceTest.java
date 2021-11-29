package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.exception.PagamentoNaoProcessadoException;
import br.com.zup.edu.desafiopagamentos.gateways.Gateway;
import br.com.zup.edu.desafiopagamentos.gateways.GatewayPagamento;
import br.com.zup.edu.desafiopagamentos.gateways.GatewayRepository;
import br.com.zup.edu.desafiopagamentos.gateways.clients.*;
import br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento.RealizarPagamento;
import br.com.zup.edu.desafiopagamentos.gateways.taxas.TaxaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.*;

@SpringBootTest
@Transactional
class ProcessarPagamentoOnlineServiceTest {
    @Autowired
    private GatewayRepository repository;
    @Autowired
    private ExecutorTransacional execTransacional;
    @PersistenceContext
    private EntityManager manager;

    private GatewayRepository repositoryMock=mock(GatewayRepository.class);

    private final static Long ID_PEDIDO = 1L;
    private final static Long ID_RESTAURANTE = 1L;
    private final static Long ID_FORMA_DE_PAGAMENTO_ONLINE = 4L;
    private final static Long ID_USUARIO = 1L;
    private Optional<TentativaPagamentoResponse> tentativaSucesso;
    private Optional<TentativaPagamentoResponse> tentativaFalha;
    private BigDecimal valorPedido;

    private PagamentoRequest pagamentoRequest;
    private ProcessarPagamentoOnlineService service;
    private ProcessaPagamento processaPagamento;

    private GatewayPagamento gatewayPagamentoMock = mock(GatewayPagamento.class);
    private Gateway gatewayMock = mock(Gateway.class);


    @BeforeEach
    void setUp() {
        this.pagamentoRequest = new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_DE_PAGAMENTO_ONLINE, "123", "12341231", "QUALQUER INFO EXTRA.");
        this.valorPedido = new BigDecimal("23.5");
        this.tentativaSucesso = Optional.of(new TentativaPagamentoResponse("qualquer info extra aqui.", StatusPagamento.SUCESSO));
        this.tentativaFalha = Optional.empty();
        this.processaPagamento= new ProcessaPagamento(valorPedido,pagamentoRequest,manager.find(FormaDePagamento.class,ID_FORMA_DE_PAGAMENTO_ONLINE));

    }
    @Test
    void deveFecharComSucessoOPagamento() throws PagamentoNaoProcessadoException {
        when(gatewayMock.taxa(valorPedido)).thenReturn(new BigDecimal("2.0"));
        when(gatewayMock.realizarPagamento(processaPagamento)).thenReturn(tentativaSucesso);


        List<Gateway> gateways= new ArrayList<>(2);
        gateways.add(gatewayMock);
        gateways.add(gatewayMock);
        when(repositoryMock.findAll()).thenReturn(gateways);



        this.service=new ProcessarPagamentoOnlineService(repositoryMock);
        Map<String,String> response = this.service.realizarPagamento(processaPagamento, execTransacional);
        assertEquals(Map.of(),response);

    }

    @Test
    void naoDeveProcessarOPagamento() throws PagamentoNaoProcessadoException {
        when(gatewayMock.taxa(valorPedido)).thenReturn(new BigDecimal("2.0"));
        when(gatewayMock.realizarPagamento(processaPagamento)).thenReturn(tentativaFalha);


        List<Gateway> gateways= new ArrayList<>(2);
        gateways.add(gatewayMock);
        gateways.add(gatewayMock);
        when(repositoryMock.findAll()).thenReturn(gateways);



        this.service=new ProcessarPagamentoOnlineService(repositoryMock);
        assertThrows(PagamentoNaoProcessadoException.class,()->this.service.realizarPagamento(processaPagamento, execTransacional));

    }

}