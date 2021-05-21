package main.java.program.view;/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import main.java.program.controller.Controller;
import main.java.program.entities.Cliente;

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
    private JButton botaoEmail;
    private Controller controller;


    public Tela() throws IOException {

        final TelaInicial telaInicial = new TelaInicial();

        while (telaInicial.existeCamposInvalidos() && !telaInicial.getSair()) {
            telaInicial.reApresentar();
        }

        if (!telaInicial.getSair()) {
            controller = new Controller(new Cliente(telaInicial.getCampoNomeCliente().getText()), this);
            initComponents();
            setVisible(true);
            controller.conectar(telaInicial.getCampoIP().getText(), Integer.parseInt(telaInicial.getCampoPorta().getText()));
            controller.escutar();
        }

        telaInicial.chamarFimPrograma();
    }

    private void initComponents() {

        titulo = new JLabel();
        jScrollPane2 = new JScrollPane();
        scrollDoPainel = new JScrollPane();
        caixaDeTexto = new JTextArea();
        botaoEnviar = new JButton();
        scrollCampoMensagem = new JScrollPane();
        campoMensagem = new JTextField();
        botaoEmail = new javax.swing.JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                formWindowActivated(evt);
            }

            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        titulo.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setText("Chat");

        caixaDeTexto.setEditable(false);
        caixaDeTexto.setColumns(20);
        caixaDeTexto.setRows(5);
        scrollDoPainel.setViewportView(caixaDeTexto);

        jScrollPane2.setViewportView(scrollDoPainel);

        botaoEnviar.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        botaoEnviar.setForeground(new Color(0, 51, 153));
        botaoEnviar.setText("Enviar");
        botaoEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoEnviarActionPerformed(evt);
            }
        });

        botaoEmail.setText("Email");
        botaoEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEmailActionPerformed(evt);
            }
        });

        campoMensagem.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                campoMensagemFocusGained(evt);
            }
        });

        campoMensagem.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                campoMensagemKeyPressed(evt);
            }
        });
        scrollCampoMensagem.setViewportView(campoMensagem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(scrollCampoMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(botaoEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(botaoEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                        .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(botaoEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scrollCampoMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));

        pack();
    }

    private void campoMensagemFocusGained(FocusEvent evt) {
        campoMensagem.setText("");
    }

    private void campoMensagemKeyPressed(KeyEvent evt) {
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                controller.enviarMensagem(campoMensagem.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void botaoEnviarActionPerformed(ActionEvent evt) {
        try {
            controller.enviarMensagem(campoMensagem.getText());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void botaoEmailActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void formWindowActivated(WindowEvent evt) {
        // TODO add your handling code here:
    }

    private void formWindowClosing(WindowEvent evt) {
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
