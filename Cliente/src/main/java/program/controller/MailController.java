package main.java.program.controller;

import main.java.program.view.Tela;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

public class MailController {

    private final Tela parentComponent;
    private final JTextField userEmail = new JTextField();
    private final JTextField finalUserEmail = new JTextField();
    private final JTextArea emailMessage = new JTextArea(5,20);

    Object[] message = {
            "De:", userEmail,
            "Para:", finalUserEmail,
            "Mensagem:", emailMessage
    };

    public MailController(Tela tela) {
        parentComponent = tela;
    }

    public void openEmailDialog () {
        int option = JOptionPane.showConfirmDialog(parentComponent, message, "Dados de envio", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION)
        {
            String userEmailValue = userEmail.getText();
            String finalUserEmailValue = finalUserEmail.getText();
            String emailMessageValue = emailMessage.getText();
        }
            setProperties();
        }

    private void setProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("apsredesteste@gmail.com", "1234abc!");
            }
        });
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("apsredesteste@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse("apsredesteste@gmail.com"));
            message.setSubject("Mail Subject");

            String msg = "This is my first email using JavaMailer";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
