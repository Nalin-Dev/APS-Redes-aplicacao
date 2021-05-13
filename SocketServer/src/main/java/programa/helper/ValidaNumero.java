package main.java.programa.helper;

/**
 * <p> A classe {@code ValidaNumero}, responsavel por validar um numero.
 */
public class ValidaNumero {

    private Integer numero;

    /**
     * <p> Metodo que realiza validacao.
     * @param valor {@code String}
     * @return {@code ValidaNumero}
     */
    public ValidaNumero validar(final String valor) {
        try {
            numero = Integer.parseInt(valor);
        } catch (RuntimeException e) {
            numero = null;
        }
        return this;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getStringNumero() {
        return String.valueOf(numero);
    }
}
