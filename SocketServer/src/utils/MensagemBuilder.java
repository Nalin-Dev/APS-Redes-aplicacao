package utils;

public class MensagemBuilder {

    private final StringBuilder mensagem = new StringBuilder();

    public MensagemBuilder escreverNovaLinha(final String novaMensagem) {
        mensagem.append("\n").append(novaMensagem);
        return this;
    }

    public MensagemBuilder escreverNaMesmaLinha(final String novaMensagem) {
        mensagem.append(novaMensagem);
        return this;
    }

    public MensagemBuilder pularNumeroDeLinhas(final int numeroDeLinhas) {
        int contador = numeroDeLinhas;
        do {
            mensagem.append("\n");
            contador--;
        } while (contador > 0);

        return this;
    }

    public MensagemBuilder pularLinha() {
        mensagem.append("\n");
        return this;
    }

    public String build() {
        return mensagem.toString();
    }
}
