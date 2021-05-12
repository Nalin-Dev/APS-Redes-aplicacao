package main.java.programa.entities;

import java.io.BufferedWriter;

public class Cliente {
    private final BufferedWriter bufferedWriter;
    private final String nomeId;

    public Cliente(BufferedWriter bufferedWriter, String nomeId) {
        this.bufferedWriter = bufferedWriter;
        this.nomeId = nomeId;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public String getNomeId() {
        return nomeId;
    }

    public String getNome() {
        return nomeId.split("#")[0];
    }
}
