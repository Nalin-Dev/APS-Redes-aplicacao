package main.java.program.entities;

import java.io.Serializable;


public class Arquivo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private byte[] conteudo;
    private transient long tamanhoKB;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public byte[] getConteudo() {
        return conteudo;
    }
    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }
    public long getTamanhoKB() {
        return tamanhoKB;
    }
    public void setTamanhoKB(long tamanhoKB) {
        this.tamanhoKB = tamanhoKB;
    }

}