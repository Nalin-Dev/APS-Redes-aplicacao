package main.java.program.helpers;


public class ValidaNumero implements IValidador {

    private final static String REGEX = "[0-9]+";

    @Override
    public boolean isValido(final String valor) {
        return valor.matches(REGEX);
    }
}
