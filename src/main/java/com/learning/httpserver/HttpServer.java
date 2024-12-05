package com.learning.httpserver;

import com.learning.httpserver.config.Configuration;
import com.learning.httpserver.config.ConfigurationManager;
import com.learning.httpserver.core.ServerListenerThread;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.logging.Logger;

public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) throws IOException {

        LOGGER.info("Server is starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using port: " + conf.getPort());
        LOGGER.info("Using web root: " + conf.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
