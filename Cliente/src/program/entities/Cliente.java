package program.entities;

import java.io.*;
import java.net.Socket;

public class Cliente  {

    private String nome;
    private Socket socket;
    private OutputStream ou ;
    private Writer ouw;
    private BufferedWriter bfw;

    public Cliente(final String nome) throws IOException {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setOu(OutputStream ou) {
        this.ou = ou;
    }

    public void setOuw(Writer ouw) {
        this.ouw = ouw;
    }

    public void setBfw(BufferedWriter bfw) {
        this.bfw = bfw;
    }

    public Socket getSocket() {
        return socket;
    }

    public OutputStream getOu() {
        return ou;
    }

    public Writer getOuw() {
        return ouw;
    }

    public BufferedWriter getBfw() {
        return bfw;
    }
}
