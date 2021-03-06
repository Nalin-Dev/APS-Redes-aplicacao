package main.java.program.entities;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cliente  {

    private final String nome;
    private final String nomeID;
    private final String regiao;
    private Socket socket;
    private OutputStream ou ;
    private Writer ouw;
    private BufferedWriter bfw;

    public Cliente(final String nome, final String regiao) throws IOException {
        this.nome = nome;
        nomeID = setNomeID(nome);
        this.regiao = regiao;
    }

    private String setNomeID(final String nome) {
        final Date date = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmmss");
        return nome + "#" + formatter.format(date);
    }

    public String getNomeID() {
        return nomeID;
    }

    public String getRegiao() {
        return regiao;
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
