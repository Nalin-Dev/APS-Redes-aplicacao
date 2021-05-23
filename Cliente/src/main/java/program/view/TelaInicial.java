package main.java.program.view;

import main.java.program.entities.Campo;
import main.java.program.helpers.ValidaIP;
import main.java.program.helpers.ValidaPorta;
import main.java.program.utils.MensagemBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

/**
 * A class {@code Tela}, responsavel por realizar interação com usuario.
 *
 * @see JTextField
 * @see JOptionPane
 */
public class TelaInicial extends JFrame {

    private final static String VAL_INIT_CAMPO_NOME = "Nome do cliente...";
    private final static String VAL_INIT_CAMPO_IP = "127.0.0.1";
    private final static String VAL_INIT_CAMPO_PORTA = "4100";
    private final Campo campoPorta;
    private final Campo campoIP;
    private final Campo campoNomeCliente;
    private final JLabel lblMessage;
    private final List<Object> componentes = new ArrayList<>();
    private final JComboBox<String> regiao = new JComboBox<>();
    private MensagemBuilder mensagemErro = new MensagemBuilder();
    private int selecao;

    public TelaInicial() {
        regiao.addItem("Salesópolis");
        regiao.addItem("Grande São Paulo");
        regiao.setSelectedIndex(0);

        this.campoPorta = new Campo(new JTextField(VAL_INIT_CAMPO_PORTA), "Campo Porta: contém valores invalidos", new ValidaPorta());
        this.campoIP = new Campo(new JTextField(VAL_INIT_CAMPO_IP), "Campo IP: contém valores invalidos", new ValidaIP());
        this.campoNomeCliente = new Campo(new JTextField(VAL_INIT_CAMPO_NOME), "Campo nome cliente: contém valores invalidos");
        this.lblMessage = new JLabel("Dados de entrada!");
        componentes.addAll(Arrays.asList(this.campoPorta, this.campoIP, this.campoNomeCliente, regiao));
        chamarTela();
    }



    public void reApresentar() {
        UIManager.put("OptionPane.minimumSize", new Dimension(350, 300));
        JOptionPane.showMessageDialog(null, mensagemErro.build());
        chamarTela();
        mensagemErro = new MensagemBuilder();
    }

    private void chamarTela() {
        UIManager.put("OptionPane.minimumSize", new Dimension(250, 200));
        selecao = JOptionPane.showOptionDialog(
                null,
                Arrays.asList(lblMessage,
                        this.campoPorta.getCampo(),
                        this.campoIP.getCampo(),
                        this.campoNomeCliente.getCampo(),
                        regiao).toArray(),
                "Entrada Chat",
                OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
    }

    public boolean existeCamposInvalidos() {
        final List<String> erros = new ArrayList<>();

        componentes.stream().filter(item -> item instanceof Campo).forEach(cp -> {
            final Campo campo = (Campo) cp;
            if (!campo.isValido()) erros.add(campo.getMensagemErro());
        });

        if (!erros.isEmpty()) {
            erros.add(0, "Por favor, existem dados inválidos.\n");
            erros.forEach(mensagemErro::escreverNovaLinha);
        }

        return mensagemErro.naoVazio();
    }

    public JTextField getCampoPorta() {
        return campoPorta.getCampo();
    }

    public JTextField getCampoIP() {
        return campoIP.getCampo();
    }

    public JTextField getCampoNomeCliente() {
        return campoNomeCliente.getCampo();
    }

    public String getRegiao() {
        return (String) regiao.getSelectedItem();
    }

    public void chamarFimPrograma() {
        JOptionPane.showMessageDialog(null, "Você saiu do progrma!");
    }

    public boolean getSair() {
        return selecao == JOptionPane.CANCEL_OPTION || selecao == JOptionPane.CLOSED_OPTION;
    }
}
