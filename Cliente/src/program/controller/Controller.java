package program.controller;

import program.utils.InfoMensagem;
import program.entities.Cliente;
import program.view.Tela;

import java.io.*;
import java.net.Socket;

public class Controller {

    private final Cliente cliente;
    private final Tela tela;
    private boolean mensagemRecebida = true;
    private String ultimaMensagem = "";

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
        tela.getCaixaDeTexto().append("Você entrou na sala!\r\n");
        cliente.getBfw().write(cliente.getNomeID() + "\r\n");
        cliente.getBfw().flush();
    }

    /***
     * Método usado para enviar mensagem para o server socket
     * @param msg do tipo String
     * @throws IOException retorna IO Exception caso dê algum erro.
     */
    public void enviarMensagem(String msg) throws IOException{

        if(msg.equals("Sair")){
            cliente.getBfw().write("Desconectado \r\n");
            tela.getCaixaDeTexto().append("Desconectado \r\n");

        } else if (!"".equals(msg)) {

            cliente.getBfw().write(msg+"\r\n");
            ultimaMensagem = "";

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
        InputStream in = cliente.getSocket().getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while(!"Sair".equalsIgnoreCase(msg)) {

            if(bfr.ready()) {
                msg = bfr.readLine();

                mensagemRecebida = msg != null;

                if (!"".equals(msg)) {

                    final String nomeRemetente = msg.contains("#") ? msg.split("-")[0] : "";
                    final String info = msg.contains("#") ? msg.split("-")[1] : "";
                    final String nome = nomeRemetente.split("#")[0];

                    if (!"".equals(nomeRemetente)) {
                        if ("".equals(ultimaMensagem) || !ultimaMensagem.equals(nomeRemetente)) {
                            ultimaMensagem = nomeRemetente;
                            tela.getCaixaDeTexto().append(String.format("\n[ %s ] - %s\r\n", nome, info));
                        }
                    } else {
                        tela.getCaixaDeTexto().append(msg + "\r\n");
                    }
                }

                if(msg.equals("Sair")) {
                    tela.getCaixaDeTexto().append("Servidor caiu! \r\n");
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
        cliente.getBfw().close();
        cliente.getOu().close();
        cliente.getSocket().close();
    }
}
