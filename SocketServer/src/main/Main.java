package main;

import server.Servidor;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try{
            final ServerSocket server = new ServerSocket(12345);

            while(true){
                System.out.println("Aguardando conexão...");
                Socket con = server.accept();
                System.out.println("Cliente conectado...");
                Thread t = new Servidor(con);
                t.start();
            }
        }catch (Exception e) {

            e.printStackTrace();
        }
    }// Fim do método main
} //Fim da classe