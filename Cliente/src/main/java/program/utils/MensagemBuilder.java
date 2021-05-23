package main.java.program.utils;

import java.nio.charset.StandardCharsets;

public class MensagemBuilder {

    private final StringBuilder mensagem = new StringBuilder();

    public MensagemBuilder escreverNovaLinha(final String novaMensagem) {
        mensagem.append("\n").append(novaMensagem);
        return this;
    }

    public boolean naoVazio() {
        return mensagem.length() > 0;
    }

    public String build() {
        return mensagem.toString();
    }
}
