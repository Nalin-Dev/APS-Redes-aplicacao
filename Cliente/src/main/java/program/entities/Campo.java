package main.java.program.entities;

import main.java.program.helpers.IValidador;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Campo {

    private final JTextField campo;
    private final String mensagemErro;
    private String primeiroValor;
    private IValidador validador;

    public Campo(final JTextField campo,final String mensagemErro) {
        this.campo = campo;
        this.mensagemErro = mensagemErro;
        config();
    }

    public Campo(final JTextField campo, final String mensagemErro, final IValidador validador) {
        this.campo = campo;
        this.mensagemErro = mensagemErro;
        this.validador = validador;
        config();
    }

    private void config() {
        if (campo.getText() != null && !campo.getText().isEmpty()) {
            primeiroValor = campo.getText();
        }

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                campoFocusGained(e);
            }

            public void focusLost(FocusEvent e) {
                campofocusLost(e);
            }
        });
    }

    private void campoFocusGained(FocusEvent evt) {
        campo.setText("");
    }

    private void campofocusLost(FocusEvent evt) {
        if (campo.getText() == null || campo.getText().isEmpty() && !primeiroValor.isEmpty()) {
            campo.setText(primeiroValor);
        }
    }

    public boolean isValido() {
        if (campo.getText() == null || campo.getText().isEmpty()) {
            return false;
        }

        if (validador == null) {
            return true;
        }

        return validador.isValido(campo.getText());
    }

    public JTextField getCampo() {
        return campo;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }
}
