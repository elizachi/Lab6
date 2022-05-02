package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.ReaderManager;
import ru.itmo.client.to_server.ServerAPI;
import ru.itmo.client.to_server.ServerAPIImpl;
import ru.itmo.common.commands.CommandType;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Класс, содержащий основную логику работы клиента
 */
public class Client {
    private final MessageManager msg = new MessageManager();
    private final AskInput ask = new AskInput();

    public void start() {
        String serverHost = "localhost";
        int serverPort = 65100;
        ServerAPI serverAPI = new ServerAPIImpl(serverHost, serverPort);

        while(true) {
            try {
                CommandType commandType = ask.askCommand(ReaderManager.getHandler());
                HumanBeing human = ask.askInputManager(commandType, ReaderManager.getHandler());
                Response response = serverAPI.executeCommand(commandType, human);
                if(response.status == Response.Status.OK) {
                    System.out.println("Ура ура! Получилось! Команда успешно выполнена.");
                } else if(response.status == Response.Status.SERVER_EXIT) {
                    System.out.println("Клиент завершает свою работу.");
                    System.exit(0);
                } else if(response.status == Response.Status.ERROR) {
                    System.out.println("В процессе выполнения данной команды произошла ошибка.");
                }
            } catch (NullPointerException e) {
                ReaderManager.returnOnPreviousReader();
                ask.removeLastElement();
            } catch (RuntimeException e) {
                System.err.println("Непредвиденная ошибка");
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                if(e.getType() == TypeOfError.NOT_STARTED) {
                    System.exit(0);
                }
            }

        }

    }
}
