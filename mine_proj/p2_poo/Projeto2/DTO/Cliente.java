package dataBaseReference.DTO;

import java.util.HashMap;
import java.util.Map;

public class Cliente {
    private Map<Integer, Customer> clientes;

    public Cliente() {
        clientes = new HashMap<>();
    }

    public void inserirCliente(int id, Customer cliente) {
        clientes.put(id, cliente);
    }

    public Customer consultarClientePorId(int id) {
        return clientes.get(id);
    }

    public Customer consultarClientePorNome(String nome) {
        for (Customer cliente : clientes.values()) {
            if (cliente.getName().equalsIgnoreCase(nome)) {
                return cliente;
            }
        }
        return null;
    }

    public Customer apagarCliente(int id) {
        return clientes.remove(id);
    }
}
