package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.FormasDePagamentoEmComumRequest;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
class ConsultarFormasDePagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @PersistenceContext
    private EntityManager manager;

    @Test
    void deveRetornarAsFormasDePagamentoComumEntreUmRestauranteEUsuario() throws Exception {

        FormasDePagamentoEmComumRequest requestObject = new FormasDePagamentoEmComumRequest(1L, 1L);

        String request = mapper.writeValueAsString(requestObject);

        final String URI = "/api/v1/forma-de-pagamento";

        MockHttpServletRequestBuilder consultaRequest = get(URI).contentType(APPLICATION_JSON).content(request);

        String response = mapper.writeValueAsString(formasDePagamentoEmComum());


        mockMvc.perform(consultaRequest)
                .andExpect(
                        status().isOk()
                ).andExpect(
                        content().json(response)
                );

    }

    @Transactional
    List<FormasDePagamentoResponse> formasDePagamentoEmComum() {

        List<FormaDePagamento> formas = new ArrayList<>();

        formas.add(manager.find(FormaDePagamento.class, 1L));
        formas.add(manager.find(FormaDePagamento.class, 2L));
        formas.add(manager.find(FormaDePagamento.class, 4L));

        return formas.stream().map(FormasDePagamentoResponse::new).collect(Collectors.toList());
    }

}