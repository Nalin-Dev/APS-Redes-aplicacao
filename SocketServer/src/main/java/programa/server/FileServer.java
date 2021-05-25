package main.java.programa.server;


import main.java.programa.entities.Cliente;
import main.java.programa.entities.ClienteFile;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileServer extends Thread{
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
            BufferedInputStream bfi = new BufferedInputStream(
                        con.getInputStream());
            bfi.read(objectAsByte);


            ClienteFile clienteFile = new ClienteFile(bf);
            broadCast.adicionarClientFile(clienteFile);
            while(true)
            {
                broadCast.enviarFileParaTodos(objectAsByte);
                bfi.read(objectAsByte);
            }

        } catch (Exception e) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "Erro genérico", e);
        }
    }
}
