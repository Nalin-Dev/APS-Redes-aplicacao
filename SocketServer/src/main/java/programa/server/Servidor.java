package main.java.programa.server;

import main.java.programa.entities.Cliente;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {

    private final Socket con;
    private BufferedReader bfr;
    private final BroadCast broadCast = new BroadCast();

    /**
     * Método construtor
     * @param con do tipo Socket
     */
    public Servidor(Socket con){
        this.con = con;
        try {
            InputStream in = con.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método run
     */
    public void run(){

        Cliente cliente;

        try{
            String msg;
            final OutputStream ou =  this.con.getOutputStream();
            final Writer ouw = new OutputStreamWriter(ou);
            final BufferedWriter bfw = new BufferedWriter(ouw);

            msg = bfr.readLine();

            final String nomeId = msg;

            cliente = new Cliente(bfw, nomeId);

            broadCast.adicionarClient(cliente);

            while(!"Sair".equalsIgnoreCase(msg) && !"Desconectado".equalsIgnoreCase(msg) && msg != null)
            {
                msg = bfr.readLine();
                if (msg != null && !"Desconectado".equalsIgnoreCase(msg))
                broadCast.enviarMensagemParaTodos(cliente, msg);
                System.out.println(msg);
            }

            if (msg != null && "Desconectado".equalsIgnoreCase(msg.trim())) {
                broadCast.enviarMensagemSistemaParaTodos(cliente, msg.trim());
            }

            ou.close();
            ouw.close();
            bfw.close();

            broadCast.removerClient(cliente);
        } catch (Exception e) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "Erro genérico", e);
        }
    }
}
