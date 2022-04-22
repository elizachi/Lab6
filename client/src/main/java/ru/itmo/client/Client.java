package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.CommandType;
import ru.itmo.client.service.FormedManager;
import ru.itmo.client.service.ReaderManager;
import ru.itmo.client.to_server.ServerAPI;
import ru.itmo.client.to_server.ServerAPIImpl;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;

public class Client {
    private final AskInput asker = new AskInput();
    private FormedManager maker = new FormedManager(asker);

    // todo получать порт и хост здесь
    public void start() {
        ServerAPI serverAPI = new ServerAPIImpl();
        int input = asker.askCommand(ReaderManager.getReader());
        HumanBeing human = new HumanBeing(input);

        asker.InputManager(CommandType.getEnum(input), ReaderManager.getReader());

        // Response response = serverAPI.executeCommand(input[0], human);

//        if(response.status == Response.Status.OK) {
//            System.out.println("Ура ура! Получилось!");
//        }
    }
}
