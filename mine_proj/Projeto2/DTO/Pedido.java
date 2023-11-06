package dataBaseReference.DTO;

import java.util.HashMap;
import java.util.Map;

public class Pedido {
    private Map <Integer, Orders> pedidos;
    
    public Pedido(){
        pedidos = new HashMap<>();
    }
    
    //inserir pedido
    public void inserirPedido(int number, Orders pedido){

        pedidos.put(number, pedido);
    }
    //consultar pedido
    public Orders consultarPedido(int number){
        return pedidos.get(number);
    }

    //apagar pedido

    public Orders apagarpedido(int number){
        return pedidos.remove(number);
    }
}
