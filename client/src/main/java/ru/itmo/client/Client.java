package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.FormedManager;
import ru.itmo.client.service.ReaderManager;
import ru.itmo.client.to_server.ServerAPI;
import ru.itmo.client.to_server.ServerAPIImpl;
import ru.itmo.common.collection.model.HumanBeing;
import ru.itmo.common.responses.Response;

public class Client {
    private FormedManager maker;


    private enum CommandType {
        ADD(true),
        CLEAR(false),
        FILTER_BY_MINUTES_OF_WAITING(false),
        FILTER_GREATER_THAN_IMPACT_SPEED(false),
        HEAD(false),
        HELP(false),
        INFO(false),
        PRINT_UNIQUE_IMPACT_SPEED(false),
        REMOVE_BY_ID(false),
        REMOVE_GREATER(false),
        REMOVE_HEAD(false),
        EXECUTE_SCRIPT(false),
        SHOW(false),
        UPDATE(true),
        EXIT(false);

        private final boolean askGroup;

        CommandType(boolean askGroup) {
            this.askGroup = askGroup;
        }
    };

    public void start() {
        ServerAPI serverAPI = new ServerAPIImpl();
        String command = AskInput.askCommand(ReaderManager.getReader());
        HumanBeing human = null;
        try {
            if(CommandType.valueOf(command).askGroup) {
                human = maker.formed(ReaderManager.getReader());
            }
        } catch(IllegalArgumentException e) {
            System.err.println("Такой команды не существует.");
            start();
            return;
        }
        Response response = serverAPI.executeCommand(command, human);

        if(response.status == Response.Status.OK) {
            System.out.println("Ура ура! Получилось!");
        }
    }
}
