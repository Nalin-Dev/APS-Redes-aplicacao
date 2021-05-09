package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoMensagem {
    private final String mensgem;

    public InfoMensagem(String cliente, String mensgem) {
        this.mensgem = criaInfoMensgem(cliente, mensgem);
    }

    private String criaInfoMensgem(String cliente, String mensgem) {
        final Date date = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        return String.format("%s enviado as %s horas", cliente, formatter.format(date)) + mensgem + "\r\n";
    }

    public String getMensgem() {
        return mensgem;
    }
}
