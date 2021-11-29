package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.transacoes.StatusTransacao;
import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.math.BigDecimal;

import static br.com.zup.edu.desafiopagamentos.transacoes.StatusTransacao.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Transactional
class ConcluirPagamentoOfflineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager manager;

    private Pagamento pagamento;
    private Usuario usuario;
    private Restaurante restaurante;
    private FormaDePagamento formaDePagamento;
    private Transacao transacao;
    private BigDecimal valor;

    @BeforeEach
    void setUp() {

        this.pagamento = new Pagamento("sem info");
        manager.persist(pagamento);

        this.formaDePagamento = manager.find(FormaDePagamento.class, 1L);
        this.restaurante = manager.find(Restaurante.class, 1L);
        this.usuario = manager.find(Usuario.class, 1L);

        this.valor = new BigDecimal("1.25");


    }

    @Test
    void naoDeveConcluirUmPagamentoQueNaoExiste() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/pagamentos/{codigoDoPagamento}", "qualquerCoisa")
                .contentType(APPLICATION_JSON);


        mockMvc.perform(requestBuilder)
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    void naoDeveConcluirUmPagamentoQueNaoFoiIniciado() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/pagamentos/{codigoDoPagamento}", pagamento.getCodigoParaConfirmacaoDePagamento())
                .contentType(APPLICATION_JSON);


        mockMvc.perform(requestBuilder)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @Transactional
    void naoDeveConcluirUmPagamentoQueJaFoiConcluido() throws Exception {

        Transacao transacaoConcluida = new Transacao(pagamento, restaurante, 1L, valor, CONCLUIDA, formaDePagamento, usuario);
        this.pagamento.associar(transacaoConcluida);


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/pagamentos/{codigoDoPagamento}", pagamento.getCodigoParaConfirmacaoDePagamento())
                .contentType(APPLICATION_JSON);


        mockMvc.perform(requestBuilder)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @Transactional
    void deveConcluirUmPagamentoQueJaFoiIniciado() throws Exception {

        Transacao transacaoParaAguardo = new Transacao(pagamento, restaurante, 1L, valor, AGUARDANDO_CONFIRMACAO, formaDePagamento, usuario);
        this.pagamento.associar(transacaoParaAguardo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/pagamentos/{codigoDoPagamento}", pagamento.getCodigoParaConfirmacaoDePagamento())
                .contentType(APPLICATION_JSON);


        mockMvc.perform(requestBuilder)
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }
}