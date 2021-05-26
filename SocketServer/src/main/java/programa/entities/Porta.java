package main.java.programa.entities;

import main.java.programa.helper.ValidaNumero;
import main.java.programa.helper.ValidaQuantidadeCaracteres;

import java.util.Optional;

/**
 * <p> A classe {@code Porta} representa uma porta de servidor valida.
 */
public class Porta {
    private final Integer valor;
    private final static int MAX_CARACTERES = 4;
    private final static int MIN_CARACTERES = 4;

    /**
     * @param digito {@code String} recebe valor a ser validado
     */
    public Porta(final String digito) {
        final Boolean isValido = new ValidaQuantidadeCaracteres(digito)
                .maximo(MAX_CARACTERES)
                .minimo(MIN_CARACTERES)
                .isValido();

        this.valor = isValido ? new ValidaNumero().validar(digito).getNumero() : null;
    }

    /**
     * <p> Metodo que retorna numeracao valida
     * @return {@code Optional<Integer>}
     */
    public Optional<Integer> getValor() {
        return Optional.ofNullable(valor) ;
    }
}
