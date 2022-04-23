package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.ReaderManager;
import ru.itmo.client.to_server.ServerAPI;
import ru.itmo.client.to_server.ServerAPIImpl;

public class Client {
    private final AskInput ask = new AskInput();

    // todo получать порт и хост здесь
    public void start() {
        ServerAPI serverAPI = new ServerAPIImpl();

        ask.askInputManager(ReaderManager.getReader());

        // Response response = serverAPI.executeCommand(input[0], human);

//        if(response.status == Response.Status.OK) {
//            System.out.println("Ура ура! Получилось!");
//        }
    }
}
