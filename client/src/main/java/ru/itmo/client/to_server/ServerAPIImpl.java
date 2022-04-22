package ru.itmo.client.to_server;

import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.common.model.HumanBeing;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerAPIImpl implements ServerAPI {

    /**
     * Получает команду и данные для ее исполнения
     */
    public Response executeCommand(int index, HumanBeing human) {
        Request request = new Request(
                index,
                human
        );

        try {
            return sendToServer(request);
        } catch (IOException e) {
            /*handle error*/
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет данные на сервер
     * @param request
     * @return
     * @throws IOException
     */
    private Response sendToServer(Request request) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 65100));

        socket.getOutputStream().write(request.toJson().getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[4096];
        int amount = socket.getInputStream().read(buffer);
        byte[] responseBytes = new byte[amount];
        System.arraycopy(buffer, 0, responseBytes, 0, amount);

        socket.close();

        String json = new String(responseBytes, StandardCharsets.UTF_8);
        return Response.fromJson(json);
    }
}
