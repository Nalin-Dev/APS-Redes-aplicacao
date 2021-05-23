package main.java.program.controller;

import main.java.program.entities.Arquivo;
import main.java.program.entities.Cliente;
import main.java.program.utils.InfoMensagem;
import main.java.program.utils.JsonParserToMap;
import main.java.program.view.Tela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.logging.Logger;

public class Controller {

    private final Cliente cliente;
    private final Tela tela;
    private boolean mensagemRecebida = true;
    private String ultimoRemetente = "";
    private BufferedOutputStream bfo;
    private BufferedInputStream bfi;
    JButton fileButton;
    JFileChooser fileChooser;
    private  Socket fileConnection;

    public Controller(Cliente cliente, Tela tela) {
        this.cliente = cliente;
        this.tela = tela;
        fileButton = new JButton("Attach");
        fileChooser = new JFileChooser();


    }

    /***
     * Método usado para conectar no server socket, retorna IO Exception caso dê algum erro.
     * @throws IOException
     */
    public void conectar(final String ip, final Integer porta) throws IOException{
        final Socket socket = new Socket(ip, porta);
        fileConnection = new Socket(ip, porta + 1);
        bfo = new BufferedOutputStream
                (fileConnection.getOutputStream());
        bfi = new BufferedInputStream(
                fileConnection.getInputStream());
        cliente.setSocket(socket);
        cliente.setOu(socket.getOutputStream());
        cliente.setOuw(new OutputStreamWriter(cliente.getOu()));
        cliente.setBfw(new BufferedWriter(cliente.getOuw()));
        tela.getCaixaDeTexto().append("SISTEMA: " + cliente.getNomeID() +" entrou na sala!\r\n");
        Logger.getLogger(Controller.class.getName()).info("Entrando na sala...");
        cliente.getBfw().write(cliente.getNomeID() + "\r\n");
        cliente.getBfw().flush();
    }

    /***
     * Método usado para enviar mensagem para o server socket
     * @param msg do tipo String
     * @throws IOException retorna IO Exception caso dê algum erro.
     */
    public void enviarMensagem(String msg) throws IOException{
        Logger.getLogger(Controller.class.getName()).info("Mensagem enviada: " + msg);
        if("Sair".equals(msg)){
            cliente.getBfw().write("Desconectado\r\n");
            tela.getCaixaDeTexto().append("Desconectado \r\n");
        } else if (!"".equals(msg)) {
            cliente.getBfw().write(msg+"\r\n");
            ultimoRemetente = "";

            if (!mensagemRecebida) {
                tela.getCaixaDeTexto().append(tela.getMensagem() + "\r\n");
            } else {
                tela.getCaixaDeTexto().append(new InfoMensagem(tela.getMensagem()).getMensgem());
                mensagemRecebida = false;
            }
        }

        cliente.getBfw().flush();
        tela.limpaMensagem();
    }


    public void escutarFile() {
        try{

            byte[] objectAsByte= new byte[fileConnection.getReceiveBufferSize()];

            int a = bfi.read(objectAsByte);
            while(a != -1 ){

                Arquivo arquivo = (Arquivo) getObjectFromByte(objectAsByte);
                JOptionPane.showMessageDialog(tela, "Você recebeu um arquivo:  " + arquivo.getNome() + "Com tamanho: " + arquivo.getTamanhoKB());
                int userSelection = fileChooser.showSaveDialog(tela);
                String dir = null;

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    dir=  fileToSave.getAbsolutePath();
                }
                if(dir != null)
                {
                    FileOutputStream fos = new FileOutputStream(dir);
                    fos.write(arquivo.getConteudo());
                }
                a = bfi.read(objectAsByte);
            }


        }catch (SocketException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Object getObjectFromByte(byte[] objectAsByte) {
        Object obj = null;

        try {
            ByteArrayInputStream bis  = new ByteArrayInputStream(objectAsByte);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();

            bis.close();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;

    }

    public void enviarFile(File fileSelected) throws IOException {

        byte[] bFile = new byte[(int) fileSelected.length()];
        FileInputStream fis = new FileInputStream(fileSelected);
        fis.read(bFile);
        fis.close();

        long kbSize = fileSelected.length() / 1024;
        Arquivo arquivo = new Arquivo();
        arquivo.setConteudo(bFile);
        arquivo.setNome(fileSelected.getName());
        arquivo.setTamanhoKB(kbSize);

        byte[] fileSeriliazed = serializarArquivo(arquivo);


        bfo.write(fileSeriliazed);
        bfo.flush();
    }

    private byte[] serializarArquivo(Arquivo arquivo){
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream ous;
            ous = new ObjectOutputStream(bao);
            ous.writeObject(arquivo);
            return bao.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void escutar() throws IOException {
        final InputStream in = cliente.getSocket().getInputStream();
        final InputStreamReader inr = new InputStreamReader(in);
        final BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while(!"Sair".equalsIgnoreCase(msg)) {

            if(bfr.ready()) {
                msg = bfr.readLine();

                Logger.getLogger(Controller.class.getName()).info("Mensagem recebida: " + msg);

                if (!"".equals(msg)) {
                    final Map<String, Object> json = JsonParserToMap.parse(msg);

                    mensagemRecebida = json.containsKey("mensagem");

                    final String nomeRemetente = (String) json.get("id");
                    final String nome = nomeRemetente.split("#")[0];

                    if (mensagemRecebida) {
                        final String mensagem = (String) json.get("mensagem");

                        if ("".equals(ultimoRemetente) || !ultimoRemetente.equals(nomeRemetente)) {
                            ultimoRemetente = nomeRemetente;
                            tela.getCaixaDeTexto().append(String.format("\n[ %s ] te enviou:\n %s\r\n", nome, mensagem));
                        } else {
                            tela.getCaixaDeTexto().append(mensagem + "\r\n");
                        }
                    } else if (json.containsKey("sistema")) {

                        final String sistema = (String) json.get("sistema");

                        if ("Desconectado".equalsIgnoreCase(sistema)) {
                            tela.getCaixaDeTexto().append(String.format("\nSISTEMA: [ %s ]  se desconectou.\r\n", nome));
                        } else if ("Entrada".equalsIgnoreCase(sistema)) {
                            tela.getCaixaDeTexto().append(String.format("\nSISTEMA: [ %s ]  entrou na sala.\r\n", nome));
                        }
                    }
                }
            }
        }
    }

    /***
     * Método usado quando o usuário clica em sair
     * @throws IOException retorna IO Exception caso dê algum erro.
     */
    public void sair() throws IOException{

        enviarMensagem("Sair");
        cliente.getBfw().close();
        cliente.getOuw().close();
        cliente.getOu().close();
        cliente.getSocket().close();
        bfo.close();
        fileConnection.close();
    }
}
