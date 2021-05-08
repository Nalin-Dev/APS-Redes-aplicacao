package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor extends Thread {

    private static ArrayList<BufferedWriter> clientes = new ArrayList<>();
    private String nome;
    private Socket con;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;

    /**
     * Método construtor
     * @param com do tipo Socket
     */
    public Servidor(Socket con){
        this.con = con;
        try {
            in  = con.getInputStream();
            inr = new InputStreamReader(in);
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

        }catch (Exception e) {
            e.printStackTrace();

        }
    }

    /***
     * Método usado para enviar mensagem para todos os clients
     * @param bwSaida do tipo BufferedWriter
     * @param msg do tipo String
     * @throws IOException
     */
    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException
    {
        BufferedWriter bwS;

        for(BufferedWriter bw : clientes){
            bwS = (BufferedWriter)bw;
            if(!(bwSaida == bwS)){
                bw.write(nome + " -> " + msg+"\r\n");
                bw.flush();
            }
        }
    }
}
