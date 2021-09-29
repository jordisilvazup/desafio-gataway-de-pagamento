package br.com.zup.edu.desafiopagamentos.pedidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "pedidosClient",url = "${pedido.api.url}")
public interface PedidoClient {
    @GetMapping("/{idPedido}")
    ResponseEntity<Map<String, BigDecimal>> consultarPedido(@PathVariable Long idPedido);


}
