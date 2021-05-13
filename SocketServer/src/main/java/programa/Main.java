package main.java.programa;

import main.java.programa.entities.Porta;
import main.java.programa.server.ControladorServidor;
import main.java.programa.view.Tela;

public class Main {

    /**
     * <p> Inicializacao da applicacao.
     */
    public static void main(String[] args) {
        final Tela tela = new Tela();
        Porta porta = new Porta(tela.getCampoPorta());

        while (porta.getValor().isEmpty() && !tela.getSair()) {
            tela.reApresentar();
            porta = new Porta(tela.getCampoPorta());
        }

        if (!tela.getSair()) {
            new ControladorServidor(porta.getValor().get());
        }
        tela.chamarFimPrograma();
    }
}