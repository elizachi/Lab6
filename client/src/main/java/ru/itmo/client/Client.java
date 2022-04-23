package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.ReaderManager;
import ru.itmo.client.to_server.ServerAPI;
import ru.itmo.client.to_server.ServerAPIImpl;
import ru.itmo.common.model.Pair;
import ru.itmo.common.responses.Response;

public class Client {
    private final AskInput ask = new AskInput();

    // todo получать порт и хост здесь
    public void start() {
        ServerAPI serverAPI = new ServerAPIImpl();

        Pair data = ask.askInputManager(ReaderManager.getReader());
        Response response = serverAPI.executeCommand(data);

        if(response.status == Response.Status.OK) {
            System.out.println("Ура ура! Получилось!");
        }
    }
}
