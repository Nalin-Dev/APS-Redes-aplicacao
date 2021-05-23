package main.java.programa.server;

import main.java.programa.Main;
import main.java.programa.entities.Porta;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p> A classe {@code ControladorServidor}, é responsavel por fazer controle do servidor.
 * @see Servidor
 * @see Porta
 * @see ServerSocket
 */
public class ControladorServidor {

    public ControladorServidor(final Integer porta) {
        Logger.getLogger(Main.class.getName()).info("Servidor iniciado na porta: " + porta);
        start(porta);
    }

    /**
     * <p> Metodo que inicia o servidor.
     * @param porta  {@code Integer}
     */
    private void start(final Integer porta) {
        try{
            final ServerSocket server = new ServerSocket(porta);
            final ServerSocket serverFile = new ServerSocket(porta + 1);

            while(true) {
                Logger.getLogger(ControladorServidor.class.getName()).info("Aguardando conexão...");
                final Socket conFile = serverFile.accept();
                final Socket con = server.accept();

                final Thread tFile = new FileServer(conFile);

                final Thread t = new Servidor(con);
                t.start();
                tFile.start();
            }
        }catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
