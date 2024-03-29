package ru.itmo.client.to_server;

import ru.itmo.client.ClientLauncher;
import ru.itmo.common.commands.CommandType;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Класс, отвечающий за отправку данных на сервер
 */
public class ServerAPIImpl implements ServerAPI {
    private final String serverHost;
    private final int serverPort;
    public int attempts = 0;
    Socket socket = new Socket();


    public ServerAPIImpl(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public int getAttempts() {
        return attempts;
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
        } catch (IOException e) {
            throw new WrongArgumentException(TypeOfError.CONNECTED_REFUSE);
        }
    }

    /**
     * Отправляет данные на сервер (использую потоки ввода-вывода)
     * @param request
     * @return
     * @throws IOException
     */
    private Response sendToServer(Request request) throws IOException {
        // создаю сокет
        Socket socket = new Socket();
        // коннекчу сокет на нужные хост и порт
        socket.connect(new InetSocketAddress(serverHost, serverPort));
        // отправка данных серверу
        // через сокет открываем поток записи, запрос парсим в json и сериализуем
        socket.getOutputStream().write(request.toJson().getBytes(StandardCharsets.UTF_8));
        // создаем массив c максимально возможным размером, в который запишем полученные от сервера байтики
        byte[] buffer = new byte[4096];
        int amount = socket.getInputStream().read(buffer);
        byte[] countPackage = new byte[amount];
        System.arraycopy(buffer, 0, countPackage, 0, amount);

        int count = Integer.parseInt(new String(countPackage, StandardCharsets.UTF_8));
        StringBuilder json = new StringBuilder();
        while(count != 0) {
            // размер считанного массива. 0 если 0 байт, -1 если eof
            amount = socket.getInputStream().read(buffer);
            // массив для записи считанных байтов (может быть меньше чем 4096,
            // поэтому создаем строго фиксированный под кол-во считанных байт)
            byte[] responseBytes = new byte[amount];
            System.arraycopy(buffer, 0, responseBytes, 0, amount);
            String add = new String(responseBytes, StandardCharsets.UTF_8);
            json.append(add);
            count--;
        }
        // тут происходит пиздец девочки - парсим из json'а
        return Response.fromJson(json.toString());
    }

    /**
     * Close connection when everything end.
     */
    public void closeConnection(){
        try{
            socket.getInputStream().close();
            socket.getOutputStream().close();
            socket.close();
            ClientLauncher.log.info("Соеденение успешно закрыто.");
        } catch (IOException exception) {
            ClientLauncher.log.error("Ошибка закрытия файлов.");
        }
    }

    /**
     * Подключение клиента к серверу
     * @return true если переподключение прошло успешно
     */
    public boolean connectToServer(){
        try {
            if (attempts > 0)
                System.out.println("Попытка переподключиться...");
            attempts++;
            socket = new Socket(serverHost, serverPort);
        } catch (UnknownHostException e) {
            ClientLauncher.log.error("Неизвестный хост: " + serverHost + ".\n");
            return false;
        } catch (IOException exception) {
            ClientLauncher.log.error("Ошибка открытия порта " + serverPort + ".\n");
            return false;
        }
        return true;
    }
}
