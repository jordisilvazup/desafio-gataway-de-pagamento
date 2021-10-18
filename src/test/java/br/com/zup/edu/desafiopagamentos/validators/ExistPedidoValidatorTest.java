package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pedidos.clients.PedidoClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExistPedidoValidatorTest {
    private PedidoClient client;
    @Mock
    private ConstraintValidatorContext ctx;
    private ExistPedidoValidator validator;

    @BeforeEach
    void setUp() {
        this.client = Mockito.mock(PedidoClient.class);
        this.validator = new ExistPedidoValidator(client);
    }

    @Test
    void deveExistirPedido() {
        Long idPedido = 1L;

        ResponseEntity<Map<String, BigDecimal>> response = ResponseEntity.ok(Map.of("valor", new BigDecimal("1.0")));

        Mockito.when(client.consultarPedido(idPedido)).thenReturn(response);

        boolean valid = validator.isValid(idPedido, ctx);

        assertTrue(valid);
    }
    @Test
    void naoDeveExistirPedido() {
        Long idPedido = 100L;


        Mockito.when(client.consultarPedido(idPedido)).thenThrow(FeignException.class);
        boolean notValid = validator.isValid(idPedido, ctx);

        assertFalse(notValid);
    }

}
