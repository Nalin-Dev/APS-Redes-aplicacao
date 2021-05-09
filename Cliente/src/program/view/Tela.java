package program.view;/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import main.Cliente;
import program.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 *
 * @author Rafael Nalin
 */
public class Tela extends JFrame {

    private JButton botaoEnviar;
    private JTextArea caixaDeTexto;
    private JTextField campoMensagem;
    private JScrollPane jScrollPane2;
    private JScrollPane scrollCampoMensagem;
    private JScrollPane scrollDoPainel;
    private JLabel titulo;
    private final JTextField txtIP;
    private final JTextField txtPorta;
    private final JTextField txtNome;
    private final String TEXTO_CAMPO_INICIAL = "Digite. . .";
    private final Controller controller;


    public Tela() throws IOException {
        UIManager.put("OptionPane.minimumSize",new Dimension(250,200));
        final JLabel lblMessage = new JLabel("Verificar!");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("4200");
        txtNome = new JTextField("Cliente");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome };
        JOptionPane.showMessageDialog(null, texts);

        controller = new Controller(new Cliente(txtNome.getText()), this);
        initComponents();
        setVisible(true);
        controller.conectar(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
        controller.escutar();
    }

    private void initComponents() {


        titulo = new JLabel();
        jScrollPane2 = new JScrollPane();
        scrollDoPainel = new JScrollPane();
        caixaDeTexto = new JTextArea();
        botaoEnviar = new JButton();
        scrollCampoMensagem = new JScrollPane();
        campoMensagem = new JTextField();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        titulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setText("Chat");

        caixaDeTexto.setEditable(false);
        caixaDeTexto.setColumns(20);
        caixaDeTexto.setRows(5);
        scrollDoPainel.setViewportView(caixaDeTexto);

        jScrollPane2.setViewportView(scrollDoPainel);

        botaoEnviar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botaoEnviar.setForeground(new java.awt.Color(0, 51, 153));
        botaoEnviar.setText("Enviar");
        botaoEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnviarActionPerformed(evt);
            }
        });

        campoMensagem.setText(TEXTO_CAMPO_INICIAL);
        campoMensagem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoMensagemFocusGained(evt);
            }
        });
        campoMensagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoMensagemKeyPressed(evt);
            }
        });
        scrollCampoMensagem.setViewportView(campoMensagem);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(titulo, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(scrollCampoMensagem, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(botaoEnviar, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(titulo, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(botaoEnviar, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                        .addComponent(scrollCampoMensagem))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void campoMensagemFocusGained(java.awt.event.FocusEvent evt) {
        if (TEXTO_CAMPO_INICIAL.equals(campoMensagem.getText())) {
            campoMensagem.setText("");
        }
    }

    private void campoMensagemKeyPressed(java.awt.event.KeyEvent evt) {
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                controller.enviarMensagem(campoMensagem.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            controller.enviarMensagem(campoMensagem.getText());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void formWindowActivated(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        try {
            controller.sair();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JTextArea getCaixaDeTexto() {
        return caixaDeTexto;
    }

    public String getMensagem() {
        return campoMensagem.getText();
    }

    public void limpaMensagem() {
        campoMensagem.setText("");
    }
}