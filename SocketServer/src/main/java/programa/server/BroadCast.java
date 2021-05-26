package main.java.programa.server;

import main.java.programa.entities.Cliente;
import main.java.programa.entities.ClienteFile;
import main.java.programa.utils.JsonToStringBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BroadCast {

    private static final ArrayList<Cliente> clientes = new ArrayList<>();
    private static final ArrayList<ClienteFile> clientesFile = new ArrayList<>();

    public BroadCast() {
    }

    /***
     * Método responsavel por adicionar um cliente na lista de clientes
     * @param cliente do tipo Cliente
     */
    public void adicionarClient(final Cliente cliente) throws IOException {
        clientes.add(cliente);
        Logger.getLogger(BroadCast.class.getName()).info(cliente.getNomeId() + " se conectou...");
        enviarMensagemSistemaParaTodos(cliente, "entrada");
    }

    public void adicionarClientFile(final ClienteFile cliente) throws IOException {
        clientesFile.add(cliente);
    }

    /***
     * Método responsavel por remover um cliente desconectado da lista de clientes
     * @param cliente do tipo Cliente
     */
    public void removerClient(final Cliente cliente) throws IOException {
        clientesFile.get(clientes.indexOf(cliente)).getBufferedOutputStream().close();
        clientesFile.remove(clientes.indexOf(cliente));
        clientes.remove(cliente);
        Logger.getLogger(BroadCast.class.getName()).info(cliente.getNomeId() + " se desconectou...");
        Logger.getLogger(BroadCast.class.getName()).info("Numero de clientes ainda conectados: " + clientes.size());
    }

    /***
     * Método responsavel por enviar mensagem para todos os clients
     * @param msg do tipo String
     * @param cliente do tipo Cliente
     */
    public void enviarMensagemParaTodos(final Cliente cliente, String msg) throws  IOException {
        final String mensagem = new JsonToStringBuilder()
                .novaPropriedade("id", cliente.getNomeId())
                .novaPropriedade("mensagem", msg)
                .novaPropriedade("regiao", cliente.getRegiao())
                .build();

        enviar(cliente, mensagem);
    }

    public void enviarFileParaTodos(ClienteFile cliente, byte[] msg) throws  IOException {
        if (!close(cliente)) {
            final List<ClienteFile> clienteList = clientesFile.stream()
                    .filter(c -> !(c.getBufferedOutputStream() == cliente.getBufferedOutputStream())).collect(Collectors.toList());

            for(ClienteFile  c : clienteList) {
                Logger.getLogger(BroadCast.class.getName()).info( " enviou arquivo para todos: ");
                c.getBufferedOutputStream().write(msg);
                c.getBufferedOutputStream().flush();
            };
        }

    }

    /***
     * Método responsavel por enviar mensagem de sistema para todos os clientes
     * @param cliente {@code Cliente}
     * @param msg {@code String}
     */
    public void enviarMensagemSistemaParaTodos(final Cliente cliente, String msg) throws  IOException {
        final String mensagem = new JsonToStringBuilder()
                .novaPropriedade("id", cliente.getNomeId())
                .novaPropriedade("sistema", msg)
                .novaPropriedade("regiao", cliente.getRegiao())
                .build();

        enviar(cliente, mensagem);
    }

    /***
     * Método responsavel por executar enviar
     * @param cliente {@code Cliente}
     * @param msg {@code String}
     */
    private void enviar(final Cliente cliente, String msg) throws IOException {
        final List<Cliente> clienteList = clientes.stream()
                .filter(c -> !(c.getNomeId().equalsIgnoreCase(cliente.getNomeId()))).collect(Collectors.toList());

        if (clienteList.isEmpty() && msg.contains("id")) {
            Logger.getLogger(BroadCast.class.getName()).warning(cliente.getNomeId() + " está sozinho na sala.");
            Logger.getLogger(BroadCast.class.getName()).warning("Mensagem enviada a todos: " + msg);
        } else {
            for(Cliente c : clienteList) {
                Logger.getLogger(BroadCast.class.getName()).info(cliente.getNomeId() + " enviou a todos: " + msg);
                c.getBufferedWriter().write(msg +"\r\n");
                c.getBufferedWriter().flush();
            }
        }
    }

    public boolean close(ClienteFile cliente) {
        return !clientesFile.contains(cliente);
    }
}
