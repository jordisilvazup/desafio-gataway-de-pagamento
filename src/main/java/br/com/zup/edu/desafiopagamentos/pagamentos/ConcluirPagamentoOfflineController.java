package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.util.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ConcluirPagamentoOfflineController {
    private final PagamentoRepository repository;

    public ConcluirPagamentoOfflineController(PagamentoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/api/v1/pagamentos/{codigoDoPagamento}")
    public ResponseEntity<?> concluir(@PathVariable String codigoDoPagamento){
        ResponseError responseError = new ResponseError();

        Pagamento pagamento = repository.findByCodigoParaConfirmacaoDePagamento(codigoDoPagamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!pagamento.existeTransacaoIniciada()){
            responseError.adicionarError("Pagamento","não existe transação iniciada para este pedido.");
            return ResponseEntity.badRequest().body(responseError);
        }

        if (pagamento.concluido()){
            responseError.adicionarError("Pagamento","este pedido já foi pago.");
            return ResponseEntity.badRequest().body(responseError);
        }

        pagamento.concluir();

        repository.save(pagamento);


        return ResponseEntity.ok().build();
    }
}
