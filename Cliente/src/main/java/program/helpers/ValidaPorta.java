package main.java.program.helpers;

public class ValidaPorta implements IValidador {

    @Override
    public boolean isValido(String valor) {
        return new ValidaNumero().isValido(valor);
    }
}
