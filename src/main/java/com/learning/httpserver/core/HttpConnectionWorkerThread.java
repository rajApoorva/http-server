package com.learning.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;
    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
             inputStream = socket.getInputStream();
             outputStream = socket.getOutputStream();
            int _byte ;
            while((_byte = inputStream.read()) >= 0){
                System.out.print((char) _byte);
            }
            String html = "<html><head><title>I created this bitch !</title><head><body><h1>Hand-made !</h1></body></head></html>";

            final String CRLF = "\n\r";

            String response = "HTTP/1.1 200 OK" + CRLF + "Content-Length: " + html.getBytes().length + CRLF + CRLF
                    + html + CRLF + CRLF;
            outputStream.write(response.getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        LOGGER.info("Procesing finished...");
    }
}
