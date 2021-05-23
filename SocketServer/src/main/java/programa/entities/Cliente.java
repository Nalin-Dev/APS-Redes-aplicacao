package main.java.programa.entities;

import java.io.BufferedWriter;


/**
 * <p> Classe modelo de um cliente conectado na rede.
 * @see BufferedWriter
 */
public class Cliente {
    private final BufferedWriter bufferedWriter;
    private final String nomeId;
    private final String regiao;

    public Cliente(BufferedWriter bufferedWriter, String nomeId, String regiao) {
        this.bufferedWriter = bufferedWriter;
        this.nomeId = nomeId;
        this.regiao = regiao;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public String getNomeId() {
        return nomeId;
    }

    public String getRegiao() {
        return regiao;
    }

    /**
     * <p> Metodo que retorna nome do cliente.
     * @return {@code String}
     */
    public String getNome() {
        return nomeId.split("#")[0];
    }
}
