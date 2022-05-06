package ru.itmo.client.to_server;

import ru.itmo.common.commands.CommandType;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * Класс, отвечающий за отправку данных на сервер
 */
public class ServerAPIImpl implements ServerAPI {
    private final String serverHost;
    private final int serverPort;

    public ServerAPIImpl(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /**
     * Получает команду и данные для ее исполнения
     */
    public Response executeCommand(CommandType command, HumanBeing human) throws WrongArgumentException {
        Request request = new Request(
                command,
                human
        );
        try {
            return sendToServer(request);
        } catch (ConnectException e) {
            throw new WrongArgumentException(TypeOfError.NOT_STARTED);
        } catch (SocketException e) {
            throw new WrongArgumentException(TypeOfError.CONNECTED_REFUSE);
        } catch (IOException e) {
            throw new RuntimeException("Pizda");
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
        socket.connect(new InetSocketAddress(serverHost, serverPort));

        socket.getOutputStream().write(request.toJson().getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[4096];
        int amount = socket.getInputStream().read(buffer);
        byte[] responseBytes = new byte[amount];
        System.arraycopy(buffer, 0, responseBytes, 0, amount);

        String json = new String(responseBytes, StandardCharsets.UTF_8);
        // тут происходит пиздец девочки
        return Response.fromJson(json);
    }
}
