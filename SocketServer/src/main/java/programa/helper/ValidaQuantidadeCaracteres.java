package main.java.programa.helper;

/**
 * <p>A classe {@code ValidaQuantidadeCaracteres}, responsavel por validar tamanho de um caracter/string.
 */
public class ValidaQuantidadeCaracteres {

    private final String valor;
    private Boolean valido = Boolean.TRUE;

    public ValidaQuantidadeCaracteres(final String valor) {
        this.valor = valor;
    }

    /**
     * Metodo que valida tamanho maximo de um caracter/string.
     * @param max
     * @return {@code ValidaQuantidadeCaracteres}
     */
    public ValidaQuantidadeCaracteres maximo(final Integer max) {
        if (valido) {
            valido = valor.length() <= max;
        }
        return this;
    }

    /**
     * Metodo que valida tamanho minimo de um caracter/string.
     * @param min
     * @return {@code ValidaQuantidadeCaracteres}
     */
    public ValidaQuantidadeCaracteres minimo(final Integer min) {
        if (valido) {
            valido = valor.length() >= min;
        }
        return this;
    }

    /**
     * <p> Metodo que retorna resultado da validacao
     * @return {@code Boolean}
     */
    public Boolean isValido() {
        return valido;
    }
}
