package server;

import utils.InfoMensagem;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {

    private static final ArrayList<BufferedWriter> clientes = new ArrayList<>();
    private String nome;
    private final Socket con;
    private BufferedReader bfr;

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

        try{
            String msg;
            OutputStream ou =  this.con.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);
            clientes.add(bfw);
            nome = msg = bfr.readLine();

            while(!"Sair".equalsIgnoreCase(msg) && msg != null)
            {
                msg = bfr.readLine();
                sendToAll(bfw, msg);
                System.out.println(msg);
            }

        }
        catch (Exception e) {
            if (e instanceof SocketException) {
                Logger.getLogger(Servidor.class.getName()).log(Level.INFO, String.format("%s saiu da chamada!", nome));
            } else {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "Erro genérico", e);
            }
        }
    }

    /***
     * Método usado para enviar mensagem para todos os clients
     * @param bwSaida do tipo BufferedWriter
     * @param msg do tipo String
     * @throws IOException
     */
    public void sendToAll(final BufferedWriter bwSaida, String msg) throws  IOException
    {
        BufferedWriter bwS;

        for(BufferedWriter bw : clientes){
            bwS = bw;
            if(!(bwSaida == bwS)){
                bw.write(new InfoMensagem(nome, msg).getMensgem());
                bw.flush();
            }
        }
    }
}
