package main.java.programa.entities;

import java.io.BufferedOutputStream;


public class ClienteFile {
    private final BufferedOutputStream bufferedOutputStream;

    public ClienteFile(BufferedOutputStream bufferedOutputStream) {
        this.bufferedOutputStream = bufferedOutputStream;
    }

    public BufferedOutputStream getBufferedOutputStream() {
        return bufferedOutputStream;
    }
}
