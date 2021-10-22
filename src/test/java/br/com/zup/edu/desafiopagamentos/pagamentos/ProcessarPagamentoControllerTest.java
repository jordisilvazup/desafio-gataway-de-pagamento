package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.pedidos.clients.PedidoClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
class ProcessarPagamentoControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PedidoClient client;

    private final static Long ID_PEDIDO = 1L;
    private final static Long ID_RESTAURANTE = 1L;
    private final static Long ID_FORMA_DE_PAGAMENTO = 1L;
    private final static Long ID_USUARIO = 1L;

    @Test
    void deveProcessarUmPagamentoOffline() throws Exception {
        Mockito.when(client.consultarPedido(ID_PEDIDO)).thenReturn(ResponseEntity.ok(Map.of("valor",new BigDecimal("3.5"))));

        PagamentoRequest pagamentoRequest = new PagamentoRequest(ID_PEDIDO, ID_RESTAURANTE, ID_USUARIO, ID_FORMA_DE_PAGAMENTO);
        String request = mapper.writeValueAsString(pagamentoRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/pagamentos")
                .contentType(APPLICATION_JSON)
                .content(request);

        mockMvc.perform(requestBuilder)
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );

    }
}