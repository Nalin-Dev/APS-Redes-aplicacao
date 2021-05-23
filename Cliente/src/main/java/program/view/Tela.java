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
import java.io.File;
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
    private JFileChooser fileChooser;
    private JButton buttonChooser;

    private final JTextField txtIP;
    private final JTextField txtPorta;
    private final JTextField txtNome;
    private final Controller controller;



    public Tela() throws IOException {
        UIManager.put("OptionPane.minimumSize",new Dimension(250,200));
        final JLabel lblMessage = new JLabel("Dados de entrada!");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("4100");
        txtNome = new JTextField("Cliente");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome };
        JOptionPane.showMessageDialog(null, texts);

        controller = new Controller(new Cliente(txtNome.getText()), this);
        initComponents();
        setVisible(true);
        controller.conectar(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
        controller.escutarFile();
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
        buttonChooser = new JButton("...");
        fileChooser = new JFileChooser();


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


        buttonChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    buttonActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                                                .addComponent(buttonChooser, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(buttonChooser, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(botaoEnviar, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                        .addComponent(scrollCampoMensagem))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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


    private void buttonActionPerformed(ActionEvent evt) throws IOException {

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                controller.enviarFile(file);
            }

    }

    private void botaoEnviarActionPerformed(ActionEvent evt) {
        try {
            controller.enviarMensagem(campoMensagem.getText());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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
