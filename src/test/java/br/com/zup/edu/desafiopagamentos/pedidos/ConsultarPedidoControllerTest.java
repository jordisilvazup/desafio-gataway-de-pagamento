package br.com.zup.edu.desafiopagamentos.pedidos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class ConsultarPedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final Long ID_PEDIDO=1L;

    @Test
    void deveRetornaUmPedido() throws Exception {


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/pedidos/"+ID_PEDIDO);

        mockMvc.perform(requestBuilder)
                .andExpect(
                        status().isOk()
                );

    }

}