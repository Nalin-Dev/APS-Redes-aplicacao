package program.controller;

import program.utils.InfoMensagem;
import main.Cliente;
import program.view.Tela;

import java.io.*;
import java.net.Socket;

public class Controller {

    private final Cliente cliente;
    private final Tela tela;

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
        cliente.getBfw().write(cliente.getNome() +"\r\n");
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
        }else{
            cliente.getBfw().write(msg+"\r\n");
            tela.getCaixaDeTexto().append(new InfoMensagem(cliente.getNome(), tela.getMensagem()).getMensgem());
        }
        cliente.getBfw().flush();
        tela.limpaMensagem();
    }

    /**
     * Método usado para receber mensagem do servidor
     * @throws IOException retorna IO Exception caso dê algum erro.
     */
    public void escutar() throws IOException{
        InputStream in = cliente.getSocket().getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while(!"Sair".equalsIgnoreCase(msg))

            if(bfr.ready()){
                msg = bfr.readLine();
                if(msg.equals("Sair"))
                    tela.getCaixaDeTexto().append("Servidor caiu! \r\n");
                else
                    tela.getCaixaDeTexto().append(msg+"\r\n");
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
