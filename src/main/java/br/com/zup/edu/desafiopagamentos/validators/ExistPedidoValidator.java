package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pedidos.clients.PedidoClient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistPedidoValidator implements ConstraintValidator<ExistPedido,Long> {
    private final PedidoClient client;

    public ExistPedidoValidator(PedidoClient client) {
        this.client = client;
    }


    @Override
    public boolean isValid(Long pedido, ConstraintValidatorContext constraintValidatorContext) {
        try{
            var response=client.consultarPedido(pedido);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
