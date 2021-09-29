package br.com.zup.edu.desafiopagamentos.pedidos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/pedidos/{idPedido}")
public class ConsultarPedidoController {

    private final EntityManager manager;

    public ConsultarPedidoController(EntityManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public ResponseEntity<?> consultar(@PathVariable Long idPedido) {

        Pedido pedido = manager.find(Pedido.class, idPedido);

        if(Objects.isNull(pedido)){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of("valor", pedido.getValor()));
    }
}
