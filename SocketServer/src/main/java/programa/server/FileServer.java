package main.java.programa.server;

import main.java.programa.entities.ClienteFile;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileServer extends Thread {
    private final Socket con;
    private  BufferedOutputStream bf;
    private final BroadCast broadCast = new BroadCast();

    public FileServer(final Socket conexao){
        this.con = conexao;
        try {
            this.bf = new BufferedOutputStream
                    (con.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

        try{
            byte[] objectAsByte = new byte[con.getReceiveBufferSize()];
            BufferedInputStream bfi = new BufferedInputStream(con.getInputStream());


            ClienteFile clienteFile = new ClienteFile(bf);
            broadCast.adicionarClientFile(clienteFile);
            while(!broadCast.close(clienteFile))
            {
                broadCast.enviarFileParaTodos(clienteFile, objectAsByte);
                if (!broadCast.close(clienteFile))
                    bfi.read(objectAsByte);
            }

            bfi.close();
            bf.close();

        } catch (SocketException e) {
        } catch (Exception e) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "Erro genérico", e);
        }
    }
}
