package com.learning.httpserver.core;

import com.learning.httpserver.HttpServer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
     private int port;
     private String webRoot;
     private ServerSocket serverSocket;

    public ServerListenerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && ! serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                LOGGER.info("Connections is accepted: " + socket.getInetAddress());
                HttpConnectionWorkerThread httpConnectionWorkerThread = new HttpConnectionWorkerThread(socket);
                httpConnectionWorkerThread.start();
            }
//            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
