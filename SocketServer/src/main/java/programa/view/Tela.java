package main.java.programa.view;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

/**
 * A class {@code Tela}, responsavel por realizar interação com usuario.
 * @see JTextField
 * @see JOptionPane
 */
public class Tela {

    private final JTextField campoPorta;
    private int selecao;

    public Tela() {
        UIManager.put("OptionPane.minimumSize",new Dimension(350,200));
        campoPorta = new JTextField("4100");
        campoPorta.setFocusable(true);
        campoPorta.setMaximumSize(new Dimension(25, 25));
        chamarTela();
    }


    public void reApresentar() {
        final String infoErro = "Por favor, entre com uma porta válida.";
        JOptionPane.showMessageDialog(null, infoErro);
        chamarTela();
    }

    private void chamarTela() {
        final JLabel lblMessage = new JLabel("Numero da Porta: \n");
        selecao = JOptionPane.showOptionDialog(
                null,
                new Object[]{lblMessage,
                        campoPorta},
                "Servidor",
                OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
    }

    public String getCampoPorta() {
        return campoPorta.getText();
    }

    public void chamarFimPrograma() {
        JOptionPane.showMessageDialog(null, "Você saiu do progrma!");
    }

    public boolean getSair() {
        return selecao == JOptionPane.CANCEL_OPTION || selecao == JOptionPane.CLOSED_OPTION;
    }
}
