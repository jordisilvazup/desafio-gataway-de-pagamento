package br.com.zup.edu.desafiopagamentos.gateways.clients;

import br.com.zup.edu.desafiopagamentos.pagamentos.Pagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import br.com.zup.edu.desafiopagamentos.util.ExecutorTransacional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TentativaPagamentoResponseTest {
    private TentativaPagamentoResponse tentativaPagamentoResponse;
    @Autowired
    private ExecutorTransacional executorTransacional;
    private Pagamento pagamento;
    private PagamentoRequest pagamentoRequest;
    private BigDecimal valorCompra;

    @BeforeEach
    void setUp() {
         Long ID_FORMA_PAGAMENTO = 4L;
         Long ID_USUARIO = 1L;
         Long ID_RESTAURANTE = 1L;
         Long ID_PEDIDO = 1L;
         this.pagamentoRequest= new PagamentoRequest(ID_PEDIDO,ID_RESTAURANTE,ID_USUARIO,ID_FORMA_PAGAMENTO);
         this.pagamento= new Pagamento("Qualquer coisa aqui");
         executorTransacional.getManager().persist(this.pagamento);
    }

    @Test
    void deveRetornarUmaTransacaoComSucesso(){
        this.tentativaPagamentoResponse= new TentativaPagamentoResponse("Qualquer Coisa", SUCESSO);
        Transacao transacao = this.tentativaPagamentoResponse
                .paraTransacao((id, classe) -> executorTransacional.getManager().find(classe, id),
                        this.pagamento,
                        pagamentoRequest,
                        valorCompra
                );

        assertTrue(transacao.concluida());

    }
    @Test
    void deveRetornarUmaTransacaoComFalha(){
        this.tentativaPagamentoResponse= new TentativaPagamentoResponse("Qualquer Coisa", FALHA);
        Transacao transacao = this.tentativaPagamentoResponse
                .paraTransacao((id, classe) -> executorTransacional.getManager().find(classe, id),
                        this.pagamento,
                        pagamentoRequest,
                        valorCompra
                );

        assertTrue(transacao.falha());

    }

}