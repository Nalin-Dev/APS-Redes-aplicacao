package main.java.programa.server;

import main.java.programa.entities.Cliente;
import main.java.programa.utils.JsonToStringBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BroadCast {

    private static final ArrayList<Cliente> clientes = new ArrayList<>();

    public BroadCast() {
    }

    public void adicionarClient(final Cliente cliente) throws IOException {
        clientes.add(cliente);
        enviarMensagemSistemaParaTodos(cliente, "entrada");
    }
    public void removerClient(final Cliente cliente) {
        clientes.remove(cliente);
    }


    /***
     * Método usado para enviar mensagem para todos os clients
     * @param msg do tipo String
     * @throws IOException
     */
    public void enviarMensagemParaTodos(final Cliente cliente, String msg) throws  IOException
    {
        final String mensagem = new JsonToStringBuilder()
                .novaPropriedade("id", cliente.getNomeId())
                .novaPropriedade("mensagem", msg)
                .build();

        enviar(cliente, mensagem);
    }

    /***
     * Método usado para enviar uma mensagem de sistema para todos os clients
     * @param msg do tipo String
     * @throws IOException
     */
    public void enviarMensagemSistemaParaTodos(final Cliente cliente, String msg) throws  IOException
    {
        final String mensagem = new JsonToStringBuilder()
                .novaPropriedade("id", cliente.getNomeId())
                .novaPropriedade("sistema", msg)
                .novaPropriedade("mensagem", msg)
                .build();

        enviar(cliente, mensagem);
    }


    private void enviar(final Cliente cliente, String msg) throws IOException {
        final List<Cliente> clienteList = clientes.stream()
                .filter(c -> !(c.getNomeId().equalsIgnoreCase(cliente.getNomeId()))).collect(Collectors.toList());

        for(Cliente c : clienteList) {

            c.getBufferedWriter().write(msg +"\r\n");
            c.getBufferedWriter().flush();
        }
    }
}
