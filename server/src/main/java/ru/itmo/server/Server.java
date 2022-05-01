package ru.itmo.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.server.utility.HandleCommands;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    private static final Logger log = LogManager.getLogger(ru.itmo.server.ServerLauncher.class.getName());
    private HandleCommands commandManager;
    private Response response;

    public void serverStart() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(65100));

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                //получение реквеста от клиента
                byte[] buffer = new byte[4096];
                int amount = socket.getInputStream().read(buffer);
                byte[] bytes = new byte[amount];
                System.arraycopy(buffer, 0, bytes, 0, amount);
                String json = new String(bytes, StandardCharsets.UTF_8);
                Request request = Request.fromJson(json);

                //обработка реквеста
                if (!request.getCommand().equalsIgnoreCase("exit")) {
                    commandManager.handle(request);
                } else {
                    socket.close();
                    serverSocket.close();
                    response = new Response(Response.Status.SERVER_EXIT, "Сервер завершает свою работу.");
                    commandManager.exit();
                    break;
                }

                //отправка респонза клиенту
                socket.getOutputStream().write(response.toJson().getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            System.err.println("Сервер завершает свою работу... :(");
        }
    }
}
