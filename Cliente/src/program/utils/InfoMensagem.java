package program.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoMensagem {
    private final String mensgem;

    public InfoMensagem(String mensgem) {
        this.mensgem = criaInfoMensgem(mensgem);
    }

    private String criaInfoMensgem(String mensgem) {
        final Date date = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return String.format("\r\n[ Você ] - enviou as %s: \r\n", formatter.format(date)) + mensgem + "\r\n";
    }

    public String getMensgem() {
        return mensgem;
    }
}
