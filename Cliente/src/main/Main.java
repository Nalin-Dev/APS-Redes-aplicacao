package main;


import view.Cliente;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Cliente app = new Cliente();
        app.conectar();
        app.escutar();

    }// Fim do método main
} //Fim da classe