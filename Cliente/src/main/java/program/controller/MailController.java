package main.java.program.controller;

import main.java.program.view.Tela;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.util.Properties;

public class MailController {

    private final Tela parentComponent;
    private final JTextField userEmail = new JTextField();
    private final JTextField userPassword = new JPasswordField();
    private final JTextField finalUserEmail = new JTextField();
    private final JTextField emailSubject = new JTextField();
    private final JTextArea emailMessage = new JTextArea(4,20);
    private Session session;
    private final Properties properties = System.getProperties();
    Object[] fields = {
            "Email:", userEmail,
            "Senha:", userPassword,
            "Para:", finalUserEmail,
            "Assunto:", emailSubject,
            "Mensagem:", emailMessage
    };
    Object[] buttons = {"Enviar",
            "Cancelar"};

    public MailController(Tela tela) {
        parentComponent = tela;
        emailMessage.setWrapStyleWord(true);
        emailMessage.setLineWrap(true);
    }

    public void openEmailDialog () {
        int option = JOptionPane.showOptionDialog(parentComponent, fields, "Dados de envio", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
        if (option == JOptionPane.OK_OPTION) {
            if (userEmail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, "O campo 'Email' é necessário para continuar.\nPor favor, verifique os dados e tente novamente.");
                openEmailDialog();
            } else if (userPassword.getText().isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, "O campo 'Senha' é necessário para continuar.\nPor favor, verifique os dados e tente novamente.");
                openEmailDialog();
            } else if (finalUserEmail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, "O campo 'Para' é necessário para continuar.\nPor favor, verifique os dados e tente novamente.");
                openEmailDialog();
            } else if (emailMessage.getText().isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, "O campo 'Mensagem' é necessário para continuar.\nPor favor, verifique os dados e tente novamente.");
                openEmailDialog();
            } else {
                setProperties();
                cleanInputs();
            }
        } else {
            cleanInputs();
        }
    }

    private void setProperties() {
        // seleciona o host como GMAIL
        String host = "smtp.gmail.com";

        // incluir as propriedades de conexão
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");

        // Cria uma sessão com autenticação
        createSession();
    }

    private void createSession() {
        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail.getText(), userPassword.getText());
            }
        });
        sendMessage();
    }

    private void sendMessage() {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userEmail.getText()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(finalUserEmail.getText()));
            message.setSubject(emailSubject.getText());
            message.setText(emailMessage.getText());
            Transport.send(message);
            JOptionPane.showMessageDialog(parentComponent, "Email enviado com sucesso!");
        } catch (MessagingException e) {
            showMessageError(e.getMessage());
        }
    }

    private void cleanInputs() {
        userEmail.setText("");
        userPassword.setText("");
        finalUserEmail.setText("");
        emailSubject.setText("");
        emailMessage.setText("");
    }

    private void showMessageError(String errorMessage) {
        if (errorMessage.contains("Username") && errorMessage.contains("Password") && errorMessage.contains("not") && errorMessage.contains("accepted")) {
            JOptionPane.showMessageDialog(parentComponent, "Ops, dados de login inválidos.");
        } else {
            JOptionPane.showMessageDialog(parentComponent, "Ops, algo deu errado.\nTente novamente mais tarde.");
        }
    }
}
