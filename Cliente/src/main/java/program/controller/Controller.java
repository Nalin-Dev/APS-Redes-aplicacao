package main.java.program.controller;

import main.java.program.entities.Cliente;
import main.java.program.utils.InfoMensagem;
import main.java.program.utils.JsonParserToMap;
import main.java.program.view.Tela;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Logger;

public class Controller {

    private final Cliente cliente;
    private final Tela tela;
    private boolean mensagemRecebida = true;
    private String ultimoRemetente = "";

    public Controller(Cliente cliente, Tela tela) {
        this.cliente = cliente;
        this.tela = tela;
    }

    /***
     * Método usado para conectar no server socket, retorna IO Exception caso dê algum erro.
     * @throws IOException
     */
    public void conectar(final String ip, final Integer porta) throws IOException{
        final Socket socket = new Socket(ip, porta);
        cliente.setSocket(socket);
        cliente.setOu(socket.getOutputStream());
        cliente.setOuw(new OutputStreamWriter(cliente.getOu()));
        cliente.setBfw(new BufferedWriter(cliente.getOuw()));
        tela.getCaixaDeTexto().append("SISTEMA: " + cliente.getNomeID() +" entrou na sala!\r\n");
        Logger.getLogger(Controller.class.getName()).info("Entrando na sala...");
        cliente.getBfw().write(cliente.getNomeID() + "\r\n");
        cliente.getBfw().flush();
    }

    /***
     * Método usado para enviar mensagem para o server socket
     * @param msg do tipo String
     * @throws IOException retorna IO Exception caso dê algum erro.
     */
    public void enviarMensagem(String msg) throws IOException{
        Logger.getLogger(Controller.class.getName()).info("Mensagem enviada: " + msg);
        if("Sair".equals(msg)){
            cliente.getBfw().write("Desconectado\r\n");
            tela.getCaixaDeTexto().append("Desconectado \r\n");
        } else if (!"".equals(msg)) {
            cliente.getBfw().write(msg+"\r\n");
            ultimoRemetente = "";

            if (!mensagemRecebida) {
                tela.getCaixaDeTexto().append(tela.getMensagem() + "\r\n");
            } else {
                tela.getCaixaDeTexto().append(new InfoMensagem(tela.getMensagem()).getMensgem());
                mensagemRecebida = false;
            }
        }

        cliente.getBfw().flush();
        tela.limpaMensagem();
    }

    /**
     * Método usado para receber mensagem do servidor
     * @throws IOException retorna IO Exception caso dê algum erro.
     */
    public void escutar() throws IOException {
        final InputStream in = cliente.getSocket().getInputStream();
        final InputStreamReader inr = new InputStreamReader(in);
        final BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while(!"Sair".equalsIgnoreCase(msg)) {

            if(bfr.ready()) {
                msg = bfr.readLine();

                Logger.getLogger(Controller.class.getName()).info("Mensagem recebida: " + msg);

                if (!"".equals(msg)) {
                    final Map<String, Object> json = JsonParserToMap.parse(msg);

                    mensagemRecebida = json.containsKey("mensagem");

                    final String nomeRemetente = (String) json.get("id");
                    final String nome = nomeRemetente.split("#")[0];

                    if (mensagemRecebida) {
                        final String mensagem = (String) json.get("mensagem");

                        if ("".equals(ultimoRemetente) || !ultimoRemetente.equals(nomeRemetente)) {
                            ultimoRemetente = nomeRemetente;
                            tela.getCaixaDeTexto().append(String.format("\n[ %s ] te enviou:\n %s\r\n", nome, mensagem));
                        } else {
                            tela.getCaixaDeTexto().append(mensagem + "\r\n");
                        }
                    } else if (json.containsKey("sistema")) {

                        final String sistema = (String) json.get("sistema");

                        if ("Desconectado".equalsIgnoreCase(sistema)) {
                            tela.getCaixaDeTexto().append(String.format("\nSISTEMA: [ %s ]  se desconectou.\r\n", nome));
                        } else if ("Entrada".equalsIgnoreCase(sistema)) {
                            tela.getCaixaDeTexto().append(String.format("\nSISTEMA: [ %s ]  entrou na sala.\r\n", nome));
                        }
                    }
                }
            }
        }
    }

    /***
     * Método usado quando o usuário clica em sair
     * @throws IOException retorna IO Exception caso dê algum erro.
     */
    public void sair() throws IOException{

        enviarMensagem("Sair");
        cliente.getBfw().close();
        cliente.getOuw().close();
        cliente.getOu().close();
        cliente.getSocket().close();
    }
}
